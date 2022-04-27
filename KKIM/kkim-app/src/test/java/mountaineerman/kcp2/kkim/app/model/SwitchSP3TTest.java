package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mountaineerman.kcp2.kkim.model.ModuleID;
import mountaineerman.kcp2.kkim.model.SwitchSP2T;
import mountaineerman.kcp2.kkim.model.SwitchSP3T;
import mountaineerman.kcp2.kkim.model.SP3TPosition;

class SwitchSP3TTest {

	@Test
	void testUpdatePosition() {
		
		SwitchSP2T tempTopSwitch = new SwitchSP2T("testSP3T_topSwitch", ModuleID.A);
		SwitchSP2T tempBottomSwitch = new SwitchSP2T("testSP3T_bottomSwitch", ModuleID.A);
		SwitchSP3T testSP3TSwitch = new SwitchSP3T("testSP3TSwitch", ModuleID.A, tempTopSwitch, tempBottomSwitch);
		tempTopSwitch = null; tempBottomSwitch = null;
		
		// VALID
		testSP3TSwitch.setTopSensorStatus(true);
		testSP3TSwitch.setBottomSensorStatus(false);
		testSP3TSwitch.updatePosition();
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SP3TPosition.TOP);
		
		testSP3TSwitch.setTopSensorStatus(false);
		testSP3TSwitch.setBottomSensorStatus(false);
		testSP3TSwitch.updatePosition();
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SP3TPosition.CENTER);
		
		testSP3TSwitch.setTopSensorStatus(false);
		testSP3TSwitch.setBottomSensorStatus(true);
		testSP3TSwitch.updatePosition();
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SP3TPosition.BOTTOM);
		
		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testSP3TSwitch.setTopSensorStatus(true);
			testSP3TSwitch.setBottomSensorStatus(true);
			testSP3TSwitch.updatePosition();
		});
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SP3TPosition.INVALID);
	}

}
