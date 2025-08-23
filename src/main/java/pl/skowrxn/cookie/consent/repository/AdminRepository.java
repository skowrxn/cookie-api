package pl.skowrxn.cookie.consent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.skowrxn.cookie.admin.entity.Admin;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

}
