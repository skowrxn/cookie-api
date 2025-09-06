package pl.skowrxn.cookie.admin.service;

import pl.skowrxn.cookie.admin.dto.WebsiteScanDTO;

public interface WebsiteScanService {

    WebsiteScanDTO scanCookies(String url);

}
