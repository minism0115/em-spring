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

    public EquipmentResponse(Equipment equipment, String modelName){
        equipmentId = equipment.getId();
        version = equipment.getVersion();
        // TODO: EQUIPMENT_NAME 테이블에서 찾은 장비명 세팅
        equipmentName = modelName;
//        companyId = equipment.getCompany().getId();
//        companyName = equipment.getCompany().getCompanyName();
    }
}
