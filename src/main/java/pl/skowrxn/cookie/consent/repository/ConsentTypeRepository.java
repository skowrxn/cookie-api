package pl.skowrxn.cookie.consent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.consent.entity.ConsentType;

import java.util.Optional;
import java.util.UUID;

public interface ConsentTypeRepository extends JpaRepository<ConsentType, UUID> {

    Optional<ConsentType> findConsentTypeByKey(String key);

}
