package com.dh.clinicaodontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PATIENTS")
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient {

    @Id
    @SequenceGenerator(name = "patient_sequence", sequenceName = "patient_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "DNI")
    private int dni;
    @Column(name = "DATE_OF_ADMISSION")
    private Date dateOfAdmission;

    //Un paciente tiene una dirección.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Address address;

    //Un paciente puede tener muchos turnos
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Turn> turns = new HashSet<>();

    //CONSTRUCTORES
    public Patient() {

    }

    public Patient(String name, String surname, int dni, Date dateOfAdmission, Address address) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.dateOfAdmission = dateOfAdmission;
        this.address = address;
    }

    public Patient(Long id, String name, String surname, int dni, Date dateOfAdmission, Address address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.dateOfAdmission = dateOfAdmission;
        this.address = address;
    }
}
