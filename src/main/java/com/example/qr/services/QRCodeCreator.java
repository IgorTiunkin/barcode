package com.example.qr.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

@Service
public class QRCodeCreator {

    private Set <BarcodeFormat> barcodeFormatSet2d = Set.of(BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC);

    public byte[] createQRCode(String data, BarcodeFormat barcodeFormat, int height, int width) {

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    data, barcodeFormat, width, height);
            return getBytes(matrix);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] getBytes(BitMatrix matrix) throws IOException {
        // Convert BitMatrix to BufferedImage
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

        // Convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }

    public boolean is2DBarcodeType(BarcodeFormat barcodeFormat) {
        return barcodeFormatSet2d.contains(barcodeFormat);
    }


}
