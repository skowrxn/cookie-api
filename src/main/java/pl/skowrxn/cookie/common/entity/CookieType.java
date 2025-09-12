package pl.skowrxn.cookie.common.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.skowrxn.cookie.admin.entity.Cookie;
import pl.skowrxn.cookie.admin.entity.Website;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cookie_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CookieType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "key")
    private String key;

    @Column(name = "description", length = 1024)
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "website_id", nullable = false)
    private Website website;

    @OneToMany(mappedBy = "cookieType", cascade = {CascadeType.ALL})
    private Set<Cookie> cookies;

    public CookieType(String name, String key, Website website, String description) {
        this.name = name;
        this.key = key;
        this.website = website;
        this.description = description;
        this.cookies = new HashSet<>();
    }
}
