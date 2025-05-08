package com.ddmtchr.blps_check_sender.connector;

import jakarta.resource.ResourceException;
import jakarta.resource.spi.*;
import jakarta.resource.spi.endpoint.MessageEndpointFactory;

import javax.transaction.xa.XAResource;

@Connector(
        description = "Connector to send HTTP requests and receive HTTP responses using 1C",
        displayName = "1C HTTP Connector",
        vendorName = "se.ifmo",
        eisType = "1C ERP"
)
public class OneCResourceAdapter implements ResourceAdapter {

    @Override
    public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
    }

    @Override
    public void stop() {
    }

    @Override
    public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
    }

    @Override
    public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException {
        return new XAResource[0];
    }
}
