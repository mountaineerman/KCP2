package mountaineerman.kcp2.kkim.app.model;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SwitchSP3TTest {

	@Test
	void testUpdatePosition() {
		
		SwitchSP2T topSwitch = new SwitchSP2T("testSP3T_topSwitch", ModuleID.A);
		SwitchSP2T bottomSwitch = new SwitchSP2T("testSP3T_bottomSwitch", ModuleID.A);
		SwitchSP3T testSP3TSwitch = new SwitchSP3T("testSP3TSwitch", ModuleID.A, topSwitch, bottomSwitch);
		
		// VALID
		testSP3TSwitch.setTopSwitchStatus(true);
		testSP3TSwitch.setBottomSwitchStatus(false);
		testSP3TSwitch.updatePosition();
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SwitchSP3TPosition.TOP);
		
		testSP3TSwitch.setTopSwitchStatus(false);
		testSP3TSwitch.setBottomSwitchStatus(false);
		testSP3TSwitch.updatePosition();
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SwitchSP3TPosition.CENTER);
		
		testSP3TSwitch.setTopSwitchStatus(false);
		testSP3TSwitch.setBottomSwitchStatus(true);
		testSP3TSwitch.updatePosition();
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SwitchSP3TPosition.BOTTOM);
		
		// INVALID
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testSP3TSwitch.setTopSwitchStatus(true);
			testSP3TSwitch.setBottomSwitchStatus(true);
			testSP3TSwitch.updatePosition();
		});
		Assertions.assertTrue(testSP3TSwitch.getPosition() == SwitchSP3TPosition.INVALID);
	}

}
