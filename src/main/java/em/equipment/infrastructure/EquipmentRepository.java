package em.equipment.infrastructure;

import em.equipment.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Equipment.EquipmentId>, EquipmentRepositoryCustom {

    Optional<Equipment> findById(Equipment.EquipmentId equipmentId);

    Equipment findOneByOrderByIdDesc();

    // period가 1이면 최근 한 달의 데이터 조회
    @Query("select count(e) from Equipment e where e.createdAt >= to_char(add_months(current_date, -:period), 'yyyy-MM-dd')")
    Long countByPeriod(@Param("period") Integer period);
}
