package pl.skowrxn.cookie.consent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.skowrxn.cookie.admin.entity.Website;

import java.util.UUID;

@Getter
@Setter
@Entity
public class ConsentType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID consentId;

    @Column(nullable = false)
    private String name;
    private String key;
    private String description;

    @ManyToOne
    @JoinColumn(name = "website_id")
    private Website website;

}
