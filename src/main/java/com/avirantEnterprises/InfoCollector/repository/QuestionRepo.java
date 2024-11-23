package com.avirantEnterprises.InfoCollector.repository;

import com.avirantEnterprises.InfoCollector.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Questions,Long> {
}
