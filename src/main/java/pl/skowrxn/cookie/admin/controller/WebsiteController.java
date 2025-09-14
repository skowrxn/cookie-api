package pl.skowrxn.cookie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.auth.AuthUtil;
import pl.skowrxn.cookie.admin.dto.WebsiteDTO;
import pl.skowrxn.cookie.admin.dto.response.WebsiteDetailsResponse;
import pl.skowrxn.cookie.admin.entity.User;
import pl.skowrxn.cookie.admin.service.WebsiteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/websites")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class WebsiteController {

    private final WebsiteService websiteService;
    private final AuthUtil authUtil;

    @GetMapping("/{id}")
    public ResponseEntity<WebsiteDetailsResponse> getWebsiteDetailsById(@PathVariable UUID id) {
        return ResponseEntity.ok(websiteService.getWebsiteDetailsById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebsite(@PathVariable UUID id) {
        websiteService.deleteWebsiteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/banner-status")
    public ResponseEntity<WebsiteDTO> updateBannerStatus(@PathVariable UUID id,
                                                         @RequestParam boolean isActive) {
        return ResponseEntity.ok(websiteService.setBannerStatus(id, isActive));
    }

    @GetMapping
    public ResponseEntity<List<WebsiteDTO>> getAllWebsites() {
        User user = authUtil.getLoggedInUser();
        return ResponseEntity.ok(websiteService.getWebsitesByUserId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<WebsiteDTO> createWebsite(@RequestParam String domain) {
        User user = authUtil.getLoggedInUser();
        return ResponseEntity.ok(websiteService.createWebsite(domain, user));
    }

}
