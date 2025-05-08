//package com.ddmtchr.blps_check_sender.service;
//
//import com.ddmtchr.blps_check_sender.connector.OneCConnection;
//import com.ddmtchr.blps_check_sender.connector.record.OneCCheckInputRecord;
//import com.ddmtchr.blps_check_sender.connector.record.OneCCheckOutputRecord;
//import com.ddmtchr.blps_check_sender.dto.CheckDto;
//import jakarta.mail.MessagingException;
//import jakarta.resource.ResourceException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class OneCService {
//
//    private final OneCConnection connection;
//    private final PdfEmailService pdfEmailService;
//
//    public void processCheck(CheckDto checkDto) {
//        try {
//            OneCCheckOutputRecord result = (OneCCheckOutputRecord) connection.createInteraction().execute(null, new OneCCheckInputRecord(checkDto));
//
//            if (result == null) {
//                throw new ResourceException("Error during 1C interaction");
//            }
//
//            pdfEmailService.sendPdf(checkDto, result);
//        } catch (ResourceException | MessagingException e) {
//            log.error(e.getMessage());
//        }
//    }
//}
