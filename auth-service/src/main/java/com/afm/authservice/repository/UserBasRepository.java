package com.afm.authservice.repository;

import model.auth.UserBas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBasRepository extends JpaRepository<UserBas, Long> {
    UserBas findByEmail(String email);
}
