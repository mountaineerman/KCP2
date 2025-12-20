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
		kkimService.serialCommunicator.establishSerialLinkToKMega();
		kkimService.kRPCCommunicator.establishKRPCLink();
		//TODO Establish connection to phone
		
		try {
			Thread.sleep(KKIMProp.kkimStartupModeInitialStartupDelayInMilliseconds);
		} catch (InterruptedException e) {e.printStackTrace();}

		if (kkimService.kRPCCommunicator.fetchCurrentGameSceneInKSP() == GameScene.FLIGHT) {
			kkimService.kRPCCommunicator.establishKRPCFlightHooks();
			kkimService.kRPCCommunicator.establishAllKRPCStreams();
			kkimService.controlPanel.moduleH.glassCL_LED.setPWM(KKIMProp.kmegaLEDDimPWM);//KMega Diagnostic Mode
			kkimService.controlPanel.moduleH.glassCR_LED.setPWM(KKIMProp.kmegaLEDDimPWM);//KKIM Diagnostic Mode
			kkimService.controlPanel.moduleH.glassBR_LED.setPWM(KKIMProp.kmegaLEDDimPWM);//Graceful Shutdown
			kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
		} else { // (SPACE_CENTER, TRACKING_STATION, EDITOR_VAB, or EDITOR_SPH)
            kkimService.setCurrentOperatingMode(IdleMode.getInstance());
        }
    }
}