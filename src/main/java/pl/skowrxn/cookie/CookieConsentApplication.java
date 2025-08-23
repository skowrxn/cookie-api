package pl.skowrxn.cookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CookieConsentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookieConsentApplication.class, args);
    }

}
