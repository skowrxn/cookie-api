package pl.skowrxn.cookie.admin.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.skowrxn.cookie.admin.entity.User;
import pl.skowrxn.cookie.admin.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email = " + email)
        );
        return UserDetailsImpl.build(user);
    }
}
