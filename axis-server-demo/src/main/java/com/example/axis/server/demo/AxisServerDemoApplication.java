package com.example.axis.server.demo;

import org.apache.axis.transport.http.AxisServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AxisServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AxisServerDemoApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<AxisServlet> axisServlet() {
		AxisServlet servlet = new AxisServlet();
		return new ServletRegistrationBean<>(servlet, "/services/*");
	}

}
