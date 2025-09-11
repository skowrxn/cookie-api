package pl.skowrxn.cookie.common.service;

import pl.skowrxn.cookie.admin.dto.request.CookieTypeRequestDTO;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CookieTypeService {

    void initializeCookieTypes();

    List<CookieTypeDTO> getAllCookieTypes();

    CookieTypeDTO getCookieTypeById(UUID id);

    Optional<CookieType> findCookieTypeByKey(String key);

    CookieTypeDTO updateCookieType(UUID id, CookieTypeRequestDTO cookieTypeDTO);

    void updateCookieTypes(List<CookieType> cookieTypes);

    void deleteCookieType(UUID id);

    CookieTypeDTO createCookieType(CookieTypeRequestDTO cookieTypeRequestDTO);

    void saveCookieType(CookieType cookieType);
}
