package pl.skowrxn.cookie.consent.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.consent.dto.ConsentRequest;
import pl.skowrxn.cookie.consent.service.ConsentService;

@RestController
@RequestMapping("/api/v1/public/consents")
@AllArgsConstructor
public class ConsentController {

    private ConsentService consentService;

    @PostMapping
    public ResponseEntity<?> saveConsent(
            @RequestBody ConsentRequest consentRequest,
            HttpServletRequest servletRequest
    ) {
        String ip = servletRequest.getRemoteAddr();
        String userAgent = servletRequest.getHeader("User-Agent");
        consentService.saveConsent(consentRequest, ip, userAgent);
        return ResponseEntity.ok().build();
    }

}
