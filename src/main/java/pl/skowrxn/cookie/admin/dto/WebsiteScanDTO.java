package pl.skowrxn.cookie.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.consent.entity.CookieType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteScanDTO {

    private UUID id;
    private List<CookieType> detectedCookieTypes;
    private Instant scanDate;
    private boolean isSuccessful;
    private int totalCookies;

}
