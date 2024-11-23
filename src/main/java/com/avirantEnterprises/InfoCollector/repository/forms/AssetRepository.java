package com.avirantEnterprises.InfoCollector.repository.forms;

import com.avirantEnterprises.InfoCollector.model.forms.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
