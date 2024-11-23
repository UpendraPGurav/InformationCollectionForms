package com.avirantEnterprises.InfoCollector.service.forms;


import com.avirantEnterprises.InfoCollector.model.forms.Asset;
import com.avirantEnterprises.InfoCollector.repository.forms.AssetRepository;
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
public class AssetService {

    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private AssetRepository assetRepository;

    public void registerAsset(String assetName, String assetType, LocalDate assetDate, String assetValue, MultipartFile assetFile) {
        Asset asset = new Asset();
        asset.setAssetName(assetName);
        asset.setAssetType(assetType);
        asset.setAssetDate(assetDate);
        asset.setAssetValue(assetValue);
        String filePath = saveFile(assetFile);
        asset.setAssetFilePath(filePath);
        assetRepository.save(asset);
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

    public Asset getAssetById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public void deleteAssetById(Long id) {
        assetRepository.deleteById(id);
    }


    public void updateAsset(Long assetId, String assetName, String assetType, LocalDate assetDate, String assetValue, MultipartFile assetFile) {
        Optional<Asset> optAsset = assetRepository.findById(assetId);
        if (optAsset.isPresent()) {
            Asset asset = optAsset.get();
            asset.setAssetName(assetName);
            asset.setAssetType(assetType);
            asset.setAssetDate(assetDate);
            asset.setAssetValue(assetValue);
            if (!assetFile.isEmpty()) {
                String filePath = saveFile(assetFile);
                asset.setAssetFilePath(filePath);
            }
            assetRepository.save(asset);
        }
    }
}
