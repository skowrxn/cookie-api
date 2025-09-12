package pl.skowrxn.cookie.common.service;

import pl.skowrxn.cookie.admin.dto.request.CookieTypeRequestDTO;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CookieTypeService {

    void initializeCookieTypes(Website website);

    List<CookieTypeDTO> getAllCookieTypes(UUID websiteId);

    CookieTypeDTO getCookieTypeById(UUID id);

    Optional<CookieType> findCookieTypeByKey(UUID websiteId, String key);

    Optional<CookieType> findCookieTypeByKey(Website website, String key);

    CookieTypeDTO updateCookieType(UUID id, CookieTypeRequestDTO cookieTypeDTO);

    void updateCookieTypes(List<CookieType> cookieTypes);

    void deleteCookieType(UUID id);

    CookieTypeDTO createCookieType(UUID websiteId, CookieTypeRequestDTO cookieTypeRequestDTO);

    void saveCookieType(CookieType cookieType);

}
