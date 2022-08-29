package com.dh.clinicaodontologica.repository;

import com.dh.clinicaodontologica.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.name = ?1 AND p.surname = ?2")
    Patient findPatientByFullname(String name, String surname);
}
