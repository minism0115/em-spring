package em.equipment.infrastructure;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import em.equipment.domain.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static em.equipment.domain.QEquipment.equipment;

@Repository
@RequiredArgsConstructor
public class EquipmentRepositoryImpl implements EquipmentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<Equipment> findEquipments(List<String> modelNameIds, Pageable pageable) {
        JPAQuery<Equipment> query = queryFactory
                .selectFrom(equipment)
                .where(inModelNameIds(modelNameIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // Sort
        for(Sort.Order order : pageable.getSort()){
            PathBuilder pathBuilder = new PathBuilder(equipment.getType(), equipment.getMetadata());
            query.orderBy(new OrderSpecifier(order.isAscending()? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty())));
        }

        List<Equipment> result = query.fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(equipment.count())
                .from(equipment)
                .where(inModelNameIds(modelNameIds));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression inModelNameIds(List<String> modelNameIds){
        if(modelNameIds.size() > 0) {
            return equipment.modelNameId.in(modelNameIds);
        } else {
            return null;
        }
    }
}
