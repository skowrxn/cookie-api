package pl.skowrxn.cookie.admin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.dto.WebsiteDTO;
import pl.skowrxn.cookie.admin.entity.User;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.repository.WebsiteRepository;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;
import pl.skowrxn.cookie.common.service.CookieTypeService;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebsiteServiceImpl implements WebsiteService {

    private final WebsiteRepository websiteRepository;
    private final CookieTypeService cookieTypeService;
    private final BannerSettingsService bannerSettingsService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void deleteWebsiteById(UUID id) {
        Website website = getWebsiteEntityById(id);
        websiteRepository.delete(website);
    }

    @Override
    public WebsiteDTO getWebsiteById(UUID id) {
        Website website = getWebsiteEntityById(id);
        return mapToDTO(website);
    }

    @Override
    public WebsiteDTO getWebsiteByKey(String key) {
        Website website = websiteRepository.findWebsiteByKey(key).orElseThrow(
                () -> new ResourceNotFoundException("Website", "key", key)
        );
        return mapToDTO(website);
    }


    @Override
    @Transactional
    public WebsiteDTO setLastSuccessfulScanTime(UUID id, Instant time) {
        Website website = getWebsiteEntityById(id);
        website.setLastSuccessfulScanTime(time);
        websiteRepository.save(website);
        return mapToDTO(website);
    }

    @Override
    @Transactional
    public WebsiteDTO setBannerStatus(UUID id, boolean isActive) {
        Website website = getWebsiteEntityById(id);
        website.setIsBannerActive(isActive);
        websiteRepository.save(website);
        return mapToDTO(website);
    }

    @Override
    @Transactional
    public WebsiteDTO createWebsite(String domain, User user) {
        Website website = new Website();
        website.setKey(generateSiteKey());
        website.setVisitors(new ArrayList<>());
        website.setDomain(domain);
        website.setUser(user);
        website.setBannerSettings(bannerSettingsService.getDefaultSettings());
        website.setIsBannerActive(true);
        website = websiteRepository.save(website);
        cookieTypeService.initializeCookieTypes(website);
        return mapToDTO(website);
    }

    private Website getWebsiteEntityById(UUID id) {
        return websiteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Website", "id", id.toString())
        );
    }

    @Override
    public List<WebsiteDTO> getWebsitesByUserId(UUID id) {
        return websiteRepository.getWebsitesByUser_Id(id).stream().map(this::mapToDTO).toList();
    }

    private String generateSiteKey() {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 16; i++) {
            key.append(chars.charAt(random.nextInt(chars.length())));
        }
        return key.toString();
    }

    private WebsiteDTO mapToDTO(Website website) {
        return modelMapper.map(website, WebsiteDTO.class);
    }

}
