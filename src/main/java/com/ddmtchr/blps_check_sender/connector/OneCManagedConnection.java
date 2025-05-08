package com.ddmtchr.blps_check_sender.connector;

import jakarta.resource.ResourceException;
import jakarta.resource.spi.*;

import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;
import java.io.PrintWriter;

public class OneCManagedConnection implements ManagedConnection {

    private final String url;

    public OneCManagedConnection(String url) {
        this.url = url;
    }

    @Override
    public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return new OneCConnection(url);
    }

    @Override
    public void destroy() throws ResourceException {
    }

    @Override
    public void cleanup() throws ResourceException {
    }

    @Override
    public void associateConnection(Object connection) throws ResourceException {
    }

    @Override
    public void addConnectionEventListener(ConnectionEventListener listener) {
    }

    @Override
    public void removeConnectionEventListener(ConnectionEventListener listener) {
    }

    @Override
    public XAResource getXAResource() throws ResourceException {
        return null;
    }

    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        return null;
    }

    @Override
    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return null;
    }
}
