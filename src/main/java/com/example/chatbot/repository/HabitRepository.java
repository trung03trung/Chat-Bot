package com.example.chatbot.repository;

import com.example.chatbot.model.Habit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends PagingAndSortingRepository<Habit,Long> {
    Habit findFirstByNameContaining(String name);
}
