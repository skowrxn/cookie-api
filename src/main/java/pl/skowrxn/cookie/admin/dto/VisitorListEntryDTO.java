package pl.skowrxn.cookie.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.skowrxn.cookie.consent.entity.ConsentStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorListEntryDTO {

    private UUID id;
    private String consentId;
    private Instant lastUpdatedTime;
    private ConsentStatus status;

}
