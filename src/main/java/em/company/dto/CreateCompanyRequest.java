package em.company.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCompanyRequest {

    @NotBlank
    private String companyName;
}
