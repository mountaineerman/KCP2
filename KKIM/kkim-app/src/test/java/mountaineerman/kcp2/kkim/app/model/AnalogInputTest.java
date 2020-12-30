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
		/*import java.util.Scanner;
		AnalogInput experimentalAnalogInput = new AnalogInput("Test1", 500, 1023);
		Scanner scanner = new Scanner(System.in);
		
		while(true)	{
			String message = scanner.nextLine();
			experimentalAnalogInput.setCalibratedValue(Integer.parseInt(message));
			System.out.println(experimentalAnalogInput.getCalibratedValue());
		}*/
	}

}
