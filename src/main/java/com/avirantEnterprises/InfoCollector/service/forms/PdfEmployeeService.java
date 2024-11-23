package com.avirantEnterprises.InfoCollector.service.forms;


import com.avirantEnterprises.InfoCollector.model.forms.Employee;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfEmployeeService {
    @Autowired
    private EmployeeService employeeService;

    private final Path rootLocation = Paths.get("upload-dir");

    public byte[] generateUserProfilePdf(Long empId) throws IOException, DocumentException {
        Employee emp = employeeService.getEmpById(empId);

        if (emp == null) {
            throw new RuntimeException("Employee not found");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        document.add(new Paragraph("User Profile"));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell("Employee Name");
        table.addCell(emp.getEmpName());

        table.addCell("Employee Rating");
        table.addCell(emp.getEmpRating());

        table.addCell("Review Month");
        table.addCell(String.valueOf(emp.getEmpPeriod()));



        document.add(table);

        if (emp.getEmpFilePath() != null) {
            String sanitizedFilePath = rootLocation.resolve(Paths.get(emp.getEmpFilePath()))
                    .normalize().toAbsolutePath().toString();
            Image image = Image.getInstance(Files.readAllBytes(Paths.get(sanitizedFilePath)));
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleToFit(100, 400); // Set the size of the image
            document.add(image);
        }

        document.close();

        return outputStream.toByteArray();
    }
}
