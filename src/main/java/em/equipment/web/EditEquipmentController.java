package em.equipment.web;

import em.equipment.application.EditEquipmentService;
import em.equipment.domain.Equipment;
import em.equipment.dto.CreateEquipmentRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/equipment")
@RestController
@RequiredArgsConstructor
public class EditEquipmentController {

    private final EditEquipmentService editEquipmentService;

    @Operation(summary = "")
    @PostMapping
    public CreateEquipmentResponse createEquipment(@RequestBody @Valid CreateEquipmentRequest createEquipmentRequest){
        Equipment equipment = editEquipmentService.createEquipment(createEquipmentRequest);
        return new CreateEquipmentResponse(equipment.getCompositeId());
    }

    @Data
    @AllArgsConstructor
    static class CreateEquipmentResponse {
        private Equipment.EquipmentId equipmentId;
    }
}
