package com.example.chatbot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "weights")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Weights {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "feature_id", nullable = false)
    private Long featureId;

    @Column(name = "feature_compare_id", nullable = false)
    private Long featureCompareId;

    private Float value;
}
