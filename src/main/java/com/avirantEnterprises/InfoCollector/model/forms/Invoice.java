package com.avirantEnterprises.InfoCollector.model.forms;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceId;

    private LocalDate invoiceDate;
    private String invoiceAmount;
    private String vendorsName;
    private String vendorsEmail;
    private String invoiceFilePath;
}