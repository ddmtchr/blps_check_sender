package com.ddmtchr.blps_check_sender.service;

import com.ddmtchr.blps_check_sender.connector.record.OneCCheckOutputRecord;
import com.ddmtchr.blps_check_sender.dto.CheckDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PdfEmailService {

    private final JavaMailSender mailSender;

    public void sendPdf(CheckDto checkDto, OneCCheckOutputRecord oneCCheck) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@blps.se.ifmo.ru");
        helper.setTo(checkDto.getEmail());
        helper.setSubject("Чек по аренде жилья");

        helper.addAttachment("receipt_" + oneCCheck.getId() + ".pdf", new ByteArrayResource(oneCCheck.getPdfBytes()));

        mailSender.send(message);
    }
}
