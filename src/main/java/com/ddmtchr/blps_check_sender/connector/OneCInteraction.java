package com.ddmtchr.blps_check_sender.connector;

import com.ddmtchr.blps_check_sender.connector.record.OneCCheckIdRecord;
import com.ddmtchr.blps_check_sender.connector.record.OneCCheckInputRecord;
import com.ddmtchr.blps_check_sender.connector.record.OneCCheckOutputRecord;
import jakarta.resource.ResourceException;
import jakarta.resource.cci.Record;
import jakarta.resource.cci.*;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class OneCInteraction implements Interaction {

    private final Connection connection;
    private final RestClient restClient;

    public OneCInteraction(Connection connection, RestClient restClient) {
        this.connection = connection;
        this.restClient = restClient;
    }

    @Override
    public void close() throws ResourceException {
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public boolean execute(InteractionSpec ispec, Record input, Record output) throws ResourceException {
        if (!(input instanceof OneCCheckInputRecord)) {
            return false;
        }

        OneCCheckIdRecord checkIdResponse = restClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(input)
                .retrieve()
                .body(OneCCheckIdRecord.class);

        if (checkIdResponse == null) {
            return false;
        }

        byte[] pdfResponse = restClient
                .get()
                .uri("/" + checkIdResponse.getId())
                .retrieve()
                .body(byte[].class);

        output = new OneCCheckOutputRecord(pdfResponse);
        return true;
    }

    @Override
    public Record execute(InteractionSpec ispec, Record input) throws ResourceException {
        if (!(input instanceof OneCCheckInputRecord)) {
            return null;
        }

        OneCCheckIdRecord checkIdResponse = restClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(input)
                .retrieve()
                .body(OneCCheckIdRecord.class);

        if (checkIdResponse == null) {
            return null;
        }

        byte[] pdfResponse = restClient
                .get()
                .uri("/" + checkIdResponse.getId())
                .retrieve()
                .body(byte[].class);

        return new OneCCheckOutputRecord(pdfResponse);
    }

    @Override
    public ResourceWarning getWarnings() throws ResourceException {
        return null;
    }

    @Override
    public void clearWarnings() throws ResourceException {
    }
}
