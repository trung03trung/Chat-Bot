package com.example.chatbot.repository;

import com.example.chatbot.model.MedicalRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends PagingAndSortingRepository<MedicalRecord,Long> {
    MedicalRecord findFirstByNameContaining(String name);
}
