package pl.skowrxn.cookie.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.admin.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
