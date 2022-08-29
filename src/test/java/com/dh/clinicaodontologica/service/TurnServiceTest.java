package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.model.Address;
import com.dh.clinicaodontologica.model.Dentist;
import com.dh.clinicaodontologica.model.Patient;
import com.dh.clinicaodontologica.model.Turn;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnServiceTest {
    @Autowired
    private DentistService dentistService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private TurnService turnService;

    Address addressTest;
    Patient patientTest;
    Dentist dentistTest;

    @BeforeEach
    void init(){
        addressTest = new Address("Balcarce", "50", "CABA", "CABA");
        patientTest = new Patient("Alberto", "Fernandez", 18148203, new Date(), addressTest);
        dentistTest = new Dentist("Cristina", "Fernandez", 18148203, "789");
    }

    @Test
    @Order(1)
    @DisplayName("Turn Register -> Turn")
    void saveTurn(){
        Dentist dentist = dentistService.createDentist(dentistTest);
        Patient patient = patientService.createPatient(patientTest);
        Turn turn = new Turn(patient, dentist, new Date());
        Turn turnTest = turnService.createTurn(turn);
        Assertions.assertNotNull(turnTest);
        Assertions.assertNotNull(turnTest.getId());
        Assertions.assertEquals(turnTest.getPatient(), patientTest);
        Assertions.assertEquals(turnTest.getDentist(), dentistTest);
    }
}