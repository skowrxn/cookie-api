package pl.skowrxn.cookie.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.skowrxn.cookie.admin.dto.CookieDTO;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookieTypeDTO {
    private UUID id;
    private String name;
    private String key;
    private String description;
    private List<CookieDTO> cookies;
}

