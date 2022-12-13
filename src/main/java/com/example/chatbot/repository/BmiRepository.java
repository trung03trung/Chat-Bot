package com.example.chatbot.repository;

import com.example.chatbot.model.Bmi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;

@Repository
public interface BmiRepository extends PagingAndSortingRepository<Bmi, Long> {

    @Query(value = "select u from Bmi u where u.startValue <= :value and u.endValue > :value")
    Bmi findByValue(@PathParam("value") Float value);
}
