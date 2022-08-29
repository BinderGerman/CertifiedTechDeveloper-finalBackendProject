package com.dh.clinicaodontologica.dto;

import com.dh.clinicaodontologica.model.Dentist;
import com.dh.clinicaodontologica.model.Patient;
import com.dh.clinicaodontologica.model.Turn;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter @Setter
public class TurnDTO {
    private Long id;
    private Patient patient;
    private Dentist dentist;
    private Date date;

    //CONSTRUCTORES
    public TurnDTO() {

    }

    public TurnDTO(Patient patient, Dentist dentist, Date date) {
        this.patient = patient;
        this.dentist = dentist;
        this.date = date;
    }

    public TurnDTO(Long id, Patient patient, Dentist dentist, Date date) {
        this.id = id;
        this.patient = patient;
        this.dentist = dentist;
        this.date = date;
    }
}


