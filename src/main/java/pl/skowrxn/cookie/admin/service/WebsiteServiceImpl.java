package pl.skowrxn.cookie.admin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.dto.WebsiteDTO;
import pl.skowrxn.cookie.admin.entity.User;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.repository.WebsiteRepository;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;
import pl.skowrxn.cookie.common.service.CookieTypeService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebsiteServiceImpl implements WebsiteService {

    private final WebsiteRepository websiteRepository;
    private final CookieTypeService cookieTypeService;

    private final ModelMapper modelMapper;

    @Override
    public void deleteWebsiteDataById(UUID id) {
        Website website = websiteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Website", "id", id.toString())
        );
        websiteRepository.delete(website);
    }

    @Override
    public WebsiteDTO getWebsiteById(UUID id) {
        Website website = websiteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Website", "id", id.toString())
        );
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
    public WebsiteDTO setLastSuccessfulScanTime(UUID id, Instant time) {
        Website website = websiteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Website", "id", id.toString())
        );
        website.setLastSuccessfulScanTime(time);
        websiteRepository.save(website);
        return mapToDTO(website);
    }

    @Override
    public WebsiteDTO setBannerStatus(UUID id, boolean isActive) {
        Website website = websiteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Website", "id", id.toString())
        );
        website.setIsBannerActive(isActive);
        websiteRepository.save(website);
        return mapToDTO(website);
    }

    @Override
    public WebsiteDTO createWebsite(String domain, User user) {
        Website website = new Website();
        website.setKey("");
        website.setVisitors(new ArrayList<>());
        website.setDomain(domain);
        website.setUser(user);
        website = websiteRepository.save(website);
        cookieTypeService.initializeCookieTypes(website);
        website.setIsBannerActive(true);
        //TODO initialize banner settings
        website = websiteRepository.save(website);
        return mapToDTO(website);
    }

    private WebsiteDTO mapToDTO(Website website) {
        return modelMapper.map(website, WebsiteDTO.class);
    }
}
