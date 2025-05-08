package com.ddmtchr.blps_check_sender;

import com.ddmtchr.blps_check_sender.dto.CheckDto;
import com.ddmtchr.blps_check_sender.service.PdfEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestApi {
    private final PdfEmailService pdfEmailService;

    @Autowired
    public TestApi(PdfEmailService pdfEmailService) {
        this.pdfEmailService = pdfEmailService;
    }

    @GetMapping("/test")
    public ResponseEntity<Void> test() throws MessagingException, IOException {
        pdfEmailService.sendPdf(
                new CheckDto(null, null, null, "rubinho-m@yandex.ru", null, null),
                null
        );
        return ResponseEntity.ok().build();
    }
}
