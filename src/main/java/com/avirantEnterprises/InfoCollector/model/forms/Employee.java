package com.avirantEnterprises.InfoCollector.model.forms;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long empId;

    private String empName;
    private String empRating;
    private YearMonth empPeriod;
    private String empFilePath;
}
