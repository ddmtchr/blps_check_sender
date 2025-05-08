package com.ddmtchr.blps_check_sender.connector.record;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OneCCheckOutputRecord extends OneCRecord {
    private byte[] pdfBytes;
}
