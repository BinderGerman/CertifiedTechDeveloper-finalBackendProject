package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.dto.PatientDTO;
import com.dh.clinicaodontologica.model.Patient;
import com.dh.clinicaodontologica.model.Turn;
import com.dh.clinicaodontologica.repository.IPatientRepository;
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
public class PatientService {

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private TurnService turnService;

    @Autowired
    ObjectMapper mapper;

    //MÉTODOS CRUD + LISTAR PACIENTES
    //CREA UN PACIENTE
    public Patient createPatient(Patient patient) {
        if (this.findPatientByFullName(patient.getName(), patient.getSurname()) != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Paciente ya cargado");

        return patientRepository.save(patient);
    }

    //BUSCA UN PACIENTE POR ID
    public PatientDTO readPatient(Long id) {
        if (patientRepository.findById(id).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el paciente con id: " + id);

        Optional<Patient> patient = patientRepository.findById(id);
        return mapper.convertValue(patient, PatientDTO.class);
    }

    //MODIFICA EL PACIENTE
    public Patient updatePatient(Patient patient) {
        if (patientRepository.findById(patient.getId()).isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el paciente que quieres modificar");

        return patientRepository.save(patient);
    }

    //ELIMINA UN PACIENTE
    public void deletePatient(Long id) {
       if (patientRepository.findById(id).isEmpty())
           throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No existe el paciente con id: " + id);

       patientRepository.deleteById(id);
    }

    //CREA UNA LISTA DE PACIENTES
    public Set<PatientDTO> getListPatients() {
        List<Patient> patients = patientRepository.findAll();
        Set<PatientDTO> patientsDTO = new HashSet<>();
        for (Patient patient: patients) {
            patientsDTO.add(mapper.convertValue(patient, PatientDTO.class));
        }
        return patientsDTO;
    }

    //MÉTODOS A PARTIR DE QUERIES
    //BUSCA UN PACIENTE POR NOMBRE Y APELLIDO
    public Patient findPatientByFullName(String name, String surname) {
         return patientRepository.findPatientByFullname(name, surname);
    }

}
