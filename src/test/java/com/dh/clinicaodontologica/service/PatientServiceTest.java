package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.dto.PatientDTO;
import com.dh.clinicaodontologica.model.Address;
import com.dh.clinicaodontologica.model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Set;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PatientServiceTest {

    @Autowired
    PatientService patientService;

    Patient patientTest;

    @BeforeEach
    void iniit() {
        patientTest = new Patient("Fiona", "Binder", 30284182, new Date(), new Address("Calle 2 ", "1943 1° B", "La Plata", "Buenos Aires"));
    }

    @Test
    @Order(1)
    @DisplayName("Patient Record -> Patient")
    void createPatient() {
        Patient patient = patientService.createPatient(patientTest);
        Assertions.assertNotNull(patient);
        Assertions.assertNotNull(patient.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Patient Search -> Patient")
    void readPatient() {
        PatientDTO patientDTO = patientService.readPatient(1L);
        Assertions.assertNotNull(patientDTO);
        Assertions.assertNotNull(patientDTO.getId());
        Assertions.assertTrue(patientDTO instanceof PatientDTO);
    }

    @Test
    @Order(3)
    @DisplayName("List patients -> Patient")
    void getListPatients() {
        Set<PatientDTO> patients = patientService.getListPatients();
        Assert.assertTrue( !patients.isEmpty() );
        Assert.assertTrue( patients.size() == 1 );
    }
}