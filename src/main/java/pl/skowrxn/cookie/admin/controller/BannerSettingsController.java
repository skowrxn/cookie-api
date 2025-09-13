package pl.skowrxn.cookie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.dto.BannerSettingsDTO;
import pl.skowrxn.cookie.admin.service.BannerSettingsService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/websites/{websiteId}/banner-settings")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class BannerSettingsController {

    private final BannerSettingsService bannerSettingsService;

    @GetMapping
    public ResponseEntity<BannerSettingsDTO> getBannerSettings(@PathVariable UUID websiteId) {
        BannerSettingsDTO bannerSettingsDTO = bannerSettingsService.getBannerSettingsByWebsiteId(websiteId);
        return ResponseEntity.ok(bannerSettingsDTO);
    }

    @PutMapping
    public ResponseEntity<BannerSettingsDTO> updateBannerSettings(@PathVariable UUID websiteId,
                                                                  @RequestBody BannerSettingsDTO bannerSettingsDTO) {
        BannerSettingsDTO updatedSettings = bannerSettingsService.updateBannerSettings(websiteId, bannerSettingsDTO);
        return ResponseEntity.ok(updatedSettings);
    }

}