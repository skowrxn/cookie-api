package pl.skowrxn.cookie.consent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.UUID;

@Entity
@Table(name = "constent_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "consent_type_id")
    private CookieType cookieType;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="visitor_id")
    private Visitor visitor;

}
