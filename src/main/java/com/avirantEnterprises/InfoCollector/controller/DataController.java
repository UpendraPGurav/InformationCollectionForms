package com.avirantEnterprises.InfoCollector.controller;

import com.avirantEnterprises.InfoCollector.model.Data;
import com.avirantEnterprises.InfoCollector.repository.DataRepository;
import com.avirantEnterprises.InfoCollector.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class DataController {
    @Autowired
    DataService dataService;

    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/data")
    public String data(Model model) {
        model.addAttribute("dataList", dataService.findAllData());
        return "data";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Data data = new Data();
        model.addAttribute("data", data);
        return "registerData";
    }

    @PostMapping("/saveData")
    public String saveData(@ModelAttribute("data") Data data) {
        dataService.saveData(data);
        return "redirect:/data";
    }

    @GetMapping("/updateData/{id}")
    public String updateData(@PathVariable("id") Long id, Model model) {
//        Data existingData = dataRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        Data existingData = dataService.getDataById(id);
        model.addAttribute("existingData", existingData);

        return "updateData";
    }


    @PostMapping("/update/{id}")
    public String updateData(@PathVariable("id") long id,@ModelAttribute("data") Data data,Model model) {
       dataService.updateData(id, data);
        return "redirect:/data";
    }


    @GetMapping("/deleteData/{id}")
    public String deleteData(@PathVariable Long id) {
        dataService.deleteData(id);
        return "redirect:/data";
    }
}

