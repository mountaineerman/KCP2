package mountaineerman.kcp2.kkim.service;

import krpc.client.RPCException;
import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.KKIMProp;

public final class StandardOperatingMode implements OperatingMode { //SINGLETON

	private static StandardOperatingMode INSTANCE;
	private long serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
	private long outputRefreshPacketLastSentTimeInMilliseconds = System.currentTimeMillis();
	
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
		//TODO Confirm connection to kRPC, KMega, and KPhone
		
		//Pull Information
		if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProp.getkMegaInputRefreshPacketReadRateInMilliseconds() ) {
			//CommonUtilities.clearScreen();
			kkimService.serialCommunicator.ingestDataFromSerialPortToInputRefreshPacketBuffer();
			if (kkimService.serialCommunicator.getisValidPacketInInputRefreshPacketBuffer()) {
				//kkimService.packetUnpacker.displayPacketInDecimal(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
				kkimService.packetUnpacker.unpackInputRefreshPacketIntoModel(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
				kkimService.serialCommunicator.clearInputRefreshPacketBuffer();
				
				kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel();
			}
			this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
			
			kkimService.controlPanel.refresh();
			//System.out.println(kkimService.controlPanel);
		}
		
//		//TEMP: Write data from Serial Buffer (KMega) to the terminal console
//		if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProp.getkMegaInputRefreshPacketReadRateInMilliseconds() ) {
//			kkimService.serialCommunicator.ingestDataFromSerialPortAndDisplay();
//			this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
//		}
		
		//Send outputRefreshPacket
		if ( (System.currentTimeMillis() - this.outputRefreshPacketLastSentTimeInMilliseconds) > KKIMProp.getkMegaOutputRefreshPacketSendRateInMilliseconds()) {
			byte[] outputRefreshPacket = kkimService.packetAssembler.assembleOutputRefreshPacket();
			kkimService.serialCommunicator.sendOutputRefreshPacket(outputRefreshPacket);
			CommonUtilities.clearScreen();
			kkimService.serialCommunicator.printCommunicationsDiagnosticInformation();
			this.outputRefreshPacketLastSentTimeInMilliseconds = System.currentTimeMillis();
		}
		
		kkimService.kRPCCommunicator.sendInfoToKSPFromModel();
		
		//TODO Send packet to kphoneOutputRefreshPacket
		
        kkimService.idleIfNecessary();//TODO replace?
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance()); //TODO add logic for entering Diagnostic and Shutdown modes
    }
}