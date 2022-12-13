package com.example.chatbot.config;


import com.github.messenger4j.Messenger;
import org.alicebot.ab.Bot;
import org.alicebot.ab.configuration.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class MessengerConfig {

    @Value("${messenger4j.pageAccessToken}")
    String pageAccessToken;


    @Value("${messenger4j.appSecret}")
    String appSecret;


    @Value("${messenger4j.verifyToken}")
    String verifyToken;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Messenger messenger() {
        return Messenger.create(pageAccessToken, appSecret, verifyToken);
    }


    @Bean
    public Bot alice() {
        return new Bot(BotConfiguration.builder()
                .name("alice")
                .path("src/main/resources")
                .build()
        );
    }

    @Bean
    public ScheduledExecutorService executorService() {
        return Executors.newScheduledThreadPool(2);
    }

}
