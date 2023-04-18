package em.equipment.infrastructure;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import em.equipment.domain.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static em.equipment.domain.QEquipment.equipment;

@Repository
@RequiredArgsConstructor
public class CustomEquipmentRepository {

    private final JPAQueryFactory query;

    public Page<Equipment> findEquipments(String modeName, Pageable pageable) {
        List<Equipment> queryResult = query
                .selectFrom(equipment)
                .where(containsModeName(modeName))
                .orderBy(equipment.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(equipment.count())
                .from(equipment)
                .where(containsModeName(modeName));

        return PageableExecutionUtils.getPage(queryResult, pageable, countQuery::fetchOne);
    }

    private BooleanExpression containsModeName(String modeName){
        if(modeName != null) {
            return equipment.modeName.contains(modeName);
        } else {
            return null;
        }
    }
}
