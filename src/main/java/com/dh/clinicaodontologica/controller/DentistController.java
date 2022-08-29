package com.dh.clinicaodontologica.controller;

import com.dh.clinicaodontologica.dto.DentistDTO;
import com.dh.clinicaodontologica.model.Dentist;
import com.dh.clinicaodontologica.service.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/odontologos")
public class DentistController {

    @Autowired
    DentistService dentistService;

    @PostMapping
    public ResponseEntity<Dentist> createDentist(@RequestBody Dentist dentist) {
        return ResponseEntity.ok(dentistService.createDentist(dentist));
    }

    @GetMapping("/{id}")
    public DentistDTO readDentist(@PathVariable Long id) {
        return dentistService.readDentist(id);
    }

    @PutMapping
    public ResponseEntity<Dentist> updateDentist(@RequestBody Dentist dentist) {
        return ResponseEntity.ok(dentistService.updateDentist(dentist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDentist(@PathVariable Long id) {
        dentistService.deleteDentist(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public Set<DentistDTO> getListDentists(){
        return dentistService.getListDentists();
    }

}
