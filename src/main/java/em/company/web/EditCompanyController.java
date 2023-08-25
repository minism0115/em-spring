package em.company.web;

import em.company.application.EditCompanyService;
import em.company.dto.CreateCompanyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "회사 편집")
@Slf4j
@RequestMapping("/api/company")
@RestController
@RequiredArgsConstructor
public class EditCompanyController {

    private final EditCompanyService editCompanyService;

    @Operation(summary = "단건 등록 API")
    @PostMapping
    @CacheEvict(value = "equipmentName", allEntries = true)
    public ResponseEntity createCompany(@Valid @RequestBody CreateCompanyRequest request) {
        editCompanyService.createCompany(request);
        return new ResponseEntity(HttpStatus.OK);
    }
}
