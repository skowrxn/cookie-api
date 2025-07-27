package pl.skowrxn.cookie.consent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.consent.entity.UserConsent;

import java.util.UUID;

public interface UserConsentRepository extends JpaRepository<UserConsent, UUID> {

}
