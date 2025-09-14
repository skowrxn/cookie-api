package pl.skowrxn.cookie.consent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.consent.entity.Visitor;

import java.util.Optional;
import java.util.UUID;

public interface VisitorRepository extends JpaRepository<Visitor, UUID> {

    Optional<Visitor> findVisitorByWebsite_IdAndConsentId(UUID websiteId, String consentId);

    Optional<Visitor> findVisitorByIdAndWebsite_Id(UUID visitorId, UUID websiteId);

    Page<Visitor> findVisitorByWebsite_Id(UUID websiteId, Pageable pageable);

}
