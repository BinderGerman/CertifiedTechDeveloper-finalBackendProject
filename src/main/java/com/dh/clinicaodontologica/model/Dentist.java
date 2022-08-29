package com.dh.clinicaodontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DENTISTS")
@Getter @Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dentist {

    @Id
    @SequenceGenerator(name = "dentist_sequence", sequenceName = "dentist_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dentist_sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "DNI")
    private int dni;
    @Column(name = "TUITION")
    private String tuition;

    @OneToMany(mappedBy = "dentist", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Turn> turns = new HashSet<>();

    //CONSTRUCTORES
    public Dentist() {

    }

    public Dentist(String name, String surname, int dni, String tuition) {
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.tuition = tuition;
    }

    public Dentist(Long id, String name, String surname, int dni, String tuition) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.tuition = tuition;
    }
}
