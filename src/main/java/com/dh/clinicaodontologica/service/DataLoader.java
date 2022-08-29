package com.dh.clinicaodontologica.service;

import com.dh.clinicaodontologica.model.AppUser;
import com.dh.clinicaodontologica.model.AppUserRole;
import com.dh.clinicaodontologica.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private IUserRepository userRepository;

    @Autowired
    public DataLoader(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("passwordadmin");
        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String hashedPassword2 = passwordEncoder2.encode("passworduser");
        userRepository.save(new AppUser("German", "Binder", "germanbinder@email.com", "Ger", hashedPassword, AppUserRole.ADMIN));
        userRepository.save(new AppUser("Cintia", "Castillo", "cintiacastillo@email.com", "Cin", hashedPassword2, AppUserRole.USER));
    }
}
