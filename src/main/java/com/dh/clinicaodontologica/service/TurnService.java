package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.dto.TurnDTO;
import com.dh.clinicaodontologica.model.Turn;
import com.dh.clinicaodontologica.repository.IDentistRepositoy;
import com.dh.clinicaodontologica.repository.IPatientRepository;
import com.dh.clinicaodontologica.repository.ITurnRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class TurnService {

    @Autowired
    private ITurnRepository turnRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IDentistRepositoy dentistRepositoy;

    @Autowired
    ObjectMapper mapper;

    //MÉTODOS CRUD + LISTAR TURNOS
    //CREA UN TURNO
    public Turn createTurn(Turn turn) {
        if (patientRepository.findById(turn.getPatient().getId()).isEmpty() ||
                dentistRepositoy.findById(turn.getDentist().getId()).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,
                    "El paciente o el odontólogo que estas cargando no existe");

        return turnRepository.save(turn);
    }

    //BUSCA UN TURNO POR ID
    public TurnDTO readTurn(Long id) {
        if (turnRepository.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el turno con id: " + id);

        Optional<Turn> turn = turnRepository.findById(id);
        return mapper.convertValue(turn, TurnDTO.class);
    }

    //MODIFICA EL TURNO
    public Turn updateTurn(Turn turn) {
        if (turnRepository.findById(turn.getId()).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el turno que quieres modificar");

        return turnRepository.save(turn);
    }

    //ELIMINA UN TURNO
    public void deleteTurn(Long id) {
        if (turnRepository.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el turno con id: " + id);
        turnRepository.deleteById(id);
    }

    //CREA UNA LISTA DE TURNOS
    public Set<TurnDTO> getListTurns() {
        List<Turn> turns = turnRepository.findAll();
        Set<TurnDTO> turnsDTO = new HashSet<>();
        for (Turn turn: turns) {
            turnsDTO.add(mapper.convertValue(turn, TurnDTO.class));
        }
        return turnsDTO;
    }

    //MÉTODOS A PARTIR DE QUERIES
    public List<Turn> findTurnsPatientById(Long id) {
        return turnRepository.findTurnsPatientById(id);
    }

    public List<Turn> findTurnsDentistByTuition(String tuition) {
        return turnRepository.findTurnsDentistByTuition(tuition);
    }
}