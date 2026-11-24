package com.example.ai_service.config;

import com.example.ai_service.advisor.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    @Bean
    public ChatMemory chatMemory() {
        // pastram doar ultimele 20 de mesaje for now
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }

    // wrapperul de ChatClient cu advisorii necesari pentru logging, audit si memory
    @Bean("fleetChatClient")
    public ChatClient fleetChatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor tokenAuditAdvisor = new TokenUsageAuditAdvisor();

        return builder
                .defaultSystem("""
                        Esti un asistent tehnic auto expert pentru platforma Smart Fleet & Garage.
                        Raspunde clar, structurat si la obiect in limba romana.
                        """)
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenAuditAdvisor))
                .build();
    }
}