package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.utils.QRCodeGenerator;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Hidden
@RequestMapping("api/qrcode")
public class QRCodeController {

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(
            @RequestParam(name = "data", required = true) String data,
            @RequestParam(name = "width", required = false, defaultValue = "250") int width,
            @RequestParam(name = "height", required = false, defaultValue = "250") int height) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.getQRCodeImage(data, width, height));
    }

}
