package pl.skowrxn.cookie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.dto.WebsiteScanDTO;
import pl.skowrxn.cookie.admin.service.WebsiteScanService;

import java.util.UUID;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1/admin/websites/{websiteId}/scan")
@RequiredArgsConstructor
public class CookieScanController {

    private final WebsiteScanService service;

    @PostMapping
    public ResponseEntity<WebsiteScanDTO> scanWebsite(@RequestParam UUID websiteId)  {
        WebsiteScanDTO scanResult = service.scanCookies(websiteId);
        return ResponseEntity.ok(scanResult);
    }
}
