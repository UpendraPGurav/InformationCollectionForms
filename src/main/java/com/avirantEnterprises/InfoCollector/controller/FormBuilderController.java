package com.avirantEnterprises.InfoCollector.controller;

import com.avirantEnterprises.InfoCollector.model.Form;
import com.avirantEnterprises.InfoCollector.repository.FormRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class FormBuilderController {
    @Autowired
    private FormRepo formRepo;

    @GetMapping("/formBuilder")
    public String formBuilder(Model model) {
        model.addAttribute("form", new Form());
        return "formBuilder";
    }

    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute("form") Form form) {
        formRepo.save(form);
        return "redirect:/forms";
    }

    @GetMapping("/forms")
    public String showForms(Model model) {
        model.addAttribute("forms", formRepo.findAll());
        return "forms";
    }



    @GetMapping("/editForm/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Form> form = formRepo.findById(id);
        model.addAttribute("form", form);
        return "formBuilder";
    }

    @GetMapping("/deleteForm/{id}")
    public String deleteForm(@PathVariable Long id) {
        formRepo.deleteById(id);
        return "redirect:/forms";
    }
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    //Expense Form
    @GetMapping("/expenseForm")
    public String expenseForm(Model model) {
        model.addAttribute("form", new Form());
        return "expenseForm";
    }
}

