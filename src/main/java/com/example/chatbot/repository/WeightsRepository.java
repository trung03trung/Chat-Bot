package com.example.chatbot.repository;

import com.example.chatbot.model.Weights;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightsRepository extends PagingAndSortingRepository<Weights, Long> {
    Weights findByNameAndFeatureIdAndFeatureCompareId(String name, Long featureId, Long compareId);
}
