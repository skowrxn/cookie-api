package pl.skowrxn.cookie;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.skowrxn.cookie.common.service.CookieTypeService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CookieTypeService cookieTypeService;

    @Override
    public void run(String... args) throws Exception {
        cookieTypeService.initializeCookieTypes();
        //TODO move to webiste constructor
    }

}
