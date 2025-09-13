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

    @Column(name = "website_key", nullable = false, unique = true)
    private String key;

    @Column(name = "domain", nullable = false, unique = true)
    private String domain;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "banner_settings_id", nullable = false)
    private BannerSettings bannerSettings;

    @OneToMany(mappedBy = "website", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CookieType> cookieTypes;

    @OneToMany(mappedBy = "website", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Visitor> visitors;

    @Column(name = "last_successful_scan_time")
    private Instant lastSuccessfulScanTime;

    @Column(nullable = false, name = "is_banner_active")
    private Boolean isBannerActive;

}
