package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.*;

@Entity
@Table(name = "cookies")
@Getter
@Setter
@NoArgsConstructor
public class Cookie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "domain")
    private String domain;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "duration")
    private String duration;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cookie_type_id", nullable = false)
    private CookieType cookieType;

    public Cookie(String name, String domain, String description, String duration, CookieType cookieType) {
        this.name = name;
        this.domain = domain;
        this.description = description;
        this.duration = duration;
        this.cookieType = cookieType;
    }
}
