package mountaineerman.kcp2.kkim.app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AnalogInputTest {

	@Test // TODO Replace with @ParameterizedTest?
	void testAnalogInput() {
		//=================================================================================================================
		// Constructor
		//=================================================================================================================
		// VALID
		new AnalogInput("FakePotentiometer", 0, 1023); // Maximum possible valid range

		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new AnalogInput("FakePotentiometer", -1, 1023); // Lower Limit too low
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new AnalogInput("FakePotentiometer", 0, 1024); // Upper Limit too high
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new AnalogInput("FakePotentiometer", -1, 1024); // Lower Limit too low, Upper Limit too high
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new AnalogInput("FakePotentiometer", 1000, 10); // Limits reversed
		});
		
		//=================================================================================================================
		// setCalibratedValue
		//=================================================================================================================
		AnalogInput
	}

}
