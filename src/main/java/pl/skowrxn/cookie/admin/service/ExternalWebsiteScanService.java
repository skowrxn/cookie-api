package pl.skowrxn.cookie.admin.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.skowrxn.cookie.admin.dto.WebsiteScanDTO;
import pl.skowrxn.cookie.admin.entity.Cookie;
import pl.skowrxn.cookie.admin.entity.WebsiteScan;
import pl.skowrxn.cookie.admin.exception.CookieScanException;
import pl.skowrxn.cookie.admin.repository.CookieRepository;
import pl.skowrxn.cookie.admin.repository.WebsiteScanRepository;
import pl.skowrxn.cookie.common.entity.CookieType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalWebsiteScanService implements WebsiteScanService {

    private ModelMapper modelMapper;
    private WebsiteScanRepository websiteScanRepository;
    private CookieRepository cookieRepository;
    private CookieTypeMetadataService cookieTypeMetadataService;

    private static final Logger logger = LoggerFactory.getLogger(ExternalWebsiteScanService.class);
    private final RestTemplate restTemplate;
    private static final String COOKIE_SCRIPT_SCAN_URL = "https://cookie-script.com";

    public ExternalWebsiteScanService(ModelMapper modelMapper, WebsiteScanRepository websiteScanRepository,
                                      CookieRepository cookieRepository, CookieTypeMetadataService cookieTypeMetadataService) {
        this.modelMapper = modelMapper;
        this.websiteScanRepository = websiteScanRepository;
        this.cookieRepository = cookieRepository;
        this.cookieTypeMetadataService = cookieTypeMetadataService;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public WebsiteScanDTO scanCookies(String url) {
        logger.info("Starting cookie scan for URL: {}", url);
        WebsiteScan websiteScan = new WebsiteScan();
        websiteScan.setScanTime(new Date().toInstant());

        String reportUrl = initiateCookieScan(url);
        if (reportUrl != null) {
            try {
                var scanResult = fetchCookieReport(reportUrl).get();
                List<CookieType> cookieTypes = scanResult.getFirst();
                int totalCookies = scanResult.getSecond();
                websiteScan.setDetectedCookieTypes(cookieTypes);
                websiteScan.setTotalCookies(totalCookies);
                websiteScan.setSuccessful(true);
                cookieTypes.forEach(c -> logger.debug("COOKIE: {}", c.toString()));
                logger.info("Successfully scanned {} cookies for URL: {}", totalCookies, url);
            } catch (Exception e) {
                websiteScan.setSuccessful(false);
                logger.error("Error getting cookie report: {}", e.getMessage());
            }
        }
        websiteScanRepository.save(websiteScan);
        return modelMapper.map(websiteScan, WebsiteScanDTO.class);
    }

    public String initiateCookieScan(String url) {
        logger.debug("Posting report request for URL: {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("site", url);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    COOKIE_SCRIPT_SCAN_URL + "/scan",
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            String locationHeader = response.getHeaders().getFirst("Location");
            if (locationHeader != null) {
                logger.debug("Received location header: {}", locationHeader);
                return COOKIE_SCRIPT_SCAN_URL + locationHeader;
            }
        } catch (Exception e) {
            throw new CookieScanException(e);
        }

        return null;
    }

    @Async
    public CompletableFuture<Pair<List<CookieType>, Integer>> fetchCookieReport(String reportUrl) {
        logger.debug("Fetching cookie report from URL: {}", reportUrl);
        List<CookieType> cookieTypes = new ArrayList<>();
        int totalCookies = 0;

        try {
            Thread.sleep(45000);
            ResponseEntity<String> response = restTemplate.exchange(
                    reportUrl,
                    HttpMethod.GET,
                    null,
                    String.class);

            String reportHtml = response.getBody();
            if (reportHtml != null) {
                var result = parseCookiesFromHtml(reportHtml);
                cookieTypes = result.getFirst();
                totalCookies = result.getSecond();
                logger.debug("Successfully parsed {} cookies from report", cookieTypes.size());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CookieScanException("Thread was interrupted", e);
        } catch (Exception e) {
            throw new CookieScanException(e);
        }

        return CompletableFuture.completedFuture(Pair.of(cookieTypes, totalCookies));
    }

    private Pair<List<CookieType>, Integer> parseCookiesFromHtml(String html) {
        logger.debug("Starting HTML parsing for cookies");
        List<CookieType> cookieTypes = new ArrayList<>();
        int totalCookies = 0;

        try {
            Document doc = Jsoup.parse(html);
            Element reportBox = doc.select("div.reportbox").get(0);

            Elements titles = reportBox.select("h3.reporttitle");
            Elements tables = reportBox.select("table.reporttable");

            int i = 0;
            while (i < titles.size() && i < tables.size()) {
                Element title = titles.get(i);
                Element table = tables.get(i);
                String name = title.ownText();
                Elements rows = table.select("tbody tr:not(.firstfoundrow)");

                logger.debug("Parsing cookie type: {} with {} cookies", name, rows.size());

                CookieType cookieType = new CookieType(name, cookieTypeMetadataService.getKey(name), cookieTypeMetadataService.getDescription(name));

                logger.debug("Created CookieType: {}", cookieType);

                for (Element row : rows) {
                    Elements cells = row.select("td");
                    if (cells.size() >= 6) {
                        String cookieKey = cells.get(0).ownText().trim();
                        String domain = cells.get(1).text().trim();
                        String expiration = cells.get(4).text().trim();
                        String description = cells.get(5).text().trim();
                        logger.debug("Found cookie - Key: {}, Domain: {}, Expiration: {}, Description: {}", cookieKey, domain, expiration, description);
                        Cookie cookie = cookieRepository.findByNameAndDomain(cookieKey, domain).orElse(
                                new Cookie(cookieKey, domain, description, expiration)
                        );
                        cookieType.getCookies().add(cookie);
                        cookieRepository.save(cookie);
                        cookieTypes.add(cookieType);
                        totalCookies++;
                    }
                }
                i++;
            }
        } catch (Exception e) {
            throw new CookieScanException("Error parsing HTML for cookies" + e.getMessage(), e);
        }

        return Pair.of(cookieTypes, totalCookies);
    }

}