package em.equipment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentDto {

    private Long id;

    private int version;

    private String modeName;

    private String companyId;
}
