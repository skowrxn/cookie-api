package pl.skowrxn.cookie.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.dto.VisitorDTO;
import pl.skowrxn.cookie.admin.dto.response.VisitorListResponse;
import pl.skowrxn.cookie.admin.service.VisitorService;

import java.util.UUID;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1/admin/websites/{websiteId}/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping
    public ResponseEntity<VisitorListResponse> getVisitors(
            @PathVariable UUID websiteId,
            @RequestParam(name="page", defaultValue = "0") Integer page,
            @RequestParam(name="pageSize", defaultValue = "20") Integer pageSize
    ) {
        VisitorListResponse response = visitorService.getVisitors(websiteId, page, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{visitorId}")
    public ResponseEntity<VisitorDTO> getVisitorById(
            @PathVariable UUID websiteId,
            @PathVariable UUID visitorId
    ) {
        VisitorDTO visitor = visitorService.getVisitorById(websiteId, visitorId);
        return ResponseEntity.ok(visitor);
    }


}
