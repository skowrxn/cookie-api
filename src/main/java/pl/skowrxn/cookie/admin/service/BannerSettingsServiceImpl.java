package pl.skowrxn.cookie.admin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.dto.BannerSettingsDTO;
import pl.skowrxn.cookie.admin.entity.BannerSettings;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.repository.BannerSettingsRepository;
import pl.skowrxn.cookie.admin.repository.WebsiteRepository;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BannerSettingsServiceImpl implements BannerSettingsService {

    private final BannerSettingsRepository bannerSettingsRepository;
    private final WebsiteRepository websiteRepository;

    private final ModelMapper modelMapper;

    @Override
    public BannerSettings getDefaultSettings() {
        BannerSettings bannerSettings = new BannerSettings();
        bannerSettings.setBannerTitleText("We use cookies");
        bannerSettings.setBannerDescriptionText("We use cookies to ensure you get the best experience on our website.");
        bannerSettings.setAcceptAllButtonText("Accept");
        bannerSettings.setRejectAllButtonText("Reject");
        bannerSettings.setCustomiseCookiesButtonText("Customize");
        bannerSettings.setSaveButtonText("Save settings");
        bannerSettings.setTextColor("#000000");
        bannerSettings.setPrimaryColor("#187bcd");
        return bannerSettingsRepository.save(bannerSettings);
    }

    @Override
    public BannerSettingsDTO getBannerSettingsByWebsiteId(UUID websiteId) {
        Website website = websiteRepository.findById(websiteId).orElseThrow(() ->
                new ResourceNotFoundException("Website", "id", websiteId.toString()));
        BannerSettings bannerSettings = bannerSettingsRepository.findById(website.getBannerSettings().getId()).
                orElseThrow(() -> new ResourceNotFoundException("BannerSettings", "id",
                        website.getBannerSettings().getId().toString()));
        return modelMapper.map(bannerSettings, BannerSettingsDTO.class);
    }

    @Override
    @Transactional
    public BannerSettingsDTO updateBannerSettings(UUID websiteId, BannerSettingsDTO bannerSettingsDTO) {
        Website website = websiteRepository.findById(websiteId).orElseThrow(() ->
                new ResourceNotFoundException("Website", "id", websiteId.toString()));
        BannerSettings bannerSettings = bannerSettingsRepository.findById(website.getBannerSettings().getId()).
                orElseThrow(() -> new ResourceNotFoundException("BannerSettings", "id",
                        website.getBannerSettings().getId().toString()));
        bannerSettings.setBannerTitleText(bannerSettingsDTO.getBannerTitleText());
        bannerSettings.setBannerDescriptionText(bannerSettingsDTO.getBannerDescriptionText());
        bannerSettings.setAcceptAllButtonText(bannerSettingsDTO.getAcceptAllButtonText());
        bannerSettings.setRejectAllButtonText(bannerSettingsDTO.getRejectAllButtonText());
        bannerSettings.setCustomiseCookiesButtonText(bannerSettingsDTO.getCustomiseCookiesButtonText());
        bannerSettings.setSaveButtonText(bannerSettingsDTO.getSaveButtonText());
        bannerSettings.setTextColor(bannerSettingsDTO.getTextColor());
        bannerSettings.setPrimaryColor(bannerSettingsDTO.getPrimaryColor());
        BannerSettings updatedBannerSettings = bannerSettingsRepository.save(bannerSettings);
        return modelMapper.map(updatedBannerSettings, BannerSettingsDTO.class);
    }
}
