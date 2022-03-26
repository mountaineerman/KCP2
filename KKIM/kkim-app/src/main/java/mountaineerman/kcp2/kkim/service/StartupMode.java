package mountaineerman.kcp2.kkim.service;

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
		
		//System.out.print("Establishing connection to kRPC... ");
		//TODO
		//System.out.println("DONE");
        
        kkimService.idleIfNecessary();
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
    }
}