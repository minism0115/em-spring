package em.equipment.infrastructure;

import em.equipment.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Equipment.EquipmentId>, EquipmentRepositoryCustom {
    List<Equipment> findByEquipmentName(String equipmentName);

    Optional<Equipment> findById(Equipment.EquipmentId equipmentId);

    Equipment findOneByOrderByIdDesc();
}
