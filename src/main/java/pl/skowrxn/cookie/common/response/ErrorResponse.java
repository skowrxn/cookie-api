package pl.skowrxn.cookie.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private String details;

    public ErrorResponse(String message) {
        this.message = message;
        this.details = null;
    }

}
