package com.avirantEnterprises.InfoCollector.service.forms;

import com.avirantEnterprises.InfoCollector.model.forms.TaxForm;
import com.avirantEnterprises.InfoCollector.repository.forms.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class TaxService {
    private final Path rootLocation = Paths.get("upload-dir");
    @Autowired
    private TaxRepository taxRepository;

    public void registerTax(String year, String amount, String type, MultipartFile taxFile) {
        TaxForm tax = new TaxForm();
        tax.setTaxYear(year);
        tax.setTaxAmount(amount);
        tax.setTaxType(type);
        String taxFilePath = saveFile(taxFile);
        tax.setTaxPath(taxFilePath);
        taxRepository.save(tax);
    }

    public void updateTax(Long id, String year, String amount, String type, MultipartFile taxFile) {
        Optional<TaxForm> optionalTaxForm = taxRepository.findById(id);
        if (optionalTaxForm.isPresent()) {
            TaxForm taxForm = optionalTaxForm.get();
            taxForm.setTaxYear(year);
            taxForm.setTaxAmount(amount);
            taxForm.setTaxType(type);
            if (!taxFile.isEmpty()) {
                String taxFilePath = saveFile(taxFile);
                taxForm.setTaxPath(taxFilePath);
            }
            taxRepository.save(taxForm);
        }
    }

    public TaxForm getTaxById(Long id) {
        return taxRepository.findById(id).orElse(null);
    }

    public List<TaxForm> getAllTaxForm(){
        return taxRepository.findAll();
    }

    public void deleteTaxById(Long id) {
        taxRepository.deleteById(id);
    }
    public void deleteAllTaxForm() {
        taxRepository.deleteAll();
    }




    //file
    private String sanitizeFilename(String filename) {return filename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");}

    //save image
    private String saveFile(MultipartFile file) {
        try {
            Files.createDirectories(rootLocation);
            String sanitizedFileName = sanitizeFilename(file.getOriginalFilename());
            Path destinationFile = rootLocation.resolve(Paths.get(sanitizedFileName))
                .normalize().toAbsolutePath();
            file.transferTo(destinationFile);
            return sanitizedFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
