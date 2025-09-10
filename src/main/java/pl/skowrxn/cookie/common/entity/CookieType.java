package pl.skowrxn.cookie.common.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.skowrxn.cookie.admin.entity.Cookie;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.entity.WebsiteScan;

import java.util.ArrayList;
import java.util.List;
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "cookie_type_cookies",
            joinColumns = @JoinColumn(name = "cookie_type_id"),
            inverseJoinColumns = @JoinColumn(name = "cookie_id")
    )
    private List<Cookie> cookies;

    @JoinColumn(name = "website_scan")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private WebsiteScan websiteScan;

    public CookieType(String name, String key, String description) {
        this.name = name;
        this.key = key;
        this.description = description;
        this.cookies = new ArrayList<>();
    }
}
