package em.equipment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateEquipmentRequest {

    @NotBlank
    private String equipmentName;

//    @NotNull
    private Long companyId;

}
