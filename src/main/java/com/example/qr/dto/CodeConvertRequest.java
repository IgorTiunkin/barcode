package com.example.qr.dto;

import com.google.zxing.BarcodeFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CodeConvertRequest {

    private String text;
    private BarcodeFormat barcodeFormat;
}
