package com.avirantEnterprises.InfoCollector.repository.forms;

import com.avirantEnterprises.InfoCollector.model.forms.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
