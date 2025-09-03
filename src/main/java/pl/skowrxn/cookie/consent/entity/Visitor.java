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

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private String ip;

    @Column
    private String userAgent;

    @JoinColumn
    @ManyToOne
    private Website website;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant lastUpdated;

    @OneToMany(mappedBy = "visitor")
    private List<ConsentLog> consentLogs;

}
