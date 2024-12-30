package mountaineerman.kcp2.kkim.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.OP;

class SwitchSP3TTest {

	@Test
	void testUpdatePosition() {
		
		SwitchSP3T testSP3TSwitch = new SwitchSP3T(IP.SP3T_SpeedMode_Switch, IP.SP3T_SpeedMode_SFC_Switch, IP.SP3T_SpeedMode_TGT_Switch, OP.SP3T_SpeedMode_ORB_LED);
		
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
