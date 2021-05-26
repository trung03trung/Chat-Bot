package com.example.chatbot;


import com.github.messenger4j.Messenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

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
        System.out.print(pageAccessToken);
        return Messenger.create(pageAccessToken, appSecret, verifyToken);
    }

}
