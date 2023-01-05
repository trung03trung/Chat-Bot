package com.example.chatbot.repository;

import com.example.chatbot.model.FootSymptoms;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootSymptomsRepository extends PagingAndSortingRepository<FootSymptoms,Long> {
    FootSymptoms findByNameContaining(String name);
}
