package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import pl.skowrxn.cookie.consent.entity.ConsentType;
import pl.skowrxn.cookie.consent.entity.Visitor;

import java.util.List;
import java.util.UUID;

@Entity
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String domain;

    @OneToMany(mappedBy = "website")
    private List<ConsentType> consentTypes;

    @OneToMany(mappedBy = "website")
    private List<Visitor> visitors;

    // cookie banner configuration
    // owner (user)

}
