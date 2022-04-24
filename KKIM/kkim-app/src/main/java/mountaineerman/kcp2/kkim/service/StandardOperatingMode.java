package mountaineerman.kcp2.kkim.service;

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
		
//		long time1 = 0;
//		long time2 = 0;
//		long time3 = 0;
//		long time4 = 0;
//		long time5 = 0;
//		long time6 = 0;
//		long time7 = 0;
//		long time8 = 0;
//		long time9 = 0;
//		long time10 = 0;
//		long time11 = 0;
//		long time12 = 0;
//		boolean pulledData = false;
//		boolean sentOutputRefreshPacket = false;
		
		//Pull Information
		if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProp.getkMegaInputRefreshPacketReadRateInMilliseconds() ) {
			//CommonUtilities.clearScreen();
//			time1 = System.currentTimeMillis();
			kkimService.serialCommunicator.ingestDataFromSerialPortToInputRefreshPacketBuffer();
//			time2 = System.currentTimeMillis();
			if (kkimService.serialCommunicator.getisValidPacketInInputRefreshPacketBuffer()) {
//				pulledData = true;
				//kkimService.packetUnpacker.displayPacketInDecimal(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
//				time3 = System.currentTimeMillis();
				kkimService.packetUnpacker.unpackInputRefreshPacketIntoModel(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
//				time4 = System.currentTimeMillis();
				kkimService.serialCommunicator.clearInputRefreshPacketBuffer();
//				time5 = System.currentTimeMillis();
				
//				time6 = System.currentTimeMillis();
				kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel();
//				time7 = System.currentTimeMillis();
			}
			this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
			
//			time8 = System.currentTimeMillis();
			kkimService.controlPanel.refresh();
//			time9 = System.currentTimeMillis();
			//CommonUtilities.clearScreen(); System.out.println(kkimService.controlPanel.toString());
		}
//		
//		//TEMP: Write data from Serial Buffer (KMega) to the terminal console
//		if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProp.getkMegaInputRefreshPacketReadRateInMilliseconds() ) {
//			kkimService.serialCommunicator.ingestDataFromSerialPortAndDisplay();
//			this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
//		}
		
		//Send outputRefreshPacket
		if ( (System.currentTimeMillis() - this.outputRefreshPacketLastSentTimeInMilliseconds) > KKIMProp.getkMegaOutputRefreshPacketSendRateInMilliseconds()) {
//			sentOutputRefreshPacket = true;
//			time10 = System.currentTimeMillis();
			byte[] outputRefreshPacket = kkimService.packetAssembler.assembleOutputRefreshPacket();
//			time11 = System.currentTimeMillis();
			kkimService.serialCommunicator.sendOutputRefreshPacket(outputRefreshPacket);
//			time12 = System.currentTimeMillis();
			//CommonUtilities.clearScreen();
			//kkimService.serialCommunicator.printCommunicationsDiagnosticInformation();
			this.outputRefreshPacketLastSentTimeInMilliseconds = System.currentTimeMillis();
		}
		
//		long time13 = System.currentTimeMillis();
		kkimService.kRPCCommunicator.sendInfoFromModelToKSP();
//		long time14 = System.currentTimeMillis();
		
//		if (pulledData && sentOutputRefreshPacket) {
//			CommonUtilities.clearScreen();
//			System.out.println("serialCommunicator.ingestDataFromSerialPortToInputRefreshPacketBuffer(): " + (time2 - time1));
//			System.out.println("packetUnpacker.unpackInputRefreshPacketIntoModel(): " + (time4 - time3));
//			System.out.println("serialCommunicator.clearInputRefreshPacketBuffer(): " + (time5 - time4));
//			System.out.println("kRPCCommunicator.pullInfoFromKSPIntoModel(): " + (time7 - time6));
//			System.out.println("controlPanel.refresh(): " + (time9 - time8));
//			System.out.println("packetAssembler.assembleOutputRefreshPacket(): " + (time11 - time10));
//			System.out.println("serialCommunicator.sendOutputRefreshPacket(): " + (time12 - time11));
//			System.out.println("kRPCCommunicator.sendInfoFromModelToKSP(): " + (time14 - time13));
//			System.out.println("Total: " + (time14 - time1));
//		}
		
		//TODO Send packet to kphoneOutputRefreshPacket
		
        kkimService.idleIfNecessary();//TODO replace?
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance()); //TODO add logic for entering Diagnostic and Shutdown modes
    }
}