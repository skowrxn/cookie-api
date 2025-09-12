package pl.skowrxn.cookie.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CookieTypeRepository extends JpaRepository<CookieType, UUID> {

    Optional<CookieType> findCookieTypeById(UUID id);

    Optional<CookieType> findCookieTypeByKey(String key);

    Optional<CookieType> findCookieTypeByWebsiteAndKey(Website website, String key);

    List<CookieType> findCookieTypesByWebsite(Website website);

}
