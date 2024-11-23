package com.avirantEnterprises.InfoCollector.controller.forms;


import com.avirantEnterprises.InfoCollector.model.forms.Employee;
import com.avirantEnterprises.InfoCollector.service.forms.EmployeeService;
import com.avirantEnterprises.InfoCollector.service.forms.PdfEmployeeService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PdfEmployeeService pdfEmployeeService;


    //Show Register form
    @GetMapping("/employee")
    public String showAssetForm() {
        return "forms/employeePerformanceForm";
    }

    //submit form
    @PostMapping("/registerEmployee")
    public String registerAsset(@RequestParam("empName") String empName,
                                @RequestParam("empRating") String empRating,
                                @RequestParam("empPeriod") YearMonth empPeriod,
//                                @RequestParam("empPeriod") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) YearMonth empPeriod,
                                @RequestParam("empFile") MultipartFile empFile) {
        String monthYear = empPeriod.format(DateTimeFormatter.ofPattern("MM-yyyy"));
        employeeService.registerEmployee(empName, empRating, empPeriod, empFile);
        return "forms/success";
    }

    //get view by id
    @GetMapping("/employee/{id}")
    public String viewAsset(@PathVariable("id") Long id, Model model) {
        Employee emp = employeeService.getEmpById(id);
        System.out.println("Profile Picture Path: " + emp.getEmpFilePath());
        model.addAttribute("emp", emp);
        return "forms/employeeView";
    }


    //get all list
    @GetMapping("/employeeList")
    public String listUserProfiles(Model model) {
        List<Employee> emp = employeeService.getAllEmps();
        model.addAttribute("emp", emp);
        return "forms/employeeList";
    }


    @GetMapping("/employee/update/{id}")
    public String viewToUpdateEmployee(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmpById(id);
//        System.out.println("Profile Picture Path: " + asset.getAssetFilePath());
        model.addAttribute("employee", employee);
        return "forms/updateEmployee";
    }

    @PostMapping("/updateEmployee")
    public String updateAsset(@RequestParam("empId") Long empId,
                              @RequestParam("empName") String empName,
                              @RequestParam("empRating") String empRating,
                              @RequestParam("empPeriod") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) YearMonth empPeriod,
                              @RequestParam("empFile") MultipartFile empFile) {
        employeeService.updateEmp(empId, empName, empRating, empPeriod, empFile);
        return "redirect:/employee/" + empId;
    }


    //Download Pdf
    @GetMapping("/employee/download/{id}")
    public ResponseEntity<byte[]> downloadUserProfilePdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfEmployeeService.generateUserProfilePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "user_profile_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}
