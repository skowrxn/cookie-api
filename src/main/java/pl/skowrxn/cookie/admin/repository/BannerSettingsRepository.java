package pl.skowrxn.cookie.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.admin.entity.BannerSettings;

import java.util.UUID;

public interface BannerSettingsRepository extends JpaRepository<BannerSettings, UUID> {
}
