package em.equipment.application;

import em.equipment.domain.Equipment;
import em.equipment.domain.Equipment.EquipmentId;
import em.equipment.domain.ModelName;
import em.equipment.dto.EquipmentResponse;
import em.equipment.dto.ModelNameResponse;
import em.equipment.infrastructure.EquipmentRepository;
import em.equipment.infrastructure.ModelNameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequestMapping("/api/equipment")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final ModelNameRepository modelNameRepository;

    @Autowired
    private final ApplicationContext applicationContext;
    private FindEquipmentService getSpringProxy(){
        return applicationContext.getBean(FindEquipmentService.class);
    }

    public EquipmentResponse findEquipment(String equipmentId, Integer equipmentVersion) {
        EquipmentId id = EquipmentId.of(equipmentId, equipmentVersion);
        Equipment foundOne = equipmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당하는 장비가 존재하지 않습니다."));
        return new EquipmentResponse(foundOne, this.getSpringProxy().findModelNameById(foundOne.getModelNameId()).getDecryptedModelName());
    }

    public Page<EquipmentResponse> findEquipments(String equipmentName, Pageable pageable) {
        // 모델명 테이블(캐싱 테이블)에서 ID를 찾고 그 ID로 조회
        List<String> modelNameIds = new ArrayList<>();
        if(equipmentName != null && !equipmentName.equals("")){
            modelNameIds = this.getSpringProxy().findModelNameIdsByName(equipmentName);
        }

        Page<Equipment> equipments = equipmentRepository.findEquipments(modelNameIds, pageable);
        List<EquipmentResponse> collect = equipments.stream()
                .map(e -> new EquipmentResponse(e, this.getSpringProxy().findModelNameById(e.getModelNameId()).getDecryptedModelName()))
                .collect(Collectors.toList());
        return new PageImpl<>(collect, pageable, equipments.getSize());
    }


    @Cacheable("modelNames") // 캐싱 @CacheEvict("modelNames")
    public List<ModelNameResponse> findModelNames() {
        List<ModelName> modelNames = modelNameRepository.findAll();
        List<ModelNameResponse> collect = modelNames.stream()
                .map(ModelNameResponse::new)
                .collect(Collectors.toList());
        return collect;
    }

    @Cacheable(value = "modelNames", key = "#id")
    public ModelNameResponse findModelNameById(String id) {
        ModelName modelName = modelNameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 형식승인명이 존재하지 않습니다."));
//        ModelName modelName = modelNameRepository.findByModelNameIdAndModelName(id, name);
        return new ModelNameResponse(modelName);
    }

    @Cacheable(value = "modelNames", key = "#name")
    public List<String> findModelNameIdsByName(String name) {
        // 테이블에는 암호화 된 데이터가 들어 있으니까 이렇게는 조회 불가
//        ModelName modelName = modelNameRepository.findByModelName(name)
//                .orElseThrow(() -> new EntityNotFoundException("해당하는 형식승인명이 존재하지 않습니다."));
//        return new ModelNameResponse(modelName);

        // 복호화 된 전체 데이터 중에서 찾은 결과값을 name을 키로 캐싱
        List<String> modelNameIds = new ArrayList<>();
        if(name != null && !name.equals("")){
            List<ModelNameResponse> modelNameResponseList = this.getSpringProxy().findModelNames();
            for (ModelNameResponse modelNameResponse : modelNameResponseList) {
                if (modelNameResponse.getDecryptedModelName().contains(name)) {
                    modelNameIds.add(modelNameResponse.getModelNameId());
                }
            }
        }
        return modelNameIds;
    }
}
