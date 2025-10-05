package com.ftn.service;

import com.ftn.model.messages.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Long> {
    // Nema dodatnih metoda potrebnih za osnovne CRUD operacije
}
