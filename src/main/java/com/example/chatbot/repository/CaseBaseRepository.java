package com.example.chatbot.repository;

import com.example.chatbot.model.CaseBase;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseBaseRepository extends PagingAndSortingRepository<CaseBase,Long> {
}
