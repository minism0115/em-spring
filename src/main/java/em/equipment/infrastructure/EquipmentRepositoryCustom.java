package em.equipment.infrastructure;

import em.equipment.domain.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipmentRepositoryCustom {

    Page<Equipment> findEquipments(List<String> modelNameIds, Pageable pageable);
}
