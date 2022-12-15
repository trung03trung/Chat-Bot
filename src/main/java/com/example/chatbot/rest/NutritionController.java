package com.example.chatbot.rest;

import com.example.chatbot.dto.BmiRequest;
import com.example.chatbot.dto.ResponseView;
import com.example.chatbot.service.ChatBotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin("*")
@RequestMapping("/nutrition")
public class NutritionController {

    private final ChatBotService chatBotService;

    public NutritionController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @GetMapping("/reply")
    public ResponseEntity<ResponseView> handleCustomMessage(@PathParam("message") String message) {
        String reply = chatBotService.sendMessage(message);
        ResponseView responseView = new ResponseView(reply);
        return ResponseEntity.status(HttpStatus.OK).body(responseView);
    }

    @PostMapping("/bmi")
    public ResponseEntity<Float> handleCustomMessage(@RequestBody BmiRequest bmiRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(chatBotService.getBmi(bmiRequest));
    }
}
