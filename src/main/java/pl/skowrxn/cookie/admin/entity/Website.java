package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.common.entity.CookieType;
import pl.skowrxn.cookie.consent.entity.Visitor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "websites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, name = "website_key")
    private String key;

    @Column(nullable = false, name = "domain")
    private String domain;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "banner_settings", nullable = false)
    private BannerSettings bannerSettings;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "website_cookie_types",
            joinColumns = @JoinColumn(name = "website_id"),
            inverseJoinColumns = @JoinColumn(name = "cookie_type_id")
    )
    private List<CookieType> cookieTypes;

    @OneToMany(mappedBy = "website", fetch = FetchType.LAZY)
    @JoinTable(
            name = "website_visitors",
            joinColumns = @JoinColumn(name = "website_id"),
            inverseJoinColumns = @JoinColumn(name = "visitor_id")
    )
    private List<Visitor> visitors;

    @Column(name = "last_successful_scan_time")
    private Instant lastSuccessfulScanTime;

    @Column(nullable = false, name = "is_banner_active")
    private Boolean isBannerActive;

}
