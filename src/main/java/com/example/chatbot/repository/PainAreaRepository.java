package com.example.chatbot.repository;

import com.example.chatbot.model.PainArea;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PainAreaRepository extends PagingAndSortingRepository<PainArea,Long> {
    PainArea findFirstByNameContaining(String name);
    List<PainArea> findByNameContaining(String name);
}
