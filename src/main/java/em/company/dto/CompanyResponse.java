package em.company.dto;

import em.company.domain.Company;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyResponse {

    private Long companyId;

    private String companyName;

    public CompanyResponse(Company company){
        companyId = company.getId();
        companyName = company.getCompanyName();
    }
}
