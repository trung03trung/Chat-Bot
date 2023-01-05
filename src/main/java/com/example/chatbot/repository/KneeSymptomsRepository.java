package com.example.chatbot.repository;

import com.example.chatbot.model.KneeSymptoms;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KneeSymptomsRepository extends PagingAndSortingRepository<KneeSymptoms,Long> {
    KneeSymptoms findByNameContaining(String name);
}
