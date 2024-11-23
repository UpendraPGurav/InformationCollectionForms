package com.avirantEnterprises.InfoCollector.controller.forms;


import com.avirantEnterprises.InfoCollector.model.forms.Invoice;
import com.avirantEnterprises.InfoCollector.service.forms.InvoiceService;
import com.avirantEnterprises.InfoCollector.service.forms.PdfInvoiceService;
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
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PdfInvoiceService pdfInvoiceService;


    //Show Register form
    @GetMapping("/invoice")
    public String showIvoicForm() {
        return "forms/invoiceForm";
    }

    //submit fomr
    @PostMapping("/registerInvoice")
    public String registerAsset(@RequestParam("invoiceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate invoiceDate,
                                @RequestParam("invoiceAmount") String invoiceAmount,
                                @RequestParam("vendorsName") String vendorsName,
                                @RequestParam("vendorsEmail") String vendorsEmail,
                                @RequestParam("invoiceFile") MultipartFile invoiceFile) {
        invoiceService.registerInvoice(invoiceDate, invoiceAmount, vendorsName, vendorsEmail, invoiceFile);
        return "forms/success";
    }

    //get view by id
    @GetMapping("/invoice/{id}")
    public String viewInvoice(@PathVariable("id") Long invoiceId, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
//        System.out.println("Profile Picture Path: " + invoice.getClass();
        model.addAttribute("invoice", invoice);
        return "forms/invoiceView";
    }


    //get all list
    @GetMapping("/invoiceList")
    public String listUserProfiles(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoice();
        model.addAttribute("invoice", invoices);
        return "forms/invoiceList";
    }


    @GetMapping("/invoice/update/{id}")
    public String viewToUpdateInvoice(@PathVariable("id") Long invoiceId, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
//        System.out.println("Profile Picture Path: " + invoice.getClass();
        model.addAttribute("invoice", invoice);
        return "forms/updateInvoice";
    }

    @PostMapping("/updateInvoice")
    public String updateAsset(@RequestParam("invoiceId") Long invoiceId,
                              @RequestParam("invoiceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate invoiceDate,
                              @RequestParam("invoiceAmount") String invoiceAmount,
                              @RequestParam("vendorsName") String vendorsName,
                              @RequestParam("vendorsEmail") String vendorsEmail,
                              @RequestParam("invoiceFile") MultipartFile invoiceFile) {
        invoiceService.updateInvoicet(invoiceId, invoiceDate, invoiceAmount, vendorsName, vendorsEmail, invoiceFile);
        return "redirect:/invoice/" + invoiceId;
    }


    //Download Pdf
    @GetMapping("/invoice/download/{id}")
    public ResponseEntity<byte[]> downloadUserProfilePdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfInvoiceService.generateUserProfilePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "user_profile_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}
