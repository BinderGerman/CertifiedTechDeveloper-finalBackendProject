package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.dto.DentistDTO;
import com.dh.clinicaodontologica.model.Dentist;
import com.dh.clinicaodontologica.repository.IDentistRepositoy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DentistService {

    @Autowired
    private IDentistRepositoy dentistRepository;

    @Autowired
    ObjectMapper mapper;

    //MÉTODOS CRUD + LISTAR ODONTÓLOGOS
    //CREA EL ODONTÓLOGO
    public Dentist createDentist(Dentist dentist) {
        if (this.findDentistByTuition(dentist.getTuition()) != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Odontólogo ya cargado");

        return dentistRepository.save(dentist);
    }

    //BUSCA EL ODONTÓLOGO POR ID
    public DentistDTO readDentist(Long id) {
        if (dentistRepository.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el odontólogo con id: " + id);

        Optional<Dentist> dentist = dentistRepository.findById(id);
        return mapper.convertValue(dentist, DentistDTO.class);
    }

    ////MODIFICA EL ODONTÓLOGO
    public Dentist updateDentist(Dentist dentist) {
        if (dentistRepository.findById(dentist.getId()).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el odontólogo que quieres modificar");

        return dentistRepository.save(dentist);
    }

    //ELIMINA EL ODONTÓLOGO
    public void deleteDentist(Long id) {
        if (dentistRepository.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el odontólogo con id: " + id);

        dentistRepository.deleteById(id);
    }

    //CREA UNA LISTA DE ODONTÓLOGOS
    public Set<DentistDTO> getListDentists() {
        List<Dentist> dentists = dentistRepository.findAll();
        Set<DentistDTO> dentistsDTO = new HashSet<>();
        for (Dentist dentist: dentists) {
            dentistsDTO.add(mapper.convertValue(dentist, DentistDTO.class));
        }
        return dentistsDTO;
    }

    //MÉTODOS A PARTIR DE QUERIES
    //BUSCA UN ODONTÓLOGO POR MATRICULA
    public Dentist findDentistByTuition(String tuition) {
        return dentistRepository.findDentistByTuition(tuition);
    }
}