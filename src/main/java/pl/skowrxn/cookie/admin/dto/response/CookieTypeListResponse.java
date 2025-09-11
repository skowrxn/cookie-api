package pl.skowrxn.cookie.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.skowrxn.cookie.common.dto.CookieTypeDTO;

import java.util.List;

@Data
public class CookieTypeListResponse {

    private List<CookieTypeDTO> cookieTypes;

}
