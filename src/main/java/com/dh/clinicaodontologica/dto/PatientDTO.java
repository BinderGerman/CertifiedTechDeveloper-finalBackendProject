package com.dh.clinicaodontologica.dto;

import com.dh.clinicaodontologica.model.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class PatientDTO {
    private Long id;
    private String name;
    private String surname;
    private int dni;
    private Date dateOfAdmission;
    private Address address;
}
