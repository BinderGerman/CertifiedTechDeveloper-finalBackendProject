package com.dh.clinicaodontologica.controller;
import com.dh.clinicaodontologica.dto.PatientDTO;
import com.dh.clinicaodontologica.model.Patient;
import com.dh.clinicaodontologica.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/pacientes")
public class PatientController {

    @Autowired
    PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.createPatient(patient));
    }

    @GetMapping("/{id}")
    public PatientDTO readPatient(@PathVariable Long id) {
        return patientService.readPatient(id);
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public Set<PatientDTO> getListPatients(){
        return patientService.getListPatients();
    }
    
}
