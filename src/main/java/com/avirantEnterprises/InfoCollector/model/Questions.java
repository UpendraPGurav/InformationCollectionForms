package com.avirantEnterprises.InfoCollector.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    private String text;
    private String type;//eg. text, multiple-choice checkbox

}
