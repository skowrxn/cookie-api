package pl.skowrxn.cookie.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.skowrxn.cookie.admin.service.ConsentExportService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/consents")
@AllArgsConstructor
public class ConsentExportController {

    private ConsentExportService consentExportService;

    @GetMapping
    public ResponseEntity<Resource> exportConsents(@RequestParam String siteId) {
        try {
            byte[] csvData = consentExportService.exportToCsv(siteId);
            ByteArrayResource resource = new ByteArrayResource(csvData);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=consents.csv")
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
