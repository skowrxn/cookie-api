package pl.skowrxn.cookie.admin.service;

import pl.skowrxn.cookie.admin.dto.BannerSettingsDTO;
import pl.skowrxn.cookie.admin.entity.BannerSettings;

import java.util.UUID;

public interface BannerSettingsService {

    BannerSettings getDefaultSettings();

    BannerSettingsDTO getBannerSettingsByWebsiteId(UUID websiteId);

    BannerSettingsDTO updateBannerSettings(UUID websiteId, BannerSettingsDTO bannerSettingsDTO);

}
