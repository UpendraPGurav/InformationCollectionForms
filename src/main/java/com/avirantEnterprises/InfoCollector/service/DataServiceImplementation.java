package com.avirantEnterprises.InfoCollector.service;

import com.avirantEnterprises.InfoCollector.model.Data;
import com.avirantEnterprises.InfoCollector.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImplementation implements DataService{

    @Autowired
    private DataRepository dataRepository;

    @Override
    public void saveData(Data data) {
        dataRepository.save(data);
    }

    @Override
    public Iterable<Data> findAllData() {
        return dataRepository.findAll();
    }

    @Override
    public Data getDataById(Long id) {
        return dataRepository.findById(id).orElse(null);
//        return dataRepository.findById(id).orElseThrow(()-> new RuntimeException("Id not found"));
    }

    @Override
    public void deleteData(Long id) {
        dataRepository.deleteById(id);
    }

    @Override
    public void updateData(Long id, Data data) {
        Data existingData = dataRepository.findById(id).get();
        existingData.setName(data.getName()); // Update other fields as necessary
        existingData.setEmail(data.getEmail());
        existingData.setMessage(data.getMessage());

        dataRepository.save(existingData);
    }
}
