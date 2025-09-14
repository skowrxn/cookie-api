package pl.skowrxn.cookie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.dto.WebsiteScanDTO;
import pl.skowrxn.cookie.admin.service.WebsiteScanService;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1/admin/websites/{websiteId}/scans")
@RequiredArgsConstructor
public class WebsiteScanController {

    private final WebsiteScanService websiteScanService;

    @PostMapping
    public ResponseEntity<WebsiteScanDTO> scanWebsite(@PathVariable UUID websiteId)  {
        WebsiteScanDTO scanResult = websiteScanService.scanCookies(websiteId);
        return ResponseEntity.ok(scanResult);
    }

    @GetMapping
    public ResponseEntity<List<WebsiteScanDTO>> getAllScans(@PathVariable UUID websiteId)  {
        return ResponseEntity.ok(websiteScanService.getAllScans(websiteId));
    }

}
