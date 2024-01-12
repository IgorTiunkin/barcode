package com.example.qr.controllers;

import com.example.qr.dto.CodeConvertRequest;
import com.example.qr.services.QRCodeCreator;
import com.google.zxing.BarcodeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/qr")
@RequiredArgsConstructor
public class QRController {

    private final List<BarcodeFormat> barcodeFormats = List.of(BarcodeFormat.QR_CODE,
            BarcodeFormat.CODE_128, BarcodeFormat.AZTEC, BarcodeFormat.PDF_417);


    private final QRCodeCreator qrCodeCreator;

    @GetMapping("/create")
    public String blank(Model model) {
        model.addAttribute("barcodes", barcodeFormats);
        model.addAttribute("request", new CodeConvertRequest());
        return "/CodeChoose";
    }

    @PostMapping("/create")
    public String create (Model model, @ModelAttribute ("request") CodeConvertRequest codeConvertRequest) {
        BarcodeFormat barcodeFormat = codeConvertRequest.getBarcodeFormat();
        String text = codeConvertRequest.getText();
        byte[] qrCodeImage = null;
        if (qrCodeCreator.is2DBarcodeType(barcodeFormat)) {
            qrCodeImage = qrCodeCreator.createQRCode(text, barcodeFormat, 300, 300);
        } else  {
            qrCodeImage = qrCodeCreator.createQRCode(text, barcodeFormat, 50, 300);
        }
        String encodedImage = Base64.getEncoder().encodeToString(qrCodeImage);
        model.addAttribute("encodedImage", encodedImage);
        return "/Codeview";
    }

}
