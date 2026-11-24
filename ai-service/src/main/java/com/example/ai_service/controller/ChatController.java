package com.example.ai_service.controller;

import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(@Qualifier("fleetChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // chatul standard cu memorie conversationala 
    @GetMapping("/chat")
    public String chat(
            @RequestHeader(value = "X-Conversation-Id", defaultValue = "razvan-session-1") String conversationId,
            @RequestParam("message") String message) {

        return this.chatClient.prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }

    // 2. chatul cu streaming pentru a trimite raspunsul partial pe masura ce este generat de model
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(
            @RequestHeader(value = "X-Conversation-Id", defaultValue = "razvan-session-1") String conversationId,
            @RequestParam("message") String message) {

        return this.chatClient.prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .stream()
                .content();
    }

    // 3. testarea temperaturii pentru a vedea cum afecteaza raspunsurile modelului
    @GetMapping("/chat/temperature-test")
    public String testTemperature(
            @RequestParam("symptom") String symptom,
            @RequestParam(value = "creative", defaultValue = "false") boolean creative) {

        // Temperature 0.1 + Top-P 0.2 -> creative = false -> raspunsuri mai conservative, mai putin variate
        // Temperature 0.9 + Top-P 0.9 -> creative = true -> raspunsuri mai creative, mai variate, mai putin predictibile
        OllamaChatOptions.Builder options = creative
                ? OllamaChatOptions.builder().temperature(0.9).topP(0.9)
                : OllamaChatOptions.builder().temperature(0.1).topP(0.2);
        return this.chatClient.prompt()
                .options(options)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, UUID.randomUUID().toString())) // sesiune unica pentru fiecare test
                .user("Enumera pe scurt 3 cauze posibile pentru urmatorul simptom auto: " + symptom)
                .call()
                .content();
    }
}