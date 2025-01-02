package mountaineerman.kcp2.kkim.service;

import krpc.client.services.KRPC.GameScene;
import mountaineerman.kcp2.kkim.KKIMProp;

public final class IdleMode implements OperatingMode { //SINGLETON

    private static IdleMode INSTANCE;
    
	private IdleMode() {}
	
	public static IdleMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new IdleMode();
        }
        return INSTANCE;
    }

    public void run(KKIMService kkimService) {

        System.out.println("In IdleMode. Sleeping for " + KKIMProp.getkkimIdleModeSleepIntervalInMilliseconds() + " milliseconds.");
        try {
            Thread.sleep(KKIMProp.getkkimIdleModeSleepIntervalInMilliseconds());
        } catch (InterruptedException e) {e.printStackTrace();}
        
        GameScene scene = kkimService.kRPCCommunicator.fetchCurrentGameSceneInKSP();
        System.out.println("GameScene: " + scene);
		if (scene == GameScene.FLIGHT) {
			kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
		} else { // (SPACE_CENTER, TRACKING_STATION, EDITOR_VAB, or EDITOR_SPH)
            kkimService.setCurrentOperatingMode(IdleMode.getInstance());
        }
    }
}