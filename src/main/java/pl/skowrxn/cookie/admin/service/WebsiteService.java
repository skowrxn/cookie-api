package pl.skowrxn.cookie.admin.service;

import pl.skowrxn.cookie.admin.dto.WebsiteDTO;
import pl.skowrxn.cookie.admin.dto.response.WebsiteDetailsResponse;
import pl.skowrxn.cookie.admin.entity.User;

import java.util.List;
import java.util.UUID;

public interface WebsiteService {

    void deleteWebsiteById(UUID id);

    WebsiteDTO getWebsiteById(UUID id);

    WebsiteDTO getWebsiteByKey(String key);

    WebsiteDTO createWebsite(String domain, User user);

    WebsiteDTO setBannerStatus(UUID id, boolean isActive);

    WebsiteDetailsResponse getWebsiteDetailsById(UUID id);

    List<WebsiteDTO> getWebsitesByUserId(UUID id);

}
