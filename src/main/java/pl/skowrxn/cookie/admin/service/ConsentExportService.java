package pl.skowrxn.cookie.admin.service;

import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skowrxn.cookie.admin.entity.Website;
import pl.skowrxn.cookie.admin.repository.WebsiteRepository;
import pl.skowrxn.cookie.common.exception.ResourceNotFoundException;
import pl.skowrxn.cookie.consent.entity.ConsentLog;
import pl.skowrxn.cookie.consent.entity.Visitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConsentExportService {

    private WebsiteRepository websiteRepository;

    public byte[] exportToCsv(String siteId) {
        Optional<Website> website = websiteRepository.findById(UUID.fromString(siteId));
        if (website.isEmpty()){
            throw new ResourceNotFoundException("website", siteId);
        }
        List<Visitor> visitors = website.get().getVisitors();

        try (CSVWriter writer = new CSVWriter(new StringWriter())){
            writer.writeNext(new String[] {"Visitor ID,Visitor Token,IP Address,User Agent,Consent Time,Consents"});
            for (Visitor visitor : visitors) {

                StringBuilder consents = new StringBuilder();
                for (ConsentLog consentLog : visitor.getConsentLogs()) {
                    consents
                            .append(consentLog.getCookieType().getKey())
                            .append(":")
                            .append(consentLog.getStatus())
                            .append(", ");
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = visitor.getLastUpdatedTime().atZone(ZoneOffset.UTC).format(formatter);

                writer.writeNext(new String[]{
                        visitor.getId().toString(),
                        visitor.getToken(),
                        visitor.getIp(),
                        visitor.getUserAgent(),
                        formattedDate,
                        consents.toString()
                });
            }

            return writer.toString().getBytes(StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Error while generating CSV file: " + e.getMessage());
        }
    }

}
