package com.example.chatbot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "case_base")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseBase {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne()
    @JoinColumn(name="condition_id")
    private Condition condition;

    @ManyToOne()
    @JoinColumn(name="foot_symptoms_id")
    private FootSymptoms footSymptoms;

    @ManyToOne()
    @JoinColumn(name="knee_symptoms_id")
    private KneeSymptoms kneeSymptoms;

    @ManyToOne()
    @JoinColumn(name="habit_id")
    private Habit habit;

    @ManyToOne()
    @JoinColumn(name="medical_record_id")
    private MedicalRecord medicalRecord;

    @ManyToOne()
    @JoinColumn(name="pain_area_id")
    private PainArea painArea;

    private Long sex;

    private String treatment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CaseBase caseBase = (CaseBase) o;
        return id != null && Objects.equals(id, caseBase.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
