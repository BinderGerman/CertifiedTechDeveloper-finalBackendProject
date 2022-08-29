package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final com.dh.clinicaodontologica.repository.IUserRepository userRepository;

    @Autowired
    public AppUserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        return userRepository.findByEmail(email).get();
    }
}
