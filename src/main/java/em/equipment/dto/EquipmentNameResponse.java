package em.equipment.dto;

import em.equipment.domain.EquipmentName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipmentNameResponse {

    private String equipmentNameId;

    private String equipmentName;

    public EquipmentNameResponse(EquipmentName equipmentName){
        equipmentNameId = equipmentName.getId();
        // TODO: 복호화된 데이터 세팅
//        equipmentName = equipmentName.getEquipmentName();
    }

}
