package em.equipment.web;

import em.equipment.application.EditEquipmentService;
import em.equipment.dto.CreateEquipmentRequest;
import em.equipment.dto.UpdateEquipmentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "장비 편집")
@Slf4j
@RequestMapping("/api/equipment")
@RestController
@RequiredArgsConstructor
public class EditEquipmentController {

    private final EditEquipmentService editEquipmentService;

    @Operation(summary = "단건 등록 API")
    @PostMapping
    public ResponseEntity createEquipment(@RequestBody @Valid CreateEquipmentRequest request){
        editEquipmentService.createEquipment(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "단건 등록 API - 첨부파일")
    @PostMapping(value = {"/file"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createEquipmentWithFiles(@RequestBody @Valid CreateEquipmentRequest request,
                                                   @RequestPart(value = "files", required = false)List<MultipartFile> files){
        editEquipmentService.createEquipmentWithFiles(request, files);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "단건 수정 API")
    @PutMapping("/equipmentId/equipmentVersion")
    public ResponseEntity updateEquipment(@PathVariable("equipmentId") Long equipmentId,
                                          @PathVariable("equipmentVersion") Integer equipmentVersion,
                                          @RequestBody @Valid UpdateEquipmentRequest request){
        editEquipmentService.updateEquipment(equipmentId, equipmentVersion, request);
        return new ResponseEntity(HttpStatus.OK);
    }
}
