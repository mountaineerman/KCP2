package mountaineerman.kcp2.kkim.kmega;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KMegaApiTest {

	@Test public void testGetHelloWorld() {
		assertEquals("Hello World from KMegaApi", KMegaApi.getHelloWorld());
	}

}
