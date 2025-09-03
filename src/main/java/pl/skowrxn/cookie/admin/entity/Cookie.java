package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import pl.skowrxn.cookie.consent.entity.ConsentType;

import java.util.UUID;

@Entity
public class Cookie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private ConsentType consentType;

}
