package pl.skowrxn.cookie.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "banner_settings")
@Getter
@Setter
public class BannerSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "reject_all_button_text", nullable = false)
    private String rejectAllButtonText;

    @Column(name = "accept_all_button_text", nullable = false)
    private String acceptAllButtonText;

    @Column(name = "save_button_text", nullable = false)
    private String saveButtonText;

    @Column(name = "customise_cookies_button_text", nullable = false)
    private String customiseCookiesButtonText;

    @Column(name = "banner_description_text", nullable = false)
    private String bannerDescriptionText;

    @Column(name = "banner_title_text", nullable = false)
    private String bannerTitleText;

    @Column(name = "primary_color", nullable = false)
    private String primaryColor;

    @Column(name = "text_color", nullable = false)
    private String textColor;

}
