package pl.skowrxn.cookie.admin.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookieDTO {

    private UUID id;
    private String name;
    private String domain;
    private String description;
    private String duration;
    private String type;

    public CookieDTO(String cookieKey, String domain, String expiration, String description, String type) {
        this.name = cookieKey;
        this.domain = domain;
        this.duration = expiration;
        this.description = description;
        this.type = type;
    }
}
