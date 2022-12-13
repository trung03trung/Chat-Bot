package com.example.chatbot.repository;

import com.example.chatbot.model.Stage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends PagingAndSortingRepository<Stage, Long> {

    Stage findByDescriptionContaining(String stage);

}
