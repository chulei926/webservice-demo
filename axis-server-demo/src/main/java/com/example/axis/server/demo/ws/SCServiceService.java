/**
 * SCServiceService.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.axis.server.demo.ws;

public interface SCServiceService extends javax.xml.rpc.Service {
	public java.lang.String getSCServiceAddress();

	public SCService getSCService() throws javax.xml.rpc.ServiceException;

	public SCService getSCService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
