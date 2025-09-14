package pl.skowrxn.cookie.consent.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ConsentRequest {

    @NotNull(message = "Log cannot be null")
    @NotEmpty(message = "Log cannot be empty")
    @Valid
    private List<ConsentLogRequestDTO> log;

    @NotBlank(message = "Website key is required")
    private String key;

    @NotBlank(message = "Consent ID is required")
    private String consent_id;

    @NotBlank(message = "Domain is required")
    private String domain;
}
