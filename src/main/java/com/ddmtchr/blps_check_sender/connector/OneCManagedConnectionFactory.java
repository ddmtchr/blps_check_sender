package com.ddmtchr.blps_check_sender.connector;

import jakarta.resource.ResourceException;
import jakarta.resource.cci.Connection;
import jakarta.resource.cci.ConnectionFactory;
import jakarta.resource.spi.*;

import javax.security.auth.Subject;
import java.io.PrintWriter;
import java.util.Set;

@ConnectionDefinition(
        connectionFactory = ConnectionFactory.class,
        connectionFactoryImpl = OneCConnectionFactory.class,
        connection = Connection.class,
        connectionImpl = OneCConnection.class
)
public class OneCManagedConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation {

    private final String url;
    private PrintWriter logWriter;
    private ResourceAdapter ra;
    private boolean isResourceAdapterSet;

    public OneCManagedConnectionFactory(String url) {
        this.url = url;
        this.isResourceAdapterSet = false;
    }

    @Override
    public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
        return new OneCConnectionFactory(this);
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        return new OneCConnectionFactory(this);
    }

    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return new OneCManagedConnection(url);
    }

    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return new OneCManagedConnection(url);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.logWriter = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return this.logWriter;
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return this.ra;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        if (this.isResourceAdapterSet) {
            throw new ResourceException("ResourceAdapter is already set");
        }
        this.ra = ra;
        this.isResourceAdapterSet = true;
    }
}
