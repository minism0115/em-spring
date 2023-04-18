package em.equipment.application;

import em.equipment.domain.Equipment;
import em.equipment.domain.Equipment.EquipmentId;
import em.equipment.dto.EquipmentResponse;
import em.equipment.infrastructure.CustomEquipmentRepository;
import em.equipment.infrastructure.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindEquipmentService {

    private final CustomEquipmentRepository customEquipmentRepository;
    private final EquipmentRepository equipmentRepository;

    public EquipmentResponse findEquipment(Long equipmentId, Integer equipmentVersion){
        EquipmentId id = EquipmentId.of(equipmentId, equipmentVersion);
        Equipment foundOne = equipmentRepository.findById(id);
        if(foundOne != null) {
            return new EquipmentResponse(foundOne);
        } else {
            throw new EntityNotFoundException("해당하는 장비가 존재하지 않습니다.");
        }
    }


    public Page<EquipmentResponse> findEquipments(String modeName, Pageable pageable) {
        Page<Equipment> equipments = customEquipmentRepository.findEquipments(modeName, pageable);
        List<EquipmentResponse> collect = equipments.stream()
                .map(EquipmentResponse::new)
                .collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, equipments.getSize());
    }
}
