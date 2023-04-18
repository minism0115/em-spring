package em.equipment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateEquipmentRequest {

    @NotBlank
    private String modeName;

    @NotNull
    private Long companyId;

}
