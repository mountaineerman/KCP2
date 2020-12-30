package mountaineerman.kcp2.kkim.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AnalogInputTest {

	@Test
	void testAnalogInput() {
		new AnalogInput("FakePotentiometer", 0, 1023);

		new AnalogInput("FakePotentiometer", -1, 1023);
		new AnalogInput("FakePotentiometer", -1, 1024);
		new AnalogInput("FakePotentiometer", -1, 1024);
	}

}
