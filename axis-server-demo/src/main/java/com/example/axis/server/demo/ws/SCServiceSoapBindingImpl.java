/**
 * SCServiceSoapBindingImpl.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.example.axis.server.demo.ws;

public class SCServiceSoapBindingImpl implements SCService {
	public java.lang.String invoke(java.lang.String xmlData) throws java.rmi.RemoteException {
		System.out.println(">>>>>>>>>>  收到请求：" + xmlData);
		return "hello: " + xmlData;
	}

}
