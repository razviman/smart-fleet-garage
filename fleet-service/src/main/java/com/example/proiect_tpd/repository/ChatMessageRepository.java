package com.example.proiect_tpd.repository;

import com.example.proiect_tpd.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
