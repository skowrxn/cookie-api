package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import pl.skowrxn.cookie.consent.entity.CookieType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cookies")
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "cookie_cookie_types",
            joinColumns = @JoinColumn(name = "cookie_id"),
            inverseJoinColumns = @JoinColumn(name = "cookie_type_id")
    )
    private List<CookieType> cookieTypes;

    public Cookie(String name, String domain, String description, String duration) {
        this.name = name;
        this.domain = domain;
        this.description = description;
        this.duration = duration;
        this.cookieTypes = new ArrayList<>();
    }
}
