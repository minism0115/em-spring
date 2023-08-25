package em.company.application;

import em.company.domain.Company;
import em.company.dto.CompanyResponse;
import em.company.infrastructure.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequestMapping("/api/company")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindCompanyService {

    private final CompanyRepository companyRepository;

    public Page<CompanyResponse> findCompanies(String companyName, Pageable pageable) {
        Page<Company> companies = companyRepository.findCompanies(companyName, pageable);
        List<CompanyResponse> collect = companies.stream()
                .map(CompanyResponse::new)
                .collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, companies.getTotalElements());
    }
}
