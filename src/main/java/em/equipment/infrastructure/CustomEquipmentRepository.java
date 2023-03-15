package em.equipment.infrastructure;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import em.equipment.domain.QEquipment;
import em.equipment.dto.EquipmentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static em.equipment.domain.QEquipment.*;

@Repository
public class CustomEquipmentRepository {

    private final EntityManager em;

    private final JPAQueryFactory query;

    public CustomEquipmentRepository(EntityManager em){
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }
    public EquipmentDto findEquipment(Long equipmentId, int equipmentVersion){
        EquipmentDto queryResult = query
                .select(Projections.fields(EquipmentDto.class,
                        equipment.id,
                        equipment.version,
                        equipment.modeName,
                        equipment.companyId
                        ))
                .from(equipment)
                .where(equipment.id.eq(equipmentId), equipment.version.eq(equipmentVersion))
                .fetchOne();
        return queryResult;
    }
}
