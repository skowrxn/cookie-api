package pl.skowrxn.cookie.admin.dto.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CookieTypeRequestDTO {

    private UUID id;
    private String name;
    private String key;
    private String description;

}
