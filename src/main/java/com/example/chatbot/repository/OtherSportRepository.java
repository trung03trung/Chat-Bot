package com.example.chatbot.repository;

import com.example.chatbot.model.OtherSport;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherSportRepository extends PagingAndSortingRepository<OtherSport,Long> {
    OtherSport findByDescriptionContaining(String des);
}
