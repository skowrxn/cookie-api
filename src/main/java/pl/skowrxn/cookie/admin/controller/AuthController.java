package pl.skowrxn.cookie.admin.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.skowrxn.cookie.admin.auth.JWTUtils;
import pl.skowrxn.cookie.admin.entity.Role;
import pl.skowrxn.cookie.admin.auth.request.LoginRequest;
import pl.skowrxn.cookie.admin.auth.request.SignupRequest;
import pl.skowrxn.cookie.admin.auth.service.UserDetailsImpl;
import pl.skowrxn.cookie.admin.entity.User;
import pl.skowrxn.cookie.admin.repository.UserRepository;
import pl.skowrxn.cookie.common.response.ErrorResponse;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JWTUtils.class);

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            logger.debug("Attempting to authenticate user: {}", loginRequest.getEmail());
            if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty() ||
                    loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new ErrorResponse("Email or password cannot be empty!"));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(userDetails);
        } catch (Exception e) {
            logger.error("Error during user authentication: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Invalid username or password!", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Username is already taken!"));
        }

        Optional<User> existingUserByEmail = userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(signupRequest.getEmail()))
                .findFirst();

        if (existingUserByEmail.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.generateCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    //TODO endpoints: change password, reset password, get account details
}