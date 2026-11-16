package com.example.ai_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ai.chat.client.ChatClient;

@SpringBootApplication
public class AiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiServiceApplication.class, args);
	}

	@RestController
    @RequestMapping("/api")
    public static class ChatController {
        private final ChatClient chatClient;
        public ChatController(ChatClient.Builder chatClientBuilder)
        {
            this.chatClient = chatClientBuilder.build();
        }
        @GetMapping("/chat")
        String chat(@RequestParam("message") String message) {
            return this.chatClient.prompt(message)
                    .call()
                    .content();
        }
    }

}
