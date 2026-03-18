package org.example.springbootintro.repository;

import java.util.Optional;
import org.example.springbootintro.model.Role;
import org.example.springbootintro.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
