package com.avirantEnterprises.InfoCollector.controller.forms;

import com.avirantEnterprises.InfoCollector.model.forms.ExpenseForm;
import com.avirantEnterprises.InfoCollector.service.forms.ExpenseService;
import com.avirantEnterprises.InfoCollector.service.forms.PdfService;
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
import java.util.List;

@Controller
public class ExpenseFormController {

    @Autowired
    private  ExpenseService expenseService;

    @Autowired
    private PdfService pdfService;


    @GetMapping("/expenseform")
    public String showExpenseForm() {
        return "forms/expenseForm";
    }

    @PostMapping("/submitExpenseForm")
    public String registerExpense(@RequestParam("expenseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expenseDate,
                                  @RequestParam("expenseAmount") String expenseAmount,
                                  @RequestParam("expenseCategory") String expenseCategory,
                                  @RequestParam("expenseDescription") String expenseDescription,
                                  @RequestParam("expenseFile") MultipartFile expenseFile) {
        expenseService.registerExpense(expenseDate, expenseAmount, expenseCategory, expenseDescription, expenseFile);
        return "forms/success";
    }

    @GetMapping("/expenseUser/{id}")
    public String showProfile(@PathVariable("id") Long id, Model model) {
        ExpenseForm expenseForm = expenseService.getExpenseById(id);
        System.out.println("Profile file path : " + expenseForm.getExpensePath());
        model.addAttribute("expenseForm", expenseForm);
        return "taxview";
    }

    @GetMapping("/expenseList")
    public String showExpenseList(Model model) {
        List<ExpenseForm> expenseForm = expenseService.getAllExpenses();
        model.addAttribute("expenseForm", expenseForm);
        return "forms/expenseList";
    }

    @GetMapping("/expenseUser/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        ExpenseForm expenseForm = expenseService.getExpenseById(id);
        model.addAttribute("expenseForm", expenseForm);
        return "forms/expenseUpdateForm";
    }

    @PostMapping("/updateExpenseForm")
    public String updateExpense(@RequestParam("id") Long id,
                                @RequestParam("expenseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expenseDate,
                                  @RequestParam("expenseAmount") String expenseAmount,
                                  @RequestParam("expenseCategory") String expenseCategory,
                                  @RequestParam("expenseDescription") String expenseDescription,
                                  @RequestParam("expenseFile") MultipartFile expenseFile) {
        expenseService.updateExpense(id,expenseDate, expenseAmount, expenseCategory, expenseDescription, expenseFile);
        return "forms/success";
    }


    @GetMapping("/expenseUser/download/{id}")
    public ResponseEntity<byte[]> downloadProfile(@PathVariable("id") Long id) throws DocumentException, IOException {
        byte[] pdfContent = pdfService.generateUserProfilePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "user_profile_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}
