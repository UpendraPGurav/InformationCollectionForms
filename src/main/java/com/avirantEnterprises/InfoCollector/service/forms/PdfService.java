package com.avirantEnterprises.InfoCollector.service.forms;

import com.avirantEnterprises.InfoCollector.model.forms.TaxForm;
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
public class PdfService {

    @Autowired
    private TaxService taxService;

    private final Path rootLocation = Paths.get("upload-dir");

    public byte[] generateUserProfilePdf(Long taxId) throws IOException, DocumentException {
        TaxForm tax = taxService.getTaxById(taxId);
        if (tax == null) {
            throw new RuntimeException("User not found");
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

        table.addCell("Tax Year");
        table.addCell(tax.getTaxYear());

        table.addCell("Tax Amount");
        table.addCell(tax.getTaxAmount());

        table.addCell("Tax type");
        table.addCell(tax.getTaxType());

        document.add(table);

        if (tax.getTaxPath() != null) {
            String sanitizedFilePath = rootLocation.resolve(Paths.get(tax.getTaxPath()))
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
