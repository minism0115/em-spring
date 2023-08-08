package em.equipment.dto;

import em.equipment.domain.Equipment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentResponse {

    private Long id;

    private int version;

    private String modeName;

    private Long companyId;

    private String companyName;

    public EquipmentResponse(Equipment equipment){
        id = equipment.getId();
        version = equipment.getVersion();
        modeName = equipment.getEquipmentName();
//        companyId = equipment.getCompany().getId();
//        companyName = equipment.getCompany().getCompanyName();
    }
}
