package com.example.socialnetwork.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findById(Long id);

    @Transactional
    @Modifying
    @Query("update AppUser a set a.enabled=true where a.email=?1")
    int enableAppUser(String email);
}
