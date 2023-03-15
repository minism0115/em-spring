package em.equipment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateEquipmentRequest {

    @NotBlank
    private String modeName;

    private String companyId;

}
