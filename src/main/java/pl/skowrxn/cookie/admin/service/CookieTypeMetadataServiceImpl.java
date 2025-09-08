package pl.skowrxn.cookie.admin.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CookieTypeMetadataServiceImpl implements CookieTypeMetadataService {

    private final Map<String, String> KEYS = Map.of(
            "Targeting cookies", "ad_storage",
            "Strictly Necessary cookies", "security_storage",
            "Functionality cookies", "functionality_storage",
            "Performance cookies", "analytics_storage",
            "Unclassified", "other"
    );

    private final Map<String, String> DESCRIPTIONS = Map.of(
            "Targeting cookies", "These cookies are used to deliver advertisements that are more relevant to you and your interests. They are also used to limit the number of times you see an advertisement as well as help measure the effectiveness of the advertising campaign.",
            "Strictly Necessary cookies", "These cookies are essential for the website to function properly. They enable basic functions like page navigation and access to secure areas of the website. The website cannot function properly without these cookies.",
            "Functionality cookies", "These cookies allow the website to remember choices you make (such as your username, language, or the region you are in) and provide enhanced, more personal features. They may also be used to provide services you have requested, such as watching a video or commenting on a blog.",
            "Performance cookies", "These cookies collect information about how visitors use a website, for instance which pages visitors go to most often, and if they get error messages from web pages. These cookies do not collect information that identifies a visitor. All information these cookies collect is aggregated and therefore anonymous. It is only used to improve how a website works.",
            "Unclassified", DEFAULT_DESCRIPTION
    );

    private static final String DEFAULT_DESCRIPTION = "Cookies that have not yet been assigned to a specific category.";

    @Override
    public String getKey(String name) {
        return KEYS.getOrDefault(name, "other");
    }

    @Override
    public String getDescription(String cookieTypeName) {
        return DESCRIPTIONS.getOrDefault(cookieTypeName, DESCRIPTIONS.get("Unclassified"));
    }
}
