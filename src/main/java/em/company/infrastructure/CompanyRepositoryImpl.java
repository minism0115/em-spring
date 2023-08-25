package em.company.infrastructure;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import em.company.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static em.company.domain.QCompany.company;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Company> findCompanies(String companyName, Pageable pageable) {
        JPAQuery<Company> query = queryFactory
                .selectFrom(company)
                .where(containsCompanyName(companyName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // Sort
        for(Sort.Order order : pageable.getSort()){
            PathBuilder pathBuilder = new PathBuilder(company.getType(), company.getMetadata());
            query.orderBy(new OrderSpecifier(order.isAscending()? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty())));
        }

        List<Company> result = query.fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(company.count())
                .from(company)
                .where(containsCompanyName(companyName));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression containsCompanyName(String companyName){
        if(companyName != null) {
            return company.companyName.contains(companyName);
        } else {
            return null;
        }
    }
}
