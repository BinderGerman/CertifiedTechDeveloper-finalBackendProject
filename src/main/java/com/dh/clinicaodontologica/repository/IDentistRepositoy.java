package com.dh.clinicaodontologica.repository;

import com.dh.clinicaodontologica.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDentistRepositoy extends JpaRepository<Dentist, Long> {
    @Query("SELECT d FROM Dentist d WHERE d.tuition = ?1")
    Dentist findDentistByTuition(String tuition);
}
