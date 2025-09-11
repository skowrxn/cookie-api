package pl.skowrxn.cookie.common.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.dto.request.CookieTypeRequestDTO;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;
import pl.skowrxn.cookie.common.entity.CookieType;
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
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CookieTypeServiceImpl.class);

    @Override
    public void initializeCookieTypes() {
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
                    newCookieType.setDescription(description);
                    cookieTypeRepository.save(newCookieType);
                    logger.debug("Initialized new cookie type: {}", cookieTypeName);
                });
    }

    @Override
    public List<CookieTypeDTO> getAllCookieTypes() {
        return cookieTypeRepository.findAll().stream()
                .map(cookieType -> modelMapper.map(cookieType, CookieTypeDTO.class))
                .toList();
    }

    @Override
    public CookieTypeDTO getCookieTypeById(UUID id) {
        CookieType cookieType = cookieTypeRepository.findCookieTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CookieType", "id", id.toString()));
        return modelMapper.map(cookieType, CookieTypeDTO.class);
    }

    @Override
    public Optional<CookieType> findCookieTypeByKey(String key) {
        return cookieTypeRepository.findCookieTypeByKey(key);
    }

    @Override
    public CookieTypeDTO updateCookieType(UUID id, CookieTypeRequestDTO cookieTypeDTO) {
        CookieType existingCookieType = cookieTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CookieType", "id", id.toString()));

        existingCookieType.setName(cookieTypeDTO.getName());
        existingCookieType.setKey(cookieTypeDTO.getKey());
        existingCookieType.setDescription(cookieTypeDTO.getDescription());

        CookieType updatedCookieType = cookieTypeRepository.save(existingCookieType);
        return modelMapper.map(updatedCookieType, CookieTypeDTO.class);
    }


    @Override
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
    public void deleteCookieType(UUID id) {
        CookieType cookieType = cookieTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CookieType", "id", id.toString()));
        cookieTypeRepository.delete(cookieType);
    }

    @Override
    public CookieTypeDTO createCookieType(CookieTypeRequestDTO cookieTypeRequestDTO) {
        CookieType cookieType = new CookieType();
        cookieType.setName(cookieTypeRequestDTO.getName());
        cookieType.setKey(cookieTypeRequestDTO.getKey());
        cookieType.setDescription(cookieTypeRequestDTO.getDescription());
        cookieType.setCookies(new HashSet<>());
        CookieType savedCookieType = cookieTypeRepository.save(cookieType);
        return modelMapper.map(savedCookieType, CookieTypeDTO.class);
    }

    @Override
    public void saveCookieType(CookieType cookieType) {
        cookieTypeRepository.save(cookieType);
    }
}
