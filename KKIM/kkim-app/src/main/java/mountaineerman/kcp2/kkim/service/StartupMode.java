package mountaineerman.kcp2.kkim.service;

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
	
	public void run(KKIMService kkimService) 
    {
        //System.out.println("Startup Mode");
        
		kkimService.serialCommunicator.establishSerialLink();
		kkimService.kRPCCommunicator.establishKRPCLink();        
		//TODO Establish connection to phone
		
		try {Thread.sleep(KKIMProp.getkkimInitialStartupDelayInMilliseconds());} catch (InterruptedException e) {e.printStackTrace();}
		
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
    }
}