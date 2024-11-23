package com.avirantEnterprises.InfoCollector.repository.forms;

import com.avirantEnterprises.InfoCollector.model.forms.TaxForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<TaxForm, Long> {
}
