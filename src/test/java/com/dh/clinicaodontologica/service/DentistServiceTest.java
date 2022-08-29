package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.dto.DentistDTO;
import com.dh.clinicaodontologica.model.Dentist;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DentistServiceTest {

    @Autowired
    DentistService dentistService;

    Dentist dentistTest;

    @BeforeEach
    void iniit() {
        dentistTest = new Dentist("Jose", "rompebocas", 30284181, "123");
    }

    @Test
    @Order(1)
    @DisplayName("Dentist Record -> Dentist")
    void createDentist() {
        Dentist dentist = dentistService.createDentist(dentistTest);
        Assertions.assertNotNull(dentist);
        Assertions.assertNotNull(dentist.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Dentist Search -> Dentist")
    void readDentist() {
        DentistDTO dentistDTO = dentistService.readDentist(1L);
        Assertions.assertNotNull(dentistDTO);
        Assertions.assertNotNull(dentistDTO.getId());
        Assertions.assertTrue(dentistDTO instanceof DentistDTO);
    }

    @Test
    @Order(3)
    @DisplayName("List dentists -> Dentist")
    void getListDentists() {
        Set<DentistDTO> dentists = dentistService.getListDentists();
        Assert.assertTrue( !dentists.isEmpty() );
        Assert.assertTrue( dentists.size() == 1 );
    }
}