package com.avirantEnterprises.InfoCollector.service.forms;

import com.avirantEnterprises.InfoCollector.model.forms.Asset;
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
public class PdfAssetService {

    @Autowired
    private AssetService assetService;

    private final Path rootLocation = Paths.get("upload-dir");

    public byte[] generateUserProfilePdf(Long assetId) throws IOException, DocumentException {
        Asset asset = assetService.getAssetById(assetId);

        if (asset == null) {
            throw new RuntimeException("Asset not found");
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

        table.addCell("Asset Name");
        table.addCell(asset.getAssetName());

        table.addCell("Asset Type");
        table.addCell(asset.getAssetType());

        table.addCell("Asset Date");
        table.addCell(String.valueOf(asset.getAssetDate()));

        table.addCell("Asset Value");
        table.addCell(String.valueOf(asset.getAssetValue()));


        document.add(table);

        if (asset.getAssetFilePath() != null) {
            String sanitizedFilePath = rootLocation.resolve(Paths.get(asset.getAssetFilePath()))
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
