package com.leichu.ws.client.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Configuration
public class WebServiceConfig {

	@Value("${ws.url}")
	private String wsUrl;

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this is the package name specified in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.leichu.ws.server.demo.service");
		return marshaller;
	}

	@Bean
	public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
		SOAPConnector client = new SOAPConnector();
		client.setDefaultUri(wsUrl);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

	public static class SOAPConnector extends WebServiceGatewaySupport {

		public Object callWebService(String url, Object request){
			return getWebServiceTemplate().marshalSendAndReceive(url, request);
		}
	}

}
