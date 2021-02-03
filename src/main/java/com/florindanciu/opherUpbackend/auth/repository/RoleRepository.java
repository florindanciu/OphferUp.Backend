package com.florindanciu.opherUpbackend.auth.repository;

import com.florindanciu.opherUpbackend.auth.model.EnumRole;
import com.florindanciu.opherUpbackend.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(EnumRole name);
}
