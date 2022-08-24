package com.leichu.ws.client.demo;

import com.alibaba.fastjson.JSONObject;
import com.leichu.ws.client.demo.config.WebServiceConfig;
import com.leichu.ws.server.demo.service.GetUserById;
import com.leichu.ws.server.demo.service.GetUserByIdResponse;
import com.leichu.ws.server.demo.service.User;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import java.lang.reflect.Method;

@SpringBootTest
class WsClientDemoApplicationTests {

	@Value("${ws.url}")
	private String wsUrl;


	@Resource
	private WebServiceConfig.SOAPConnector soapConnector;

	@Test
	void dynamicInvokeTest() throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsUrl);

		Object person = Thread.currentThread().getContextClassLoader().loadClass("com.leichu.ws.server.demo.service.User").newInstance();
		Class<?> personClass = person.getClass();
		Method m1 = personClass.getMethod("setId", Long.class);
		m1.invoke(person, 4L);
		Method setName = personClass.getMethod("setName", String.class);
		setName.invoke(person, "tttt");
		client.invoke("saveUser", person);


		// invoke("方法名",参数1,参数2,参数3....);
		//这里注意如果是复杂参数的话，要保证复杂参数可以序列化
		Object[] objects = client.invoke("getUserById", 3L);
		System.out.println("getUserById>>>返回数据:" + JSONObject.toJSONString(objects[0]));

		Object[] list = client.invoke("getUsers");
		System.out.println("返回数据:" + JSONObject.toJSONString(list[0]));
	}


	@Test
	void staticInvokeTest() {
		GetUserById getUserById = new GetUserById();
		getUserById.setUserId(1L);
		Object o = soapConnector.callWebService(wsUrl, getUserById);
		JAXBElement<GetUserByIdResponse> response = (JAXBElement<GetUserByIdResponse>) o;
		GetUserByIdResponse value = response.getValue();
		User user = value.getReturn();
		System.out.println(JSONObject.toJSONString(user));
	}

}
