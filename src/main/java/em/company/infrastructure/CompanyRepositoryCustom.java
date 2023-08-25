package em.company.infrastructure;

import em.company.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {
    Page<Company> findCompanies(String companyName, Pageable pageable);
}
