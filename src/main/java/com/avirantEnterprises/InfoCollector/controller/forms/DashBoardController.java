package com.avirantEnterprises.InfoCollector.controller.forms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoardController {

    @GetMapping("/userdash")
    public String userdash() {
        return "forms/userdash";
    }
}
