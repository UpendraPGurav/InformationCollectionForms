package com.avirantEnterprises.InfoCollector.repository.forms;

import com.avirantEnterprises.InfoCollector.model.forms.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
