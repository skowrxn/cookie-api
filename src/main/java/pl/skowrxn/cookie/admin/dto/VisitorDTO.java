package pl.skowrxn.cookie.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.skowrxn.cookie.admin.dto.response.ConsentLogResponseDTO;
import pl.skowrxn.cookie.consent.entity.ConsentStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDTO {

    private UUID id;
    private String consentId;
    private String ip;
    private String userAgent;
    private Instant lastUpdatedTime;
    private List<ConsentLogResponseDTO> consentLogs;
    private ConsentStatus status;

}
