package com.ddmtchr.blps_check_sender.connector;

import jakarta.resource.ResourceException;
import jakarta.resource.cci.*;
import jakarta.resource.spi.ManagedConnectionFactory;

import javax.naming.NamingException;
import javax.naming.Reference;

public class OneCConnectionFactory implements ConnectionFactory {

    private final ManagedConnectionFactory managedConnectionFactory;

    public OneCConnectionFactory(ManagedConnectionFactory managedConnectionFactory) {
        this.managedConnectionFactory = managedConnectionFactory;
    }

    @Override
    public Connection getConnection() throws ResourceException {
        return (OneCConnection) this.managedConnectionFactory.createManagedConnection(null, null).getConnection(null, null);
    }

    @Override
    public Connection getConnection(ConnectionSpec properties) throws ResourceException {
        return getConnectionFromSpec(properties);
    }

    private Connection getConnectionFromSpec(ConnectionSpec spec) throws ResourceException {
        if (spec instanceof OneCConnectionSpec oneCConnectionSpec) {
            return new OneCConnection(oneCConnectionSpec.getUrl());
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
