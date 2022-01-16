package com.afm.authservice.repository;

import model.auth.Authority;
import model.auth.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(ERole name);

}
