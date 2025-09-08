package pl.skowrxn.cookie.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteScanDTO {

    private UUID id;
    private List<CookieTypeDTO> detectedCookieTypes;
    private Instant scanDate;
    private boolean isSuccessful;
    private int totalCookies;

}
