package pl.skowrxn.cookie.consent.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.repository.WebsiteRepository;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;
import pl.skowrxn.cookie.consent.dto.ConsentLogDTO;
import pl.skowrxn.cookie.consent.dto.ConsentRequest;
import pl.skowrxn.cookie.consent.entity.ConsentLog;
import pl.skowrxn.cookie.consent.entity.ConsentType;
import pl.skowrxn.cookie.consent.entity.Visitor;
import pl.skowrxn.cookie.consent.repository.ConsentTypeRepository;
import pl.skowrxn.cookie.consent.repository.VisitorRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsentServiceImpl implements ConsentService {

    private ConsentTypeRepository consentTypeRepository;
    private VisitorRepository visitorRepository;
    private WebsiteRepository websiteRepository;

    public void saveConsent(ConsentRequest request, String ip, String userAgent) {
        String siteKey = request.getKey();
        Website website = websiteRepository.findWebsiteByKey(siteKey)
                .orElseThrow(() -> new ResourceNotFoundException("website", "key", siteKey));

        String token = request.getConsent_id();
        List<ConsentLog> consentLogs = new ArrayList<>();

        Visitor visitor = visitorRepository.findVisitorByToken(token).orElseGet(Visitor::new);
        //TODO: if visitor exists, only update existing and different consent statuses
        //  + if userAgent is null, leave existing one

        visitor.setUserAgent(userAgent);
        visitor.setIp(ip);
        visitor.setToken(token);

        for (ConsentLogDTO consentLogDTO : request.getLog()) {
            String consentTypeKey = consentLogDTO.getName();
            ConsentType consentType = consentTypeRepository.findConsentTypeByKey(consentTypeKey).orElseThrow(() ->
                    new ResourceNotFoundException("consentType", "key", consentTypeKey));
            Boolean status = consentLogDTO.getStatus().equalsIgnoreCase("yes");

            ConsentLog consentLog = new ConsentLog();
            consentLog.setConsentType(consentType);
            consentLog.setStatus(status);
            consentLog.setVisitor(visitor);
            consentLogs.add(consentLog);
        }
        visitor.setConsentLogs(consentLogs);
        visitorRepository.save(visitor);
    }

}
