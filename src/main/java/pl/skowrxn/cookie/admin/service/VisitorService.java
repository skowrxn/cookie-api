package pl.skowrxn.cookie.admin.service;

import pl.skowrxn.cookie.admin.dto.VisitorDTO;
import pl.skowrxn.cookie.admin.dto.response.VisitorListResponse;
import pl.skowrxn.cookie.consent.entity.ConsentStatus;

import java.util.UUID;

public interface VisitorService {

    VisitorListResponse getVisitors(UUID websiteId, Integer page, Integer size);

    VisitorDTO getVisitorById(UUID websiteId, UUID id);

    VisitorDTO getVisitorByConsentId(UUID websiteId, String consentId);

    Integer getConsentsCount(UUID websiteId, ConsentStatus consentStatus);

}
