package em.equipment.application;

import em.company.infrastructure.CompanyRepository;
import em.equipment.domain.Equipment;
import em.equipment.domain.ModelName;
import em.equipment.dto.CreateEquipmentRequest;
import em.equipment.dto.ModelNameResponse;
import em.equipment.dto.UpdateEquipmentRequest;
import em.equipment.infrastructure.ModelNameRepository;
import em.equipment.infrastructure.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EditEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;
    private final ModelNameRepository modelNameRepository;
    private final FindEquipmentService findEquipmentService;

    @Transactional
    @CacheEvict(value = "modelNames", allEntries = true)
    public void createEquipment(CreateEquipmentRequest request){
        validateDuplicationOfEquipmentName(request.getEquipmentName());
//        Company findCompany = companyRepository.findById(request.getCompanyId())
//                .orElseThrow(() -> new EntityNotFoundException("해당하는 업체가 존재하지 않습니다."));

        // 장비명 암호화
        byte[] encodeByte = Base64.encodeBase64(request.getEquipmentName().getBytes());

        // 장비명 저장
        ModelName modelName = ModelName.builder()
                .id(UUID.randomUUID().toString())
                .modelName(encodeByte)
                .build();
        ModelName savedOne = modelNameRepository.save(modelName);

        // 장비 저장
        Equipment equipment = Equipment.builder()
                .id(UUID.randomUUID().toString())
                .version(1)
                .modelNameId(savedOne.getId()) // 장비명 테이블의 ID를 저장
//                .company(findCompany)
                .build();
        equipmentRepository.save(equipment);
    }

    private void validateDuplicationOfEquipmentName(String equipmentName) {
        // 암호화 시 salt 값이 존재해서 장비명에 대한 암호화 값이 암호화 할 때마다 달라진다고 가정
        // 복호화된 장비명을 모두 가지고 온 다음 for문 돌면서 평문인 장비명과 비교해야 한다
        Boolean duplicateYn = false;
        List<ModelNameResponse> modelNames = findEquipmentService.findModelNames();
        for (ModelNameResponse modelName : modelNames) {
            if(modelName.getDecryptedModelName().equals(equipmentName)) duplicateYn = true;
        }
        if(duplicateYn){
            throw new ConstraintViolationException("이미 존재하는 장비명입니다.", null);
        }
    }

    @Transactional
    @CacheEvict(value = "modelNames", allEntries = true)
    public void updateEquipment(String equipmentId, Integer equipmentVersion, UpdateEquipmentRequest request) {
        Equipment.EquipmentId id = Equipment.EquipmentId.of(equipmentId, equipmentVersion);
        Equipment foundOne = equipmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 장비가 존재하지 않습니다."));
        foundOne.update(request);
    }

    @Transactional
    @CacheEvict(value = "modelNames", allEntries = true)
    public void createEquipmentWithFiles(CreateEquipmentRequest request, List<MultipartFile> files) {
        validateDuplicationOfEquipmentName(request.getEquipmentName());
//        Equipment lastOne = equipmentRepository.findOneByOrderByIdDesc();
//        Long id = 1L;
//        if(lastOne != null){
//            id = lastOne.getId() + 1L;
//        }
//        Company findCompany = companyRepository.findById(request.getCompanyId())
//                .orElseThrow(() -> new EntityNotFoundException("해당하는 업체가 존재하지 않습니다."));
        Equipment equipment = Equipment.builder()
                .id(UUID.randomUUID().toString())
                .version(1)
                .modelNameId(request.getEquipmentName())
//                .company(findCompany)
                .build();
        equipmentRepository.save(equipment);
    }
}
