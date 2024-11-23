package com.avirantEnterprises.InfoCollector.service.forms;

import com.avirantEnterprises.InfoCollector.model.forms.ExpenseForm;
import com.avirantEnterprises.InfoCollector.repository.forms.ExpenseRepository;
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
public class ExpenseService {

    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private ExpenseRepository expenseRepository;

    public void registerExpense(LocalDate expenseDate, String expenseAmount, String expenseCategory, String expenseDescription, MultipartFile expenseFile) {
        ExpenseForm expenseForm = new ExpenseForm();
        expenseForm.setExpenseDate(expenseDate);
        expenseForm.setExpenseAmount(expenseAmount);
        expenseForm.setExpenseCategory(expenseCategory);
        expenseForm.setExpenseDescription(expenseDescription);
        String expenseFilePath = saveFile(expenseFile);
    }

    public void updateExpense(Long id, LocalDate expenseDate, String expenseAmount, String expenseCategory, String expenseDescription, MultipartFile expenseFile) {
        Optional<ExpenseForm> optionalExpenseForm = expenseRepository.findById(id);
        if(optionalExpenseForm.isPresent()){
            ExpenseForm expenseForm = (ExpenseForm) optionalExpenseForm.get();
            expenseForm.setExpenseDate(expenseDate);
            expenseForm.setExpenseAmount(expenseAmount);
            expenseForm.setExpenseCategory(expenseCategory);
            expenseForm.setExpenseDescription(expenseDescription);
            if(!expenseFile.isEmpty()){
                String expenseFilePath = saveFile(expenseFile);
                expenseForm.setExpensePath(expenseFilePath);
            }
            expenseRepository.save(expenseForm);
        }
    }

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


    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9]", "_");
    }

    public ExpenseForm getExpenseById(Long id){
        return expenseRepository.findById(id).orElse(null);
    }

    public List<ExpenseForm> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public void deleteExpenseById(Long id){
        expenseRepository.deleteById(id);
    }
}
