/**
 * SCServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.axis.client.demo.ws;

public class SCServiceServiceLocator extends org.apache.axis.client.Service implements SCServiceService {

    public SCServiceServiceLocator() {
    }


    public SCServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SCServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SCService
    private java.lang.String SCService_address = "http://127.0.0.1:8080/services/SCService";

    public java.lang.String getSCServiceAddress() {
        return SCService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SCServiceWSDDServiceName = "SCService";

    public java.lang.String getSCServiceWSDDServiceName() {
        return SCServiceWSDDServiceName;
    }

    public void setSCServiceWSDDServiceName(java.lang.String name) {
        SCServiceWSDDServiceName = name;
    }

    public SCService getSCService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SCService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSCService(endpoint);
    }

    public SCService getSCService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SCServiceSoapBindingStub _stub = new SCServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getSCServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSCServiceEndpointAddress(java.lang.String address) {
        SCService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SCService.class.isAssignableFrom(serviceEndpointInterface)) {
                SCServiceSoapBindingStub _stub = new SCServiceSoapBindingStub(new java.net.URL(SCService_address), this);
                _stub.setPortName(getSCServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SCService".equals(inputPortName)) {
            return getSCService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://SCService.xxx.com", "SCServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://SCService.xxx.com", "SCService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SCService".equals(portName)) {
            setSCServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
