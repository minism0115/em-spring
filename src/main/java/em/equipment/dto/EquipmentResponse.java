package em.equipment.dto;

import em.equipment.domain.Equipment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentResponse {

    private String equipmentId;

    private int version;

    private String equipmentName;

    private Long companyId;

    private String companyName;

    public EquipmentResponse(Equipment equipment){
        equipmentId = equipment.getId();
        version = equipment.getVersion();
        equipmentName = equipment.getEquipmentName();
//        companyId = equipment.getCompany().getId();
//        companyName = equipment.getCompany().getCompanyName();
    }
}
