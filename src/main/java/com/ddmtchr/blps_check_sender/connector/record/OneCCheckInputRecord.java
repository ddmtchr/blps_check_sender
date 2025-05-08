package com.ddmtchr.blps_check_sender.connector.record;

import com.ddmtchr.blps_check_sender.dto.CheckDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OneCCheckInputRecord extends OneCRecord {

    public OneCCheckInputRecord(CheckDto checkDto) {
        this.guest = checkDto.getGuest();
        this.estate = checkDto.getEstate();
        this.amount = checkDto.getAmount();
        this.startDate = checkDto.getStartDate();
        this.endDate = checkDto.getEndDate();
    }

    private String guest;
    private String estate;
    private Long amount;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;
}
