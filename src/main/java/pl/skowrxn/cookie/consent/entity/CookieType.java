package pl.skowrxn.cookie.consent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.admin.entity.Cookie;
import pl.skowrxn.cookie.admin.entity.Website;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cookie_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CookieType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String key;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "website_id")
    private Website website;

    @OneToMany(mappedBy = "cookieType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cookie> cookies;

}
