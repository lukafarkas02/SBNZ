package com.ftn.model.messages;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public abstract class Message {
    private Long id;
    private String content;          // Tekst poruke
    private LocalDateTime timestamp; // Kada je poslata
}
