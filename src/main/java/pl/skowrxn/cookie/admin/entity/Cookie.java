package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import pl.skowrxn.cookie.consent.entity.CookieType;

import java.util.UUID;

@Entity
public class Cookie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String domain;

    @Column
    private String description;

    @Column
    private String duration;

    @ManyToOne
    @JoinColumn(name = "cookie_type_id")
    private CookieType cookieType;

}
