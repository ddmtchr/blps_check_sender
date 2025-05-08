package com.ddmtchr.blps_check_sender.connector;

import jakarta.resource.ResourceException;
import jakarta.resource.cci.*;
import org.springframework.web.client.RestClient;

public class OneCConnection implements Connection {

    private final RestClient restClient;

    public OneCConnection(String url) {
        this.restClient = RestClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public Interaction createInteraction() throws ResourceException {
        return new OneCInteraction(this, restClient);
    }

    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        return null;
    }

    @Override
    public ConnectionMetaData getMetaData() throws ResourceException {
        return null;
    }

    @Override
    public ResultSetInfo getResultSetInfo() throws ResourceException {
        return null;
    }

    @Override
    public void close() throws ResourceException {
    }
}
