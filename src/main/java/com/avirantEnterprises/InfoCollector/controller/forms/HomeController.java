package com.avirantEnterprises.InfoCollector.controller.forms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Avirant Information !");
        return "forms/userdash"; // Refers to home.html template
    }
}
