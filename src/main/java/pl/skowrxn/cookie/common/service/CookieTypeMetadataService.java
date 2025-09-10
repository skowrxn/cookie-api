package pl.skowrxn.cookie.common.service;

import java.util.Map;

public interface CookieTypeMetadataService {


    String getKey(String cookieTypeName);

    String getDescription(String cookieTypeName);

    Map<String, String> getAllCookieTypes();

    Map<String, String> getAllDescriptions();
}
