package em.modelName.dto;

import em.modelName.domain.ModelName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

@Data
@NoArgsConstructor
public class ModelNameResponse {

    private String modelNameId;

    private String decryptedModelName;

    public ModelNameResponse(ModelName modelName){
        modelNameId = modelName.getId();
        // 복호화된 데이터 세팅
        decryptedModelName = new String(Base64.decodeBase64(modelName.getModelName()));
    }

}
