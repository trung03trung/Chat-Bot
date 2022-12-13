package com.example.chatbot.repository;

import com.example.chatbot.model.SimilarWeights;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimilarWeightsRepository extends PagingAndSortingRepository<SimilarWeights, Long> {
    SimilarWeights findByNameAndCaseFromIdAndCaseToId(String name, Long from, Long to);
}
