package com.avirantEnterprises.InfoCollector.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long formId;
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Questions> questions = new ArrayList<>();
}
