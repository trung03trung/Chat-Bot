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
public class Stage {
    @Id
    private Long id;

    private String name;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stage stage = (Stage) o;
        return id != null && Objects.equals(id, stage.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
