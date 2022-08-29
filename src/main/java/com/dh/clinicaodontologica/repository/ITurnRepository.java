package com.dh.clinicaodontologica.repository;

import com.dh.clinicaodontologica.model.Dentist;
import com.dh.clinicaodontologica.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITurnRepository extends JpaRepository<Turn, Long> {

    @Query("SELECT t FROM Turn t WHERE t.patient.id = ?1")
    List<Turn> findTurnsPatientById(Long id);

    @Query("SELECT t FROM Turn t WHERE t.dentist.tuition  = ?1")
    List<Turn> findTurnsDentistByTuition(String tuition);
}
