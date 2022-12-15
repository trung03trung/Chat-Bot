package com.example.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BmiRequest {

    private Integer sex;

    private int age;

    private float weight;

    private Integer height;
}
