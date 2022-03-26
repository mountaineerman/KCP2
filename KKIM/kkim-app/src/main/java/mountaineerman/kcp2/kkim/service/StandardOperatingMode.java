package mountaineerman.kcp2.kkim.service;

import java.util.Arrays;

public final class StandardOperatingMode implements OperatingMode { //SINGLETON

	private static StandardOperatingMode INSTANCE;
    
	private StandardOperatingMode() {}
	
	public static StandardOperatingMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StandardOperatingMode(); 
        }
        
        return INSTANCE;
    }
	
	public void run(KKIMService kkimService) 
    {
        //System.out.println("Standard Operating Mode");
		
		kkimService.serialCommunicator.ingestDataFromSerialPortToInputRefreshPacketBuffer();
		if (kkimService.serialCommunicator.getisValidPacketInInputRefreshPacketBuffer()) {
			Arrays.toString(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
			//this->packetUnpacker.unpackOutputRefreshPacketIntoModel();
		}
		kkimService.serialCommunicator.printCommunicationsDiagnosticInformation();
	//		//Refresh model based on kRPC
	//		
	//		//If it's time to send an OutputRefreshPacket
	//			//Assemble OutputRefreshPacket
	//			//Send OutputRefreshPacket
	//		
	//		//Read data from serial buffer
	//		//If an InputRefreshPacket has arrived
	//			//Unpack InputRefreshPacket into model
	//			//Refresh model state?
	//			//Communicate to kRPC?		

		
        kkimService.idleIfNecessary();
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance()); //TODO add logic for entering Diagnostic and Shutdown modes
    }
}