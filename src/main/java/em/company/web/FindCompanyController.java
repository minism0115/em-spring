package em.company.web;

import em.company.application.FindCompanyService;
import em.company.dto.CompanyResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회사 조회")
@Slf4j
@RequestMapping("/api/company")
@RestController
@RequiredArgsConstructor
public class FindCompanyController {

    private final FindCompanyService findCompanyService;

    @Operation(summary = "")
    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> findCompanies(
            @RequestParam(required = false) String companyName,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        Page<CompanyResponse> responses = findCompanyService.findCompanies(companyName, pageable);
        return new ResponseEntity(responses, HttpStatus.OK);
    }
}
