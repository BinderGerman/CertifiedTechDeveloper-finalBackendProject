package com.dh.clinicaodontologica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESSES")
@Getter @Setter
public class Address {

    @Id
    @SequenceGenerator(name = "address_sequence", sequenceName = "address_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    @Column(name = "ID")
    private Long id;
    @Column(name = "STREET")
    private String street;
    @Column(name = "NUMBER")
    private String number;
    @Column(name = "CITY")
    private String city;
    @Column(name = "PROVINCE")
    private String province;
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Patient patient;

    //CONSTRUCTORES
    public Address() {

    }

    public Address(String street, String number, String city, String province) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.province = province;
    }

    public Address(Long id, String street, String number, String city, String province) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.city = city;
        this.province = province;
    }
}
