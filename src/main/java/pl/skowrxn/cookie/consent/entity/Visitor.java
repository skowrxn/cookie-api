package pl.skowrxn.cookie.consent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Visitor {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String token;

    private String ip;
    private String userAgent;
    private Instant lastUpdated;

    @OneToMany(mappedBy = "visitor")
    private List<Consent> consents;

}
