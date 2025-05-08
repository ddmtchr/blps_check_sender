package com.ddmtchr.blps_check_sender.connector;

import jakarta.resource.ResourceException;
import jakarta.resource.cci.*;

import javax.naming.NamingException;
import javax.naming.Reference;

public class OneCConnectionFactory implements ConnectionFactory {

    private final String url;

    public OneCConnectionFactory(String url) {
        this.url = url;
    }

    @Override
    public Connection getConnection() throws ResourceException {
        return new OneCConnection(url);
    }

    @Override
    public Connection getConnection(ConnectionSpec properties) throws ResourceException {
        return new OneCConnection(url);
    }

    private Connection getConnectionFromSpec(ConnectionSpec spec) throws ResourceException {
        if (spec instanceof OneCConnectionSpec) {
            return new OneCConnection(((OneCConnectionSpec) spec).getUrl());
        }
        throw new ResourceException("ConnectionSpec isn't instance of OneCConnectionSpec");
    }

    @Override
    public RecordFactory getRecordFactory() throws ResourceException {
        return null;
    }

    @Override
    public ResourceAdapterMetaData getMetaData() throws ResourceException {
        return null;
    }

    @Override
    public void setReference(Reference reference) {

    }

    @Override
    public Reference getReference() throws NamingException {
        return null;
    }
}
