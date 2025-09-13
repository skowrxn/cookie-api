package pl.skowrxn.cookie.admin.service;

import pl.skowrxn.cookie.admin.dto.WebsiteDTO;
import pl.skowrxn.cookie.admin.entity.User;
import pl.skowrxn.cookie.admin.entity.Website;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface WebsiteService {

    void deleteWebsiteById(UUID id);

    WebsiteDTO getWebsiteById(UUID id);

    WebsiteDTO getWebsiteByKey(String key);

    WebsiteDTO createWebsite(String domain, User user);

    WebsiteDTO setLastSuccessfulScanTime(UUID id, Instant time);

    WebsiteDTO setBannerStatus(UUID id, boolean isActive);

    List<WebsiteDTO> getWebsitesByUserId(UUID id);

    Website getWebsiteEntityById(UUID id);

}
