package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import pl.skowrxn.cookie.consent.entity.CookieType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteScan {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @Column
    private List<CookieType> detectedCookieTypes;

    @Column(nullable = false)
    @CreatedDate
    private Instant scanDate;

    @Column(nullable = false)
    private boolean isSuccessful;

    @Column
    private int totalCookies;

}
