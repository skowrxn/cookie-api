package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "website_scans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteScan {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name="scan_time")
    private Instant scanTime;

    @Column(nullable = false, name="is_successful")
    private boolean isSuccessful;

    @Column
    @OneToMany(mappedBy = "websiteScan", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CookieType> detectedCookieTypes;

    @Column(nullable = false, name = "total_cookies")
    private int totalCookies;

}
