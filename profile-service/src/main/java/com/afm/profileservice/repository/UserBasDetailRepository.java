package com.afm.profileservice.repository;


import model.auth.UserBasDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBasDetailRepository extends JpaRepository<UserBasDetail, Long> {
    UserBasDetail findByEmail(String mail);
}
