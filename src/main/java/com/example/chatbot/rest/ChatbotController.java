package com.example.chatbot.rest;

import com.example.chatbot.dto.ResponseView;
import com.example.chatbot.service.ChatBotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin("*")
@RequestMapping("/heath-care")
public class ChatbotController {

    private final ChatBotService chatBotService;

    public ChatbotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @GetMapping("/reply")
    public ResponseEntity<ResponseView> handleCustomMessage(@PathParam("message") String message) {
        ResponseView responseView = chatBotService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(responseView);
    }
}
