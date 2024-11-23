package com.avirantEnterprises.InfoCollector.model.forms;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TaxForm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taxId;
    private String taxYear;
    private String taxAmount;
    private String taxType;
    private String taxPath;
}
