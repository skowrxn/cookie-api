package pl.skowrxn.cookie.common.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.dto.request.CookieTypeRequestDTO;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.repository.WebsiteRepository;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;
import pl.skowrxn.cookie.common.entity.CookieType;
import pl.skowrxn.cookie.common.exception.BadRequestException;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;
import pl.skowrxn.cookie.common.repository.CookieTypeRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CookieTypeServiceImpl implements CookieTypeService {

    private final CookieTypeRepository cookieTypeRepository;
    private final CookieTypeMetadataService cookieTypeMetadataService;
    private final WebsiteRepository websiteRepository;

    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CookieTypeServiceImpl.class);

    @Override
    @Transactional
    public void initializeCookieTypes(Website website) {
        List<String> existingCookieTypes = cookieTypeRepository.findAll().stream().map(CookieType::getName).toList();
        Set<String> allCookieTypes = cookieTypeMetadataService.getAllCookieTypes().keySet();
        if(existingCookieTypes.size() == allCookieTypes.size()) return;
        allCookieTypes.stream()
                .filter(cookieTypeName -> !existingCookieTypes.contains(cookieTypeName))
                .forEach(cookieTypeName -> {
                    String key = cookieTypeMetadataService.getAllCookieTypes().get(cookieTypeName);
                    String description = cookieTypeMetadataService.getDescription(cookieTypeName);
                    CookieType newCookieType = new CookieType();
                    newCookieType.setName(cookieTypeName);
                    newCookieType.setKey(key);
                    newCookieType.setWebsite(website);
                    newCookieType.setDescription(description);
                    cookieTypeRepository.save(newCookieType);
                    logger.debug("Initialized new cookie type: {}", cookieTypeName);
                });
    }

    @Override
    public List<CookieTypeDTO> getAllCookieTypes(UUID websiteId) {
        Website website = getWebsiteByIdOrThrow(websiteId);
        return cookieTypeRepository.findCookieTypesByWebsite(website).stream()
                .map(cookieType -> modelMapper.map(cookieType, CookieTypeDTO.class))
                .toList();
    }

    @Override
    public CookieTypeDTO getCookieTypeById(UUID id) {
        CookieType cookieType = cookieTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CookieType", "id", id.toString()));
        return modelMapper.map(cookieType, CookieTypeDTO.class);
    }

    @Override
    public Optional<CookieType> findCookieTypeByKey(UUID websiteId, String key) {
        Website website = getWebsiteByIdOrThrow(websiteId);
        return cookieTypeRepository.findCookieTypeByWebsiteAndKey(website, key);
    }

    @Override
    public Optional<CookieType> findCookieTypeByKey(Website website, String key) {
        return cookieTypeRepository.findCookieTypeByWebsiteAndKey(website, key);
    }

    @Override
    @Transactional
    public CookieTypeDTO updateCookieType(UUID id, CookieTypeRequestDTO cookieTypeDTO) {
        CookieType existingCookieType = getCookieTypeOrThrow(id);
        Optional<CookieType> optionalDuplicate = cookieTypeRepository.findCookieTypeByKey(cookieTypeDTO.getKey());
        if (optionalDuplicate.isPresent() && !optionalDuplicate.get().getId().equals(cookieTypeDTO.getId())) {
            throw new BadRequestException("Cookie type with key " + cookieTypeDTO.getKey() + " already exists.");
        }

        existingCookieType.setName(cookieTypeDTO.getName());
        existingCookieType.setKey(cookieTypeDTO.getKey());
        existingCookieType.setDescription(cookieTypeDTO.getDescription());

        CookieType updatedCookieType = cookieTypeRepository.save(existingCookieType);
        return modelMapper.map(updatedCookieType, CookieTypeDTO.class);
    }


    @Override
    @Transactional
    public void updateCookieTypes(List<CookieType> cookieTypes) {
        Map<UUID, CookieType> cookieTypeMap = cookieTypes.stream()
                .collect(Collectors.toMap(CookieType::getId, Function.identity()));
        List<CookieType> existingCookieTypes = cookieTypeRepository.findAllById(cookieTypeMap.keySet());
        existingCookieTypes.forEach(existingCookieType -> {
            CookieType updatedCookieType = cookieTypeMap.get(existingCookieType.getId());
            existingCookieType.setName(updatedCookieType.getName());
            existingCookieType.setKey(updatedCookieType.getKey());
            existingCookieType.setDescription(updatedCookieType.getDescription());
        });
        cookieTypeRepository.saveAll(existingCookieTypes);
    }

    @Override
    @Transactional
    public void deleteCookieType(UUID id) {
        CookieType cookieType = getCookieTypeOrThrow(id);
        cookieTypeRepository.delete(cookieType);
    }

    @Override
    @Transactional
    public CookieTypeDTO createCookieType(UUID websiteId, CookieTypeRequestDTO cookieTypeRequestDTO) {
        Website website = getWebsiteByIdOrThrow(websiteId);
        if (cookieTypeRepository.existsCookieTypeByKey(cookieTypeRequestDTO.getKey())){
            throw new BadRequestException("Cookie type with key " + cookieTypeRequestDTO.getKey() + " already exists.");
        }
        CookieType cookieType = new CookieType();
        cookieType.setName(cookieTypeRequestDTO.getName());
        cookieType.setKey(cookieTypeRequestDTO.getKey());
        cookieType.setWebsite(website);
        cookieType.setDescription(cookieTypeRequestDTO.getDescription());
        cookieType.setCookies(new HashSet<>());
        CookieType savedCookieType = cookieTypeRepository.save(cookieType);
        return modelMapper.map(savedCookieType, CookieTypeDTO.class);
    }

    @Override
    @Transactional
    public void saveCookieType(CookieType cookieType) {
        Optional<CookieType> optionalDuplicate = cookieTypeRepository.findCookieTypeByKey(cookieType.getKey());
        if (optionalDuplicate.isPresent() && !optionalDuplicate.get().getId().equals(cookieType.getId())) {
            throw new BadRequestException("Cookie type with key " + cookieType.getKey() + " already exists.");
        }
        cookieTypeRepository.save(cookieType);
    }

    private Website getWebsiteByIdOrThrow(UUID websiteId) {
        return websiteRepository.findById(websiteId)
                .orElseThrow(() -> new ResourceNotFoundException("Website", "id", websiteId.toString()));
    }

    private CookieType getCookieTypeOrThrow(UUID id) {
        return cookieTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CookieType", "id", id.toString()));
    }


}
