package pl.skowrxn.cookie.consent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.repository.WebsiteRepository;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;
import pl.skowrxn.cookie.common.service.CookieTypeService;
import pl.skowrxn.cookie.consent.dto.ConsentLogDTO;
import pl.skowrxn.cookie.consent.dto.ConsentRequest;
import pl.skowrxn.cookie.consent.entity.ConsentLog;
import pl.skowrxn.cookie.common.entity.CookieType;
import pl.skowrxn.cookie.consent.entity.ConsentStatus;
import pl.skowrxn.cookie.consent.entity.Visitor;
import pl.skowrxn.cookie.consent.repository.VisitorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsentServiceImpl implements ConsentService {

    private final CookieTypeService cookieTypeService;
    private final VisitorRepository visitorRepository;
    private final WebsiteRepository websiteRepository;

    @Override
    @Transactional
    public void saveConsent(ConsentRequest request, String ip, String userAgent) {
        Website website = findWebsiteByKey(request.getKey());
        Visitor visitor = findOrCreateVisitor(request.getConsent_id(), ip, userAgent);

        List<ConsentLog> consentLogs = createConsentLogs(request.getLog(), website, visitor);
        ConsentStatus consentStatus = determineConsentStatus(consentLogs);

        visitor.setWebsite(website);
        visitor.setStatus(consentStatus);
        visitor.getConsentLogs().clear();
        visitor.getConsentLogs().addAll(consentLogs);
        visitorRepository.save(visitor);
    }

    private Website findWebsiteByKey(String siteKey) {
        return websiteRepository.findWebsiteByKey(siteKey)
                .orElseThrow(() -> new ResourceNotFoundException("website", "key", siteKey));
    }

    private Visitor findOrCreateVisitor(String token, String ip, String userAgent) {
        Visitor visitor = visitorRepository.findVisitorByToken(token).orElseGet(Visitor::new);
        visitor.setUserAgent(userAgent == null ? visitor.getUserAgent() : userAgent);
        visitor.setIp(ip);
        visitor.setToken(token);
        if (visitor.getConsentLogs() == null) {
            visitor.setConsentLogs(new ArrayList<>());
        }
        return visitor;
    }

    private List<ConsentLog> createConsentLogs(List<ConsentLogDTO> logDTOs, Website website, Visitor visitor) {
        return logDTOs.stream()
                .map(dto -> createConsentLog(dto, website, visitor))
                .collect(Collectors.toList());
    }

    private ConsentLog createConsentLog(ConsentLogDTO dto, Website website, Visitor visitor) {
        String cookieTypeKey = dto.getName();
        CookieType cookieType = cookieTypeService.findCookieTypeByKey(website, cookieTypeKey)
                .orElseThrow(() -> new ResourceNotFoundException("CookieType", "key", cookieTypeKey));

        boolean status = "yes".equalsIgnoreCase(dto.getStatus());

        ConsentLog consentLog = new ConsentLog();
        consentLog.setCookieType(cookieType);
        consentLog.setStatus(status);
        consentLog.setVisitor(visitor);
        return consentLog;
    }

    private ConsentStatus determineConsentStatus(List<ConsentLog> consentLogs) {
        boolean anyAccepted = consentLogs.stream().anyMatch(ConsentLog::getStatus);
        boolean allAccepted = consentLogs.stream().allMatch(ConsentLog::getStatus);

        if (!anyAccepted) {
            return ConsentStatus.REJECTED_ALL;
        } else if (allAccepted) {
            return ConsentStatus.ACCEPTED_ALL;
        }
        return ConsentStatus.PARTIAL;
    }

}
