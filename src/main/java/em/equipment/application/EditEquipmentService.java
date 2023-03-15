package em.equipment.application;

import em.equipment.domain.Equipment;
import em.equipment.dto.CreateEquipmentRequest;
import em.equipment.infrastructure.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EditEquipmentService {

    private final EquipmentRepository equipmentRepository;

    @Transactional
    public Equipment createEquipment(CreateEquipmentRequest createEquipmentRequest){
        validateDuplicationOfModeName(createEquipmentRequest.getModeName());
        Equipment lastOne = equipmentRepository.findOneByOrderByIdDesc();
        Long id = 1L;
        if(lastOne != null){
            id = lastOne.getId() + 1L;
        }
        Equipment equipment = Equipment.builder()
                .id(id)
                .version(1)
                .modeName(createEquipmentRequest.getModeName())
                .companyId(createEquipmentRequest.getCompanyId())
                .build();
        return equipmentRepository.save(equipment);
    }

    private void validateDuplicationOfModeName(String modeName) {
        List<Equipment> equipmentList = equipmentRepository.findByModeName(modeName);
        if(!equipmentList.isEmpty()){
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
    }
}
