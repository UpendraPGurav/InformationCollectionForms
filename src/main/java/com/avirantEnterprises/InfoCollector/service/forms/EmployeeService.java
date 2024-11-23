package com.avirantEnterprises.InfoCollector.service.forms;

import com.avirantEnterprises.InfoCollector.model.forms.Asset;
import com.avirantEnterprises.InfoCollector.model.forms.Employee;
import com.avirantEnterprises.InfoCollector.repository.forms.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private EmployeeRepository employeeRepository;

    public void registerEmployee(String empName, String empRating, YearMonth empPeriod, MultipartFile empFile) {
        Employee emp = new Employee();
        emp.setEmpName(empName);
        emp.setEmpRating(empRating);
        emp.setEmpPeriod(empPeriod);
        String filePath = saveFile(empFile);
        emp.setEmpFilePath(filePath);
        employeeRepository.save(emp);
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

    public Employee getEmpById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAllEmps() {
        return employeeRepository.findAll();
    }

    public void deleteEmpById(Long id) {
        employeeRepository.deleteById(id);
    }


    public void updateEmp(Long empId, String empName, String empRating, YearMonth empPeriod, MultipartFile empFile) {
        Optional<Employee> optEmp = employeeRepository.findById(empId);
        if (optEmp.isPresent()) {
            Employee emp = optEmp.get();
            emp.setEmpName(empName);
            emp.setEmpRating(empRating);
            emp.setEmpPeriod(empPeriod);

            if (!empFile.isEmpty()) {
                String filePath = saveFile(empFile);
                emp.setEmpFilePath(filePath);
            }
            employeeRepository.save(emp);
        }
    }
}
