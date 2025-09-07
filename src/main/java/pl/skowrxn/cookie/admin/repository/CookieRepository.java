package pl.skowrxn.cookie.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.admin.entity.Cookie;

import java.util.Optional;
import java.util.UUID;

public interface CookieRepository extends JpaRepository<Cookie, UUID> {

    Optional<Cookie> findByNameAndDomain(String cookieKey, String domain);
}
