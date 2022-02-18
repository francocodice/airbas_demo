package com.afm.reservationservice.repository;

import model.prenotation.EnumRateType;
import model.prenotation.Rate;
import model.prenotation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RateRepository extends JpaRepository<Rate, Long> {
    @Query(value="SELECT * FROM Rate r WHERE r.type = :type ", nativeQuery = true)
    Rate findByType(String type);
}
