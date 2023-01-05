package com.example.chatbot.repository;

import com.example.chatbot.model.PainArea;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PainAreaRepository extends PagingAndSortingRepository<PainArea,Long> {
    PainArea findByNameContaining(String name);
}
