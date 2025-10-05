package com.ftn.service;

import com.ftn.model.messages.Warning;
import com.ftn.model.User;
import com.ftn.service.WarningRepository;
import com.ftn.service.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningService {

    private final WarningRepository warningRepository;
    private final UserRepository userRepository;

    public WarningService(WarningRepository warningRepository, UserRepository userRepository) {
        this.warningRepository = warningRepository;
        this.userRepository = userRepository;
    }

    public List<Warning> getWarningsByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return warningRepository.findByUser(user);
    }
}
