package em.equipment.application;

import em.company.infrastructure.CompanyRepository;
import em.equipment.domain.Equipment;
import em.equipment.domain.EquipmentName;
import em.equipment.dto.CreateEquipmentRequest;
import em.equipment.dto.UpdateEquipmentRequest;
import em.equipment.infrastructure.EquipmentNameRepository;
import em.equipment.infrastructure.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EditEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;
    private final EquipmentNameRepository equipmentNameRepository;

    @Transactional
    public void createEquipment(CreateEquipmentRequest request){
        validateDuplicationOfEquipmentName(request.getEquipmentName());
//        Company findCompany = companyRepository.findById(request.getCompanyId())
//                .orElseThrow(() -> new EntityNotFoundException("해당하는 업체가 존재하지 않습니다."));

        // 장비명 암호화
        byte[] encodeByte = Base64.encodeBase64(request.getEquipmentName().getBytes());

        // 장비명 저장
        EquipmentName equipmentName = EquipmentName.builder()
                .id(UUID.randomUUID().toString())
                .equipmentName(encodeByte)
                .build();
        EquipmentName savedOne = equipmentNameRepository.save(equipmentName);

        // 장비 저장
        Equipment equipment = Equipment.builder()
                .id(UUID.randomUUID().toString())
                .version(1)
                .equipmentName(savedOne.getId()) // 장비명 테이블의 ID를 저장
//                .company(findCompany)
                .build();
        equipmentRepository.save(equipment);
    }

    private void validateDuplicationOfEquipmentName(String equipmentName) {
        List<Equipment> equipments = equipmentRepository.findByEquipmentName(equipmentName);
        if(!equipments.isEmpty()){
            throw new ConstraintViolationException("이미 존재하는 장비명입니다.", null);
        }
    }

    @Transactional
    public void updateEquipment(String equipmentId, Integer equipmentVersion, UpdateEquipmentRequest request) {
        Equipment.EquipmentId id = Equipment.EquipmentId.of(equipmentId, equipmentVersion);
        Equipment foundOne = equipmentRepository.findById(id);
        if(foundOne != null) {
            foundOne.update(request);
        } else {
            throw new EntityNotFoundException("해당하는 장비가 존재하지 않습니다.");
        }
    }

    public void createEquipmentWithFiles(CreateEquipmentRequest request, List<MultipartFile> files) {
        validateDuplicationOfEquipmentName(request.getEquipmentName());
//        Equipment lastOne = equipmentRepository.findOneByOrderByIdDesc();
//        Long id = 1L;
//        if(lastOne != null){
//            id = lastOne.getId() + 1L;
//        }
//        Company findCompany = companyRepository.findById(request.getCompanyId())
//                .orElseThrow(() -> new EntityNotFoundException("해당하는 업체가 존재하지 않습니다."));
        Equipment equipment = Equipment.builder()
                .id(UUID.randomUUID().toString())
                .version(1)
                .equipmentName(request.getEquipmentName())
//                .company(findCompany)
                .build();
        equipmentRepository.save(equipment);
    }
}
