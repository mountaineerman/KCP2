package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SwitchSP4TTest {

	@Test
	void testUpdatePosition() {
		
		SwitchSP2T tempSensorAB = new SwitchSP2T("testSP4TSensorAB", ModuleID.A);
		SwitchSP2T tempSensorCD = new SwitchSP2T("testSP4TSensorCD", ModuleID.A);
		SwitchSP4T testSP4TSwitch = new SwitchSP4T("testSP4TSwitch", ModuleID.A, tempSensorAB, tempSensorCD);
		tempSensorAB = null; tempSensorCD = null;
		
		// VALID
		testSP4TSwitch.setsensorABStatus(true);
		testSP4TSwitch.setsensorCDStatus(false);
		testSP4TSwitch.updatePosition();
		Assertions.assertTrue(testSP4TSwitch.getPosition() == SwitchSP4TPosition.ONE);
		
		testSP4TSwitch.setsensorABStatus(true);
		testSP4TSwitch.setsensorCDStatus(true);
		testSP4TSwitch.updatePosition();
		Assertions.assertTrue(testSP4TSwitch.getPosition() == SwitchSP4TPosition.TWO);
		
		testSP4TSwitch.setsensorABStatus(false);
		testSP4TSwitch.setsensorCDStatus(false);
		testSP4TSwitch.updatePosition();
		Assertions.assertTrue(testSP4TSwitch.getPosition() == SwitchSP4TPosition.THREE);
		
		testSP4TSwitch.setsensorABStatus(false);
		testSP4TSwitch.setsensorCDStatus(true);
		testSP4TSwitch.updatePosition();
		Assertions.assertTrue(testSP4TSwitch.getPosition() == SwitchSP4TPosition.FOUR);
	}

}
