package com.dh.clinicaodontologica;

import com.dh.clinicaodontologica.model.Address;
import com.dh.clinicaodontologica.model.Patient;
import com.dh.clinicaodontologica.service.AddressService;
import com.dh.clinicaodontologica.service.PatientService;
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
public class IntegrationTestForPatient {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AddressService addressService;

    public void carryDataSet() {
        Address addressTest = new Address("Caller 2", "1943 1° B", "La Plata", "Buenos Aires");
        Patient patientTest = patientService.createPatient(new Patient("German", "Binder", 30284183, new Date(), addressTest));
    }
    @Test
    public void listPatient() throws Exception {
        this.carryDataSet();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/pacientes/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
