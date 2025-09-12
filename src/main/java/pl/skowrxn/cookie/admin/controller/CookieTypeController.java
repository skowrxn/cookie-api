package pl.skowrxn.cookie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.dto.request.CookieTypeRequestDTO;
import pl.skowrxn.cookie.admin.dto.response.CookieTypeListResponse;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;
import pl.skowrxn.cookie.common.service.CookieTypeService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/websites/{websiteId}/cookie-types")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class CookieTypeController {

    private final CookieTypeService cookieTypeService;

    @GetMapping
    public ResponseEntity<CookieTypeListResponse> getAllCookieTypes(@PathVariable UUID websiteId) {
        CookieTypeListResponse response = new CookieTypeListResponse();
        response.setCookieTypes(cookieTypeService.getAllCookieTypes(websiteId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CookieTypeDTO> getCookieTypeById(@PathVariable UUID id){
        CookieTypeDTO cookieTypeDTO = cookieTypeService.getCookieTypeById(id);
        return ResponseEntity.ok(cookieTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CookieTypeDTO> updateCookieType(@PathVariable UUID id, @RequestBody CookieTypeRequestDTO cookieTypeDTO) {
        CookieTypeDTO updatedCookieTypeDTO = cookieTypeService.updateCookieType(id, cookieTypeDTO);
        return ResponseEntity.ok(updatedCookieTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCookieType(@PathVariable UUID id) {
        cookieTypeService.deleteCookieType(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CookieTypeDTO> createCookieType(@PathVariable UUID websiteId, @RequestBody CookieTypeRequestDTO cookieTypeDTO) {
        CookieTypeDTO createCookieTypeDTO = cookieTypeService.createCookieType(websiteId, cookieTypeDTO);
        return ResponseEntity.ok(createCookieTypeDTO);
    }


}
