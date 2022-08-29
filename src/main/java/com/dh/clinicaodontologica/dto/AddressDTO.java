package com.dh.clinicaodontologica.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressDTO {
    private Long id;
    private String street;
    private String number;
    private String city;
    private String Province;
}
