package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.dto.AddressDTO;
import com.dh.clinicaodontologica.model.Address;
import com.dh.clinicaodontologica.repository.IAddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AddressService {

    @Autowired
    private IAddressRepository addressRepository;

    @Autowired
    ObjectMapper mapper;

    public AddressDTO readAddress(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        AddressDTO addressDTO = null;
        if (address.isPresent()) {
            addressDTO = mapper.convertValue(address, AddressDTO.class);
        }
        return addressDTO;
    }

    public void updateAddress(Address address) {
        addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public Set<AddressDTO> getListAddress() {
        List<Address> addresses = addressRepository.findAll();
        Set<AddressDTO> addressesDTO = new HashSet<>();
        for (Address address: addresses) {
            addressesDTO.add(mapper.convertValue(address, AddressDTO.class));
        }

        return addressesDTO;
    }
}
