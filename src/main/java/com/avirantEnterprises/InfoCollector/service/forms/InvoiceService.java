package com.avirantEnterprises.InfoCollector.service.forms;

import com.avirantEnterprises.InfoCollector.model.forms.Asset;
import com.avirantEnterprises.InfoCollector.model.forms.Invoice;
import com.avirantEnterprises.InfoCollector.repository.forms.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private InvoiceRepository invoiceRepository;

    public void registerInvoice(LocalDate invoiceDate, String invoiceAmount, String vendorsName, String vendorsEmail, MultipartFile invoiceFile) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(invoiceDate);
        invoice.setInvoiceAmount(invoiceAmount);
        invoice.setVendorsName(vendorsName);
        invoice.setVendorsEmail(vendorsEmail);

        String filePath = saveFile(invoiceFile);
        invoice.setInvoiceFilePath(filePath);
        invoiceRepository.save(invoice);
    }

    private String saveFile(MultipartFile file) {
        try {
            Files.createDirectories(rootLocation);
            String sanitizedFileName = sanitizeFileName(file.getOriginalFilename());
            Path destinationFile = rootLocation.resolve(Paths.get(sanitizedFileName))
                    .normalize().toAbsolutePath();
            file.transferTo(destinationFile);
            return sanitizedFileName;  // Store only the sanitized file name
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).orElse(null);
    }

    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    public void deleteInvoiceById(Long id) {
        invoiceRepository.deleteById(id);
    }


    public void updateInvoicet(Long invoiceId,LocalDate invoiceDate, String invoiceAmount, String vendorsName, String vendorsEmail, MultipartFile invoiceFile) {
        Optional<Invoice> optInvoice = invoiceRepository.findById(invoiceId);

        if(optInvoice.isPresent()) {
            Invoice invoice = optInvoice.get();
            invoice.setInvoiceDate(invoiceDate);
            invoice.setInvoiceAmount(invoiceAmount);
            invoice.setVendorsName(vendorsName);
            invoice.setVendorsEmail(vendorsEmail);

            if (!invoiceFile.isEmpty()) {
                String filePath = saveFile(invoiceFile);
                invoice.setInvoiceFilePath(filePath);
            }
            invoiceRepository.save(invoice);
        }
    }
}
