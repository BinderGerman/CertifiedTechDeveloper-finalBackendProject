package com.dh.clinicaodontologica;

import com.dh.clinicaodontologica.model.Address;
import com.dh.clinicaodontologica.model.Dentist;
import com.dh.clinicaodontologica.model.Patient;
import com.dh.clinicaodontologica.model.Turn;
import com.dh.clinicaodontologica.repository.IDentistRepositoy;
import com.dh.clinicaodontologica.repository.IPatientRepository;
import com.dh.clinicaodontologica.service.DentistService;
import com.dh.clinicaodontologica.service.PatientService;
import com.dh.clinicaodontologica.service.TurnService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegrationTestForTurn {
    @Autowired
    private PatientService patientService;
    @Autowired
    private DentistService dentistService;
    @Autowired
    private TurnService turnService;
    @Autowired
    private IPatientRepository patientRepository;
    @Autowired
    private IDentistRepositoy dentistRepositoy;
    @Autowired
    private MockMvc mockMvc;

    public void carryDataSet() {
        Address addressTest = new Address("A.P.Bell", "1418", "Trelew", "Chubut");
        this.patientService.createPatient(new Patient("Mauricio", "Binder", 30284181, new Date(), addressTest));
        this.dentistService.createDentist(new Dentist("Felipe", "Sacamuelas", 30284181, "456"));
        turnService.createTurn(new Turn(patientRepository.findById(1L).get(), dentistRepositoy.findById(1L).get(), new Date()));

    }
    @Test
    public void listTurn() throws Exception {
        this.carryDataSet();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
