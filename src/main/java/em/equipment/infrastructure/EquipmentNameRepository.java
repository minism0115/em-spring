package em.equipment.infrastructure;

import em.equipment.domain.EquipmentName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentNameRepository extends JpaRepository<EquipmentName, String> {

    List<EquipmentName> findByEquipmentNameIdAndEquipmentName(String equipmentNameId, String equipmentName);
}
