package com.dh.clinicaodontologica.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TURNS")
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Turn {

    @Id
    @SequenceGenerator(name = "turn_sequence", sequenceName = "turn_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turn_sequence")
    @Column(name = "ID")
    private Long id;

    //Muchos turnos pueden corresponder a un mismo paciente.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private Patient patient;

    //Muchos turnos pueden corresponder a un mismo odontólogo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DENTIST_ID", nullable = false)
    private Dentist dentist;

    @Column(name = "DATE")
    private Date date;

    //CONSTRUCTORES
    public Turn() {

    }

    public Turn(Patient patient, Dentist dentist, Date date) {
        this.patient = patient;
        this.dentist = dentist;
        this.date = date;
    }

    public Turn(Long id, Patient patient, Dentist dentist, Date date) {
        this.id = id;
        this.patient = patient;
        this.dentist = dentist;
        this.date = date;
    }
}
