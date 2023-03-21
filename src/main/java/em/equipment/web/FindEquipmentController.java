package em.equipment.web;

import em.common.Result;
import em.equipment.dto.EquipmentDto;
import em.equipment.dto.EquipmentSearchParam;
import em.equipment.infrastructure.CustomEquipmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class FindEquipmentController {

    private final CustomEquipmentRepository customEquipmentRepository;

    @Operation(summary = "")
    @GetMapping(value = "/equipment/{equipmentId}/{equipmentVersion}")
    public Result findEquipment(
            @PathVariable("equipmentId") Long equipmentId,
            @PathVariable("equipmentVersion") int equipmentVersion){
        EquipmentDto equipmentDto = customEquipmentRepository.findEquipment(equipmentId, equipmentVersion);
        return new Result(equipmentDto);
    }

    @Operation(summary = "")
    @GetMapping(value = "/equipment")
    public List<EquipmentDto> findEquipmentList(
            EquipmentSearchParam param){
        return customEquipmentRepository.findEquipmentList(param);
    }
}
