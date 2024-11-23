package com.avirantEnterprises.InfoCollector.controller.forms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {

        return "forms/login";
    }
}
