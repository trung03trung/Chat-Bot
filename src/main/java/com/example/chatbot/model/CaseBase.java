package com.example.chatbot.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class CaseBase {
    @Id
    private Long id;

    private Long stageId;

    private Long bmiId;

    private Long habitId;

    private Long exerciseId;

    private Long otherSportId;

    private String nutrition;

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
