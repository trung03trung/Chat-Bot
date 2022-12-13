package com.example.chatbot.repository;

import com.example.chatbot.model.ExerciseIntensity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseIntensityRepository extends PagingAndSortingRepository<ExerciseIntensity,Long> {
    ExerciseIntensity findByDescriptionContaining(String des);
}
