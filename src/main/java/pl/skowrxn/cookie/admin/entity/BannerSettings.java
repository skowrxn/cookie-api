package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class BannerSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String rejectAllButtonText;

    @Column(nullable = false)
    private String acceptAllButtonText;

    @Column(nullable = false)
    private String saveButtonText;

    @Column(nullable = false)
    private String manageCookiesButtonText;

    @Column(nullable = false)
    private String bannerDescriptionText;

    @Column(nullable = false)
    private String bannerTitleText;

    @Column(nullable = false)
    private String primaryColor;

    @Column(nullable = false)
    private String textColor;

}
