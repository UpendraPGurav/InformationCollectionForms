package com.avirantEnterprises.InfoCollector.service;

import com.avirantEnterprises.InfoCollector.model.Data;

public interface DataService {
    public void saveData(Data data);

    Iterable<Data> findAllData();

    Data getDataById(Long id);

    public void deleteData(Long id);

    public void updateData(Long id, Data data);
}
