package com.ftn.service;

import com.ftn.model.*;
import com.ftn.service.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(UserRegisterDTO dto) throws Exception {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setLocation(dto.getLocation());

        if (dto.getUserType().equalsIgnoreCase("citizen")) {
            user.setUserType(UserType.CITIZEN);
        } else if (dto.getUserType().equalsIgnoreCase("risk_group")) {
            user.setUserType(UserType.RISK_GROUP);
            user.setRiskType(RiskType.valueOf(dto.getRiskType().toUpperCase()));
        } else if (dto.getUserType().equalsIgnoreCase("institution")) {
            user.setUserType(UserType.INSTITUTION);
            user.setInstitutionName(dto.getInstitutionName());
            // user.setInstitutionType(dto.getInstitutionType());
            user.setInstitutionType(InstitutionType.valueOf(dto.getInstitutionType().toUpperCase()));
            user.setInstitutionAddress(dto.getInstitutionAddress());
        }

        return userRepository.save(user);
    }

    public User login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid password");
        }

        return user;
    }

}
