package em.equipment.application;

import em.company.domain.Company;
import em.company.infrastructure.CompanyRepository;
import em.equipment.domain.Equipment;
import em.equipment.dto.CreateEquipmentRequest;
import em.equipment.dto.UpdateEquipmentRequest;
import em.equipment.infrastructure.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EditEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public Equipment createEquipment(CreateEquipmentRequest request){
        validateDuplicationOfModeName(request.getModeName());
        Equipment lastOne = equipmentRepository.findOneByOrderByIdDesc();
        Long id = 1L;
        if(lastOne != null){
            id = lastOne.getId() + 1L;
        }
        Company findCompany = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("해당하는 업체가 존재하지 않습니다."));
        Equipment equipment = Equipment.builder()
                .id(id)
                .version(1)
                .modeName(request.getModeName())
                .company(findCompany)
                .build();
        return equipmentRepository.save(equipment);
    }

    private void validateDuplicationOfModeName(String modeName) {
        List<Equipment> equipments = equipmentRepository.findByModeName(modeName);
        if(!equipments.isEmpty()){
            throw new ConstraintViolationException("이미 존재하는 장비명입니다.", null);
        }
    }

    @Transactional
    public void updateEquipment(Long equipmentId, Integer equipmentVersion, UpdateEquipmentRequest request) {
        Equipment.EquipmentId id = Equipment.EquipmentId.of(equipmentId, equipmentVersion);
        Equipment foundOne = equipmentRepository.findById(id);
        if(foundOne != null) {
            foundOne.update(request);
        } else {
            throw new EntityNotFoundException("해당하는 장비가 존재하지 않습니다.");
        }
    }
}
