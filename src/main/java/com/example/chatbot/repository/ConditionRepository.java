package com.example.chatbot.repository;

import com.example.chatbot.model.Condition;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends PagingAndSortingRepository<Condition,Long> {
    Condition findConditionByNameContaining(String name);
}
