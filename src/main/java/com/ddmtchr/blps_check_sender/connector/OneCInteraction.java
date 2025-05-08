package com.ddmtchr.blps_check_sender.connector;

import com.ddmtchr.blps_check_sender.connector.record.OneCCheckIdRecord;
import com.ddmtchr.blps_check_sender.connector.record.OneCCheckInputRecord;
import com.ddmtchr.blps_check_sender.connector.record.OneCCheckOutputRecord;
import com.ddmtchr.blps_check_sender.exception.OneCInteractionException;
import jakarta.resource.ResourceException;
import jakarta.resource.cci.Record;
import jakarta.resource.cci.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Slf4j
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

        output = new OneCCheckOutputRecord(checkIdResponse.getId(), pdfResponse);
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
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    log.error("Error {} during POST to 1C: {}", response.getStatusCode(), response.getStatusText());
                    throw new OneCInteractionException("Error during POST to 1C");
                }))
                .body(OneCCheckIdRecord.class);

        if (checkIdResponse == null) {
            return null;
        }

        log.info("Created check #{} in 1C", checkIdResponse.getId());

        byte[] pdfResponse = restClient
                .get()
                .uri("?id={param}", checkIdResponse.getId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    log.error("Error {} during GET to 1C: {}", response.getStatusCode(), response.getStatusText());
                    throw new OneCInteractionException("Error during GET to 1C");
                }))
                .body(byte[].class);

        log.info("Retrieved check #{} from 1C", checkIdResponse.getId());

        return new OneCCheckOutputRecord(checkIdResponse.getId(), pdfResponse);
    }

    @Override
    public ResourceWarning getWarnings() throws ResourceException {
        return null;
    }

    @Override
    public void clearWarnings() throws ResourceException {
    }
}
