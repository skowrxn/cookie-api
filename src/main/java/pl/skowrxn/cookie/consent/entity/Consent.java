package pl.skowrxn.cookie.consent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name="visitor_id")
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "consent_type_id")
    private ConsentType consentType;

}
