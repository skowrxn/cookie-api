package pl.skowrxn.cookie.admin.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.skowrxn.cookie.admin.auth.service.UserDetailsImpl;
import pl.skowrxn.cookie.admin.entity.User;
import pl.skowrxn.cookie.admin.repository.UserRepository;

import java.util.UUID;

@Component
public class AuthUtil {

    private final UserRepository userRepository;

    public AuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        UUID loggedInUserId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
