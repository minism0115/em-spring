package em.equipment.web;

import em.equipment.application.FindEquipmentService;
import em.equipment.dto.EquipmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "장비 조회")
@Slf4j
@RequestMapping("/api/equipment")
@RestController
@RequiredArgsConstructor
public class FindEquipmentController {

    private final FindEquipmentService findEquipmentService;

    @Operation(summary = "")
    @GetMapping(value = "/{equipmentId}/{equipmentVersion}")
    public ResponseEntity<EquipmentResponse> findEquipment(
            @PathVariable("equipmentId") Long equipmentId,
            @PathVariable("equipmentVersion") Integer equipmentVersion){
        EquipmentResponse response = findEquipmentService.findEquipment(equipmentId, equipmentVersion);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @Operation(summary = "")
    @GetMapping
    public ResponseEntity<Page<EquipmentResponse>> findEquipments(
            @RequestParam(required = false) String modeName,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable){
        Page<EquipmentResponse> responses = findEquipmentService.findEquipments(modeName, pageable);
        return new ResponseEntity(responses, HttpStatus.OK);
    }
}
