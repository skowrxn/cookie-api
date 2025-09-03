package pl.skowrxn.cookie.admin.auth.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    private String username;
    private String email;
    private String password;

}
