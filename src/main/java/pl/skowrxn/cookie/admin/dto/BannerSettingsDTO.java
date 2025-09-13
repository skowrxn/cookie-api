package pl.skowrxn.cookie.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BannerSettingsDTO {

    private String rejectAllButtonText;
    private String acceptAllButtonText;
    private String saveButtonText;
    private String customiseCookiesButtonText;
    private String bannerDescriptionText;
    private String bannerTitleText;
    private String primaryColor;
    private String textColor;

}
