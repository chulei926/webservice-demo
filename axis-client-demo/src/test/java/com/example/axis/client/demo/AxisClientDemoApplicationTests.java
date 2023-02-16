package com.example.axis.client.demo;

import com.example.axis.client.demo.ws.SCService;
import com.example.axis.client.demo.ws.SCServiceServiceLocator;
import org.apache.axis.AxisProperties;
import org.apache.axis.components.net.SunFakeTrustSocketFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class AxisClientDemoApplicationTests {

	static {
		// Axis https请求跳过证书认证
		AxisProperties.setProperty("axis.socketSecureFactory", SunFakeTrustSocketFactory.class.getName());
		System.setProperty("javax.net.debug", "true");
	}

	@Test
	void contextLoads() throws Exception {
//		java.net.URL endpoint = new java.net.URL("https://127.0.0.1:8080/services/SCService");
		SCServiceServiceLocator locator = new SCServiceServiceLocator();
		locator.setSCServiceEndpointAddress("https://127.0.0.1:8080/services/SCService");
		SCService scService = locator.getSCService();
		UUID uuid = UUID.randomUUID();
		String invoke = scService.invoke(uuid.toString());
		System.out.println(uuid + " >>> " + invoke);

//		ExecutorService executorService = Executors.newFixedThreadPool(10);
//		CountDownLatch countDownLatch = new CountDownLatch(10);
//		for (int i = 0; i < 10; i++) {
//			executorService.execute(()->{
//				try {
//					SCServiceServiceLocator locator = new SCServiceServiceLocator();
//					locator.setSCServiceEndpointAddress("https://127.0.0.1:8080/services/SCService");
//					SCService scService = locator.getSCService();
//					UUID uuid = UUID.randomUUID();
//					String invoke = scService.invoke(uuid.toString());
//					System.out.println(uuid + " >>> " + invoke);
//				} catch (Exception e){
//					e.printStackTrace();
//				} finally {
//					countDownLatch.countDown();
//				}
//			});
//		}
//
//		countDownLatch.await();
//		executorService.shutdown();
//		executorService.shutdownNow();
	}

	private void invokes(){

	}

}
