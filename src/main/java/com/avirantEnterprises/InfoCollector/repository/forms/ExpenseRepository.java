package com.avirantEnterprises.InfoCollector.repository.forms;

import com.avirantEnterprises.InfoCollector.model.forms.ExpenseForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseForm, Long> {
}
