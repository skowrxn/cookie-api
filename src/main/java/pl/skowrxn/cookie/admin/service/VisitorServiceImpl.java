package pl.skowrxn.cookie.admin.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.dto.VisitorDTO;
import pl.skowrxn.cookie.admin.dto.VisitorListEntryDTO;
import pl.skowrxn.cookie.admin.dto.response.VisitorListResponse;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;
import pl.skowrxn.cookie.consent.entity.ConsentStatus;
import pl.skowrxn.cookie.consent.entity.Visitor;
import pl.skowrxn.cookie.consent.repository.VisitorRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final ModelMapper modelMapper;

    @Override
    public VisitorListResponse getVisitors(UUID websiteId, Integer page, Integer size) {
        Pageable pageDetails = PageRequest.of(page, size,
                Sort.by("lastUpdatedTime").descending());
        Page<Visitor> visitors = visitorRepository.findVisitorByWebsite_Id(websiteId, pageDetails);
        List<VisitorListEntryDTO> visitorDTOs = visitors.stream()
                .map(category -> this.modelMapper.map(category, VisitorListEntryDTO.class))
                .toList();

        VisitorListResponse visitorListResponse = new VisitorListResponse();
        visitorListResponse.setVisitors(visitorDTOs);
        visitorListResponse.setPage(page);
        visitorListResponse.setPageSize(size);
        visitorListResponse.setTotalPages(visitors.getTotalPages());
        visitorListResponse.setTotalElements(visitors.getTotalElements());
        visitorListResponse.setLastPage(visitors.isLast());

        return visitorListResponse;
    }

    @Override
    public VisitorDTO getVisitorById(UUID websiteId, UUID id) {
        Visitor visitor = visitorRepository.findVisitorByIdAndWebsite_Id(id, websiteId).orElseThrow(
                () -> new ResourceNotFoundException("Visitor", "id", id.toString())
        );
        return modelMapper.map(visitor, VisitorDTO.class);
    }

    @Override
    public VisitorDTO getVisitorByConsentId(UUID websiteId, String consentId) {
        Visitor visitor = visitorRepository.findVisitorByWebsite_IdAndConsentId(websiteId, consentId).orElseThrow(
                () -> new ResourceNotFoundException("Visitor", "consentId", consentId)
        );
        return modelMapper.map(visitor, VisitorDTO.class);
    }

    @Override
    public Integer getConsentsCount(UUID websiteId, ConsentStatus consentStatus) {
        return visitorRepository.countByWebsite_IdAndStatus(websiteId, consentStatus);
    }

}
