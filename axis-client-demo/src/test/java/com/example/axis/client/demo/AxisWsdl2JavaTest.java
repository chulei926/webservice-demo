package com.example.axis.client.demo;//package com.ffcs.oss.sc.platform.srv;
//
import org.apache.axis.wsdl.WSDL2Java;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AxisWsdl2JavaTest {

	@Test
	void genServer() {
		WSDL2Java.main(new String[]{"-o", "src/main/java/com/example/axis/client/demo/ws", "--server-side", "src/test/resources/wsdl/SCService.wsdl"});
	}

	@Test
	void genClient() {
		WSDL2Java.main(new String[]{"-o", "src/main/java/com/example/axis/client/demo/ws", "-client", "src/test/resources/wsdl/SCService.wsdl"});

	}

}
