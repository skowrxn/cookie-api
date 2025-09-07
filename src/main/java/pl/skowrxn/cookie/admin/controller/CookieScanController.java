package pl.skowrxn.cookie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.dto.WebsiteScanDTO;
import pl.skowrxn.cookie.admin.service.WebsiteScanService;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1/admin/scan")
@RequiredArgsConstructor
public class CookieScanController {

    private final WebsiteScanService service;

    @PostMapping
    public ResponseEntity<?> scanWebsite(@RequestParam String site)  {
        WebsiteScanDTO scanResult = service.scanCookies(site);
        return ResponseEntity.ok(scanResult);
    }
}
