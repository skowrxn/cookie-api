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

    private String id;
    private String key;
    private String domain;
    private UUID userId;
    private BannerSettingsDTO bannerSettings;
    private Instant lastSuccessfulScanTime;
    private Boolean isBannerActive;

}
