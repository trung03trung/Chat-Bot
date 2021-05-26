package com.example.chatbot;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ChatBotService {

    private ScheduledExecutorService executorService;

    private Chat chatSession;

    @Autowired
    public ChatBotService(Bot alice, ScheduledExecutorService executorService) {
        this.executorService = executorService;
        chatSession = new Chat(alice);

    }

    public String sendMessage(String text) {
//        executorService.schedule(() -> {
//            String answer = chatSession.multisentenceRespond(text);
//        }, new Random().ints(1000, 3000).findFirst().getAsInt(), TimeUnit.MILLISECONDS);

        String answer = chatSession.multisentenceRespond(text);
        System.out.print(answer);
        return answer;
    }
}
