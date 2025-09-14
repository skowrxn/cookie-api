package pl.skowrxn.cookie.admin.service;

import pl.skowrxn.cookie.admin.dto.WebsiteScanDTO;

import java.util.List;
import java.util.UUID;

public interface WebsiteScanService {

    WebsiteScanDTO scanCookies(UUID websiteId);

    List<WebsiteScanDTO> getAllScans(UUID websiteId);

}
