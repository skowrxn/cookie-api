package pl.skowrxn.cookie.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.admin.entity.WebsiteScan;

import java.util.UUID;

public interface WebsiteScanRepository extends JpaRepository<WebsiteScan, UUID> {
}
