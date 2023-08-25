package em.equipment.application;

import em.equipment.domain.Equipment;
import em.equipment.domain.Equipment.EquipmentId;
import em.equipment.domain.EquipmentName;
import em.equipment.dto.EquipmentNameResponse;
import em.equipment.dto.EquipmentResponse;
import em.equipment.infrastructure.EquipmentNameRepository;
import em.equipment.infrastructure.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequestMapping("/api/equipment")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentNameRepository equipmentNameRepository;

    public EquipmentResponse findEquipment(String equipmentId, Integer equipmentVersion){
        EquipmentId id = EquipmentId.of(equipmentId, equipmentVersion);
        Equipment foundOne = equipmentRepository.findById(id)
                .orElseThrow();
        if(foundOne != null) {
            return new EquipmentResponse(foundOne);
        } else {
            throw new EntityNotFoundException("해당하는 장비가 존재하지 않습니다.");
        }
    }

    @Cacheable("equipment")
    public Page<EquipmentResponse> findEquipments(String equipmentName, Pageable pageable) {
        Page<Equipment> equipments = equipmentRepository.findEquipments(equipmentName, pageable);
        List<EquipmentResponse> collect = equipments.stream()
                .map(EquipmentResponse::new)
                .collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, equipments.getSize());
    }

    @Cacheable("equipmentName")
    public List<EquipmentNameResponse> findEquipmentNames(String equipmentNameId, String equipmentName){
        List<EquipmentName> equipmentNames = equipmentNameRepository.findByEquipmentNameIdAndEquipmentName(equipmentNameId, equipmentName);
        List<EquipmentNameResponse> collect = equipmentNames.stream()
                .map(EquipmentNameResponse::new)
                .collect(Collectors.toList());
        return collect;
    }
}
