package mountaineerman.kcp2.kkim.service;

import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.KKIMProperties;

public final class StandardOperatingMode implements OperatingMode { //SINGLETON

	private static StandardOperatingMode INSTANCE;
	private long serialPortLastReadTimeInMilliseconds = 0;
	private long outPutRefreshPacketLastSentTimeInMilliseconds = 0;
	
	private StandardOperatingMode() {}
	
	public static StandardOperatingMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StandardOperatingMode(); 
        }
        
        return INSTANCE;
    }
	
	public void run(KKIMService kkimService) 
    {
		
		//TODO Confirm connection to kRPC, KMega, and KPhone
		
		if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProperties.getkMegaInputRefreshPacketReadRateInMilliseconds() ) {
			CommonUtilities.clearScreen();
			kkimService.serialCommunicator.ingestDataFromSerialPortToInputRefreshPacketBuffer();
			if (kkimService.serialCommunicator.getisValidPacketInInputRefreshPacketBuffer()) {
				//kkimService.packetUnpacker.displayPacketInDecimal(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
				kkimService.packetUnpacker.unpackInputRefreshPacketIntoModel(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
				kkimService.serialCommunicator.clearInputRefreshPacketBuffer();
			}
			kkimService.serialCommunicator.printCommunicationsDiagnosticInformation();
			System.out.println();
			kkimService.controlPanel.displayInputStatus();
			this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();

			//Refresh model based on kRPC
			
			//kkimService.controlPanel.displayInputStatus();
			//kkimService.controlPanel.refresh();
		}
		
		if ( (System.currentTimeMillis() - this.outPutRefreshPacketLastSentTimeInMilliseconds) > KKIMProperties.getkMegaOutputRefreshPacketSendRateInMilliseconds()) {
			//Assemble OutputRefreshPacket
			//Send OutputRefreshPacket
			this.outPutRefreshPacketLastSentTimeInMilliseconds = System.currentTimeMillis();
		}
		
		//Send info to kRPC?
		
		//TODO Send packet to phone
		
        kkimService.idleIfNecessary();
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance()); //TODO add logic for entering Diagnostic and Shutdown modes
    }
}