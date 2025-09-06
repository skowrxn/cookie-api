package pl.skowrxn.cookie.admin.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CookieDTO {

    private UUID id;
    private String name;
    private String domain;
    private String description;
    private String duration;
    private String type;
    
}
