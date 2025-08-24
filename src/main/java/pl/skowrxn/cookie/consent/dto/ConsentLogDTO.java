package pl.skowrxn.cookie.consent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConsentLogDTO {

    @NotBlank(message = "Consent type name is required")
    private String name;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(yes|no)$", message = "Status must be 'yes' or 'no'")
    private String status;

}
