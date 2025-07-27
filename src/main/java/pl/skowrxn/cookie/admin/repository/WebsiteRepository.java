package pl.skowrxn.cookie.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.admin.entity.Website;

import java.util.UUID;

public interface WebsiteRepository extends JpaRepository<Website, UUID> {
}
