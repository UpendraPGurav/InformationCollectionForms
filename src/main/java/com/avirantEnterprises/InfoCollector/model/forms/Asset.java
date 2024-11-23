package com.avirantEnterprises.InfoCollector.model.forms;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assetId;

    private String assetName;
    private String assetType;
    private LocalDate assetDate;
    private String assetValue;
    private String assetFilePath;
}
