package com.avirantEnterprises.InfoCollector.model.forms;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class ExpenseForm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long expenseId;

    private LocalDate expenseDate;
    private String expenseAmount;
    private  String expenseCategory;
    private  String expenseDescription;
    private  String expensePath;
}
