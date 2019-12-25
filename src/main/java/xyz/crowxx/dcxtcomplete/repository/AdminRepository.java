package xyz.crowxx.dcxtcomplete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.crowxx.dcxtcomplete.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findAdminByUsername(String username);

}
