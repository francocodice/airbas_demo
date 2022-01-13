package com.afm.authservice.repository;


import model.auth.UserBas;
import model.auth.UserBasDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBasDetailRepository extends JpaRepository<UserBasDetail, Long> {
    UserBasDetail findByUserbas(UserBas userbas);
    UserBasDetail findByUserbasId(Long id);
}
