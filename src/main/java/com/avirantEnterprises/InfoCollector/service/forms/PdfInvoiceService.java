package com.avirantEnterprises.InfoCollector.service.forms;


import com.avirantEnterprises.InfoCollector.model.forms.Invoice;
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
import java.util.Optional;

@Service
public class PdfInvoiceService {

    @Autowired
    private InvoiceService invoiceService;

    private final Path rootLocation = Paths.get("upload-dir");

    public byte[] generateUserProfilePdf(Long invoiceId) throws IOException, DocumentException {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);

        if (invoice==null) {
            throw new RuntimeException("invoice not found");
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

        table.addCell("Invoice Date");
        table.addCell(String.valueOf(invoice.getInvoiceDate()));

        table.addCell("Invoice Amount");
        table.addCell(invoice.getInvoiceAmount());

        table.addCell("Invoice Vendors Name");
        table.addCell(String.valueOf(invoice.getVendorsName()));

        table.addCell("Invoice Vendors Email");
        table.addCell(String.valueOf(invoice.getVendorsEmail()));


        document.add(table);

        if (invoice.getInvoiceFilePath() != null) {
            String sanitizedFilePath = rootLocation.resolve(Paths.get(invoice.getInvoiceFilePath()))
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
