package pl.skowrxn.cookie.common.service;

import pl.skowrxn.cookie.common.dto.CookieTypeDTO;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.List;
import java.util.UUID;

public interface CookieTypeService {

    void initializeCookieTypes();

    List<CookieTypeDTO> getAllCookieTypes();

    CookieTypeDTO getCookieTypeByName(String name);

    CookieTypeDTO getCookieTypeByKey(String key);

    CookieTypeDTO updateCookieType(UUID id, CookieTypeDTO cookieTypeDTO);

    void updateCookieTypes(List<CookieType> cookieTypes);
}
