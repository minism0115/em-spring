package em.equipment.infrastructure;

import em.equipment.domain.EquipmentName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentNameRepository extends JpaRepository<EquipmentName, Long> {

}
