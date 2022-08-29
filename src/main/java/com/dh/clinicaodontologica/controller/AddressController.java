package com.dh.clinicaodontologica.controller;

import com.dh.clinicaodontologica.dto.AddressDTO;
import com.dh.clinicaodontologica.model.Address;
import com.dh.clinicaodontologica.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/direcciones")
public class AddressController {

    @Autowired
    AddressService addressService;


    @GetMapping("/{id}")
    public AddressDTO readAddress(@PathVariable Long id) {
        return addressService.readAddress(id);
    }

    @PutMapping
    public ResponseEntity<?> updateAddress(@RequestBody Address address) {
        addressService.updateAddress(address);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public Collection<AddressDTO> getListAddresss(){
        return addressService.getListAddress();
    }
}
