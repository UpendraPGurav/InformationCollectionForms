package com.avirantEnterprises.InfoCollector.controller.forms;


import com.avirantEnterprises.InfoCollector.model.forms.Asset;
import com.avirantEnterprises.InfoCollector.service.forms.AssetService;
import com.avirantEnterprises.InfoCollector.service.forms.PdfAssetService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private PdfAssetService pdfAssetService;


    //Show Register form
    @GetMapping("/asset")
    public String showAssetForm(){
        return "forms/assetRegistrationForm";
    }

    //submit fomr
    @PostMapping("/registerAsset")
    public String registerAsset(@RequestParam("assetName") String assetName,
                           @RequestParam("assetType") String assetType, @RequestParam("assetDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate assetDate,
                           @RequestParam("assetValue") String assetValue,
                           @RequestParam("assetFile") MultipartFile assetFile) {
        assetService.registerAsset(assetName,assetType,assetDate,assetValue,assetFile);
        return "forms/success";
    }

    //get view by id
    @GetMapping("/asset/{id}")
    public String viewAsset(@PathVariable("id") Long id, Model model) {
        Asset asset = assetService.getAssetById(id);
        System.out.println("Profile Picture Path: " + asset.getAssetFilePath());
        model.addAttribute("asset", asset);
        return "forms/assetView";
    }


    //get all list
    @GetMapping("/assetList")
    public String listUserProfiles(Model model) {
        List<Asset> assets = assetService.getAllAssets();
        model.addAttribute("assets", assets);
        return "forms/assetList";
    }


    @GetMapping("/asset/update/{id}")
    public String viewToUpdateAsset(@PathVariable("id") Long id, Model model) {
        Asset asset = assetService.getAssetById(id);
//        System.out.println("Profile Picture Path: " + asset.getAssetFilePath());
        model.addAttribute("asset", asset);
        return "forms/updateAsset";
    }

    @PostMapping("/updateAsset")
    public String updateAsset(@RequestParam("assetId") Long assetId,
                                @RequestParam("assetName") String assetName,
                                @RequestParam("assetType") String assetType, @RequestParam("assetDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate assetDate,
                                @RequestParam("assetValue") String assetValue,
                                @RequestParam("assetFile") MultipartFile assetFile) {
        assetService.updateAsset(assetId,assetName,assetType,assetDate,assetValue,assetFile);
        return "redirect:/asset/" + assetId;
    }


    //Download Pdf
    @GetMapping("/asset/download/{id}")
    public ResponseEntity<byte[]> downloadUserProfilePdf(@PathVariable Long id) throws IOException, DocumentException {
        byte[] pdfContent = pdfAssetService.generateUserProfilePdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "user_profile_" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }

}
