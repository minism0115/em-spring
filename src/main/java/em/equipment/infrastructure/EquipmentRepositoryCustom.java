package em.equipment.infrastructure;

import em.equipment.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipmentRepositoryCustom {

    Page<Equipment> findEquipments(String equipmentName, Pageable pageable);
}
