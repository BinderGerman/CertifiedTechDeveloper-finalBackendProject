package com.dh.clinicaodontologica.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DentistDTO {
    private Long id;
    private String name;
    private String surname;
    private int dni;
    private String tuition;
}
