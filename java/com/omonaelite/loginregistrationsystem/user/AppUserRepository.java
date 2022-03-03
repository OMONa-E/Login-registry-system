package com.omonaelite.loginregistrationsystem.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByFirstName(String firstName);
    Optional<AppUser> findByLastName(String lastName);
    Optional<AppUser> findByEnabled(boolean enabled);
    @Transactional
    @Modifying
    @Query("UPDATE AppUser a "+"SET a.enabled = TRUE " + "WHERE a.email = ?1")
    int enableAppUser(String email);


}
