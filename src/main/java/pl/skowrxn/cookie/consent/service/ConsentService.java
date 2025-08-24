package pl.skowrxn.cookie.consent.service;

import pl.skowrxn.cookie.consent.dto.ConsentRequest;

public interface ConsentService {

    void saveConsent(ConsentRequest request, String ip, String userAgent);

}
