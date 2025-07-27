package pl.skowrxn.cookie.consent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.consent.entity.Consent;

import java.util.UUID;

public interface ConsentRepository extends JpaRepository<Consent, UUID> {

}
