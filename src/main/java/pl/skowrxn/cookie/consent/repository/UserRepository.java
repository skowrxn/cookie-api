package pl.skowrxn.cookie.consent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.consent.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByToken(String token);
    
}
