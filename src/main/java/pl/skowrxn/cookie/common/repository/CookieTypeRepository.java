package pl.skowrxn.cookie.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.Optional;
import java.util.UUID;

public interface CookieTypeRepository extends JpaRepository<CookieType, UUID> {

    Optional<CookieType> findCookieTypeByKey(String key);

}
