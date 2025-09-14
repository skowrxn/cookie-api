package pl.skowrxn.cookie.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsentLogResponseDTO {

    private UUID id;
    private CookieTypeDTO cookieType;
    private Boolean status;

}
