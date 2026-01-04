package mountaineerman.kcp2.kkim.service;

import mountaineerman.kcp2.kkim.KKIMProp;

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
		if (KKIMProp.kMegaIsActive) {kkimService.serialCommunicator.teardownSerialLinkToKMega();}
		if (KKIMProp.kPhoIsActive) {kkimService.kPhoCommunicator.teardownBluetoothLinkToKPho();}
		kkimService.kRPCCommunicator.closeKRPCLink(); //kRPC Note: "All of a client's streams are automatically stopped when it disconnects."
		System.exit(0);
    }
}