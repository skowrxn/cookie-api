package pl.skowrxn.cookie.consent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.skowrxn.cookie.admin.entity.Website;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "visitors", indexes = {
        @Index(name = "idx_visitor_token", columnList = "token")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "user_agent", length = 1024)
    private String userAgent;

    @ManyToOne
    @JoinColumn(name = "website_id", nullable = false)
    private Website website;

    @LastModifiedDate
    @Column(name = "last_updated_time", nullable = false)
    private Instant lastUpdatedTime;

    @OneToMany(mappedBy = "visitor")
    private List<ConsentLog> consentLogs;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConsentStatus status;
}
