package com.example.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ResponseBmi {
    Map<String, String> result = new HashMap<>();
}
