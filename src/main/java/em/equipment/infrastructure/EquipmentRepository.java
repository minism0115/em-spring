package em.equipment.infrastructure;

import em.equipment.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long>, EquipmentRepositoryCustom {
    List<Equipment> findByEquipmentName(String equipmentName);

    Equipment findById(Equipment.EquipmentId equipmentId);

    Equipment findOneByOrderByIdDesc();
}
