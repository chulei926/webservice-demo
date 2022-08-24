package com.leichu.ws.server.demo.config;

import com.leichu.ws.server.demo.service.UserService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

	@Resource
	private UserService userService;

	@Resource
	private Bus bus;

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, userService);
		endpoint.publish("/UserService"); // http://localhost:8080/services/UserService?wsdl
		return endpoint;
	}


}
