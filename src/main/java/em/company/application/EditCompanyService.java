package em.company.application;

import em.company.domain.Company;
import em.company.dto.CreateCompanyRequest;
import em.company.infrastructure.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EditCompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    public void createCompany(CreateCompanyRequest request) {
        validateDuplicationOfCompanyName(request.getCompanyName());
        Company company = Company.builder()
                .companyName(request.getCompanyName())
                .build();
        companyRepository.save(company);
    }

    private void validateDuplicationOfCompanyName(String companyName) {
        List<Company> findCompanies = companyRepository.findByCompanyName(companyName);
        if(!findCompanies.isEmpty()){
            throw new ConstraintViolationException("이미 존재하는 회사명입니다.", null);
        }
    }
}
