package mountaineerman.kcp2.kkim.service;

public final class ShutdownMode implements OperatingMode { //SINGLETON

	private static ShutdownMode INSTANCE;
    
	private ShutdownMode() {}
	
	public static ShutdownMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ShutdownMode();
        }
        return INSTANCE;
    }
	
	public void run(KKIMService kkimService) {
		kkimService.serialCommunicator.teardownSerialLinkToKMega();
		kkimService.kRPCCommunicator.closeKRPCLink(); //kRPC Note: "All of a clients streams are automatically stopped when it disconnects."
		System.exit(0);
    }
}