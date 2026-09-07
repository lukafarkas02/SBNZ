package com.ftn.model.messages;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.ftn.model.User;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String type;              // NOVO: "PM25_SPIKE", "SUSTAINED_HAZARDOUS", "AIR_RECOVERING"...

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}