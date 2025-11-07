package mountaineerman.kcp2.kkim.service;

import krpc.client.services.KRPC.GameScene;
import mountaineerman.kcp2.kkim.KKIMProp;

public final class StartupMode implements OperatingMode { //SINGLETON

	private static StartupMode INSTANCE;
    
	private StartupMode() {}
	
	public static StartupMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StartupMode();
        }
        return INSTANCE;
    }
	
	public void run(KKIMService kkimService) {
		
		if (KKIMProp.getkMegaIsOn()) {
			kkimService.serialCommunicator.establishSerialLinkToKMega();
		}
		if (KKIMProp.getkPhoIsOn()) {
			kkimService.kPhoCommunicator.establishBluetoothLinkToKPho();
			while (true) {//FIXME
				// try {
				// 	Thread.sleep(1000);
				// } catch (InterruptedException e) {e.printStackTrace();}
			}
		}
		kkimService.kRPCCommunicator.establishKRPCLink();        
		
		try {
			Thread.sleep(KKIMProp.getkkimStartupModeInitialStartupDelayInMilliseconds());
		} catch (InterruptedException e) {e.printStackTrace();}

		if (kkimService.kRPCCommunicator.fetchCurrentGameSceneInKSP() == GameScene.FLIGHT) {
			kkimService.kRPCCommunicator.establishKRPCFlightHooks();
			kkimService.controlPanel.moduleH.glassCL_LED.setPWM(KKIMProp.getkmegaDimPWM());//KMega Diagnostic Mode
			kkimService.controlPanel.moduleH.glassCR_LED.setPWM(KKIMProp.getkmegaDimPWM());//KKIM Diagnostic Mode
			kkimService.controlPanel.moduleH.glassBR_LED.setPWM(KKIMProp.getkmegaDimPWM());//Graceful Shutdown
			kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
		} else { // (SPACE_CENTER, TRACKING_STATION, EDITOR_VAB, or EDITOR_SPH)
            kkimService.setCurrentOperatingMode(IdleMode.getInstance());
        }
    }
}