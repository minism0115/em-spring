package em.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Tag(name = "첨부파일")
@Slf4j
@RequestMapping("/api/file")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "첨부파일 업로드 API")
    public ResponseEntity uploadFiles(
            @RequestParam(name = "parent") Long parent,
            @RequestParam(name = "parentType") ParentType parentType,
            @RequestPart(name = "files") List<MultipartFile> files){
        fileService.uploadFiles(parent, parentType, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "첨부파일 목록 조회 API")
    public ResponseEntity<Map<ParentType, List<File>>> findFiles(
            @RequestParam Long parentId,
            @RequestPart ParentType parentType
    ){
        Map<ParentType, List<File>> result = fileService.findByParentIdAndParentType(parentId, parentType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    @Operation(summary = "첨부파일 단건 다운로드 API: 첨부파일 ID로 조회")
//    public ResponseEntity<Resource> downloadFile(
//            @PathVariable Long id
//    ){
//
//    }
}
