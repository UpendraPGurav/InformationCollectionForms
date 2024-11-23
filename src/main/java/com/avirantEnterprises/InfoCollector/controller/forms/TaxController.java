package com.avirantEnterprises.InfoCollector.controller.forms;

import com.avirantEnterprises.InfoCollector.model.forms.TaxForm;
import com.avirantEnterprises.InfoCollector.service.forms.PdfService;
import com.avirantEnterprises.InfoCollector.service.forms.TaxService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
public class TaxController {

    @Autowired
    private TaxService taxService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/taxform")
    public String showRegistrationForm() {
        return "forms/taxForm";
    }

    @PostMapping("/submitTaxForm")
    public String register(@RequestParam("year") String year,
                           @RequestParam("amount") String amount,
                           @RequestParam("type") String type,
                           @RequestParam("taxFile") MultipartFile taxFile) {
        taxService.registerTax(year, amount, type, taxFile);
        return "forms/success";
    }

    //user by id
    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable("id") Long id, Model model) {
        TaxForm taxForm = taxService.getTaxById(id);
        System.out.println("Profile file path : " + taxForm.getTaxPath());
        model.addAttribute("taxForm", taxForm);
        return "forms/taxview";
    }

    //all profile
    @GetMapping("/taxList")
    public String showTaxList(Model model) {
        List<TaxForm> taxform = taxService.getAllTaxForm();
        model.addAttribute("taxform", taxform);
        return "forms/taxList";
    }


    //delete by id
    @GetMapping("/tax/delete/{id}")
    public String deleteTaxForm(@PathVariable("id") Long id) {
        taxService.deleteTaxById(id);
        return "redirect:/taxList";
    }


    //update by id
    @GetMapping("/tax/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        TaxForm taxForm = taxService.getTaxById(id);
        model.addAttribute("taxForm", taxForm);
        return "forms/updateForm";
    }

    //
    @PostMapping("/updateTaxForm")
    public String updateTaxForm(@RequestParam("id") Long id,
                                @RequestParam("year") String year,
                                @RequestParam("amount") String amount,
                                @RequestParam("type") String type,
                                @RequestParam("taxFile") MultipartFile taxFile) {
        taxService.updateTax(id, year, amount, type, taxFile);
        return "redirect:/profile/" + id;
    }

    //deleteAll form
    @GetMapping("tax/deleteAll")
    public String deleteAllTaxes() {
        return "redirect:/taxList";
    }



    @GetMapping("/tax/download/{id}")
    public ResponseEntity<byte[]> downloadProfile(@PathVariable("id") Long id) throws DocumentException, IOException {
        byte[] pdfContent = pdfService.generateUserProfilePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "user_profile_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}
