package pl.skowrxn.cookie.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteDTO {

    private UUID id;
    private String key;
    private String domain;
    private UUID userId;
    private Instant lastSuccessfulScanTime;
    private Boolean isBannerActive;

}
