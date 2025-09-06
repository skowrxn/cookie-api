package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.skowrxn.cookie.consent.entity.CookieType;
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

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String domain;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "banner_settings", nullable = false)
    private BannerSettings bannerSettings;

    @OneToMany(mappedBy = "website", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<CookieType> cookieTypes;

    @OneToMany(mappedBy = "website", fetch = FetchType.LAZY)
    private List<Visitor> visitors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    @Column
    private Instant lastSuccessfulScanTime;

    @Column(nullable = false)
    private Boolean isBannerActive;

}
