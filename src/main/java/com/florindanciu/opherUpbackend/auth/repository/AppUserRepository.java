package com.florindanciu.opherUpbackend.auth.repository;

import com.florindanciu.opherUpbackend.auth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    AppUser findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
