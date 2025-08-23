package pl.skowrxn.cookie.consent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "consent_type_id")
    private ConsentType consentType;

}
