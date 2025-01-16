package mountaineerman.kcp2.kkim.service;

import java.util.Arrays;

import krpc.client.services.KRPC.GameScene;
//import mountaineerman.kcp2.kkim.CommonUtilities;
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
	
	public void run(KKIMService kkimService) {
		//TODO Confirm connection to kRPC, KMega, and KPhone

		if (kkimService.kRPCCommunicator.fetchCurrentGameSceneInKSP() != GameScene.FLIGHT) { // (SPACE_CENTER, TRACKING_STATION, EDITOR_VAB, or EDITOR_SPH)
			kkimService.setCurrentOperatingMode(IdleMode.getInstance());
			return;
		}
		
		// long time1 = 0;
		// long time2 = 0;
		// long time3 = 0;
		// long time4 = 0;
		// long time5 = 0;
		// long time6 = 0;
		// long time7 = 0;
		// long time8 = 0;
		// long time9 = 0;
		// long time10 = 0;
		// long time11 = 0;
		// long time12 = 0;
		// boolean pulledData = false;
		// boolean sentOutputRefreshPacket = false;
		
		//Pull Information
		if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProp.getkMegaInputRefreshPacketReadRateInMilliseconds() ) {
			// time1 = System.currentTimeMillis();
			kkimService.serialCommunicator.ingestDataFromSerialPortToPacketBuffer();
			this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
			// time2 = System.currentTimeMillis();
			if (kkimService.serialCommunicator.getisValidPacketInPacketBuffer()) {
				switch (kkimService.serialCommunicator.getPacketTypeInPacketBuffer()) {
					case INPUT_REFRESH_PACKET:
						// pulledData = true;
						//kkimService.packetUnpacker.displayPacketInDecimal(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
						// time3 = System.currentTimeMillis();
						kkimService.packetUnpacker.unpackInputRefreshPacketIntoModel(kkimService.serialCommunicator.getPacketBuffer());
						// time4 = System.currentTimeMillis();
						kkimService.serialCommunicator.clearPacketBufferAndFriends();
						// time5 = System.currentTimeMillis();
						
						// time6 = System.currentTimeMillis();
						kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel();
						// time7 = System.currentTimeMillis();
						
						// time8 = System.currentTimeMillis();
						kkimService.controlPanel.refresh();
						// time9 = System.currentTimeMillis();
						//CommonUtilities.clearScreen(); System.out.println(kkimService.controlPanel.toString());
						break;
					case KKIM_TERMINAL_DISPLAY_PACKET:
						byte[] kkimTerminalDisplayPacket = kkimService.serialCommunicator.getPacketBuffer();
						String payload = new String(Arrays.copyOfRange(kkimTerminalDisplayPacket, KKIMProp.getallPacketsHeaderLengthInBytes(), kkimTerminalDisplayPacket.length));
						System.out.println("KMEGA: " + payload);
						kkimService.serialCommunicator.clearPacketBufferAndFriends();
						break;
					default:
						break;
				}
			}
		}
		
		//Send packet to KMega
		if ( (System.currentTimeMillis() - this.outputRefreshPacketLastSentTimeInMilliseconds) > KKIMProp.getkMegaAllPacketsSendRateInMilliseconds()) {

			byte[] packet = null;
			if ( KKIMProp.getkMegaSendPacketType().equals("outputRefreshPacket") ) {
				// sentOutputRefreshPacket = true;
				// time10 = System.currentTimeMillis();
				packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
				// time11 = System.currentTimeMillis();
			} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacket6") ) {
				packet = kkimService.packetAssembler.assembleGaugePacket6();
			} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacket5") ) {
				//FIXME
			} else {
				throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.getkMegaSendPacketType());
			}

			kkimService.serialCommunicator.sendPacket(packet);
			// time12 = System.currentTimeMillis();
			// CommonUtilities.clearScreen();
			// kkimService.serialCommunicator.printCommunicationsDiagnosticInformation();
			this.outputRefreshPacketLastSentTimeInMilliseconds = System.currentTimeMillis();
		}
		
		// long time13 = System.currentTimeMillis();
		kkimService.kRPCCommunicator.sendInfoFromModelToKSP();
		// long time14 = System.currentTimeMillis();
		
//		if (pulledData && sentOutputRefreshPacket) {
//			CommonUtilities.clearScreen();
//			System.out.println("serialCommunicator.ingestDataFromSerialPortToInputRefreshPacketBuffer(): " + (time2 - time1));
//			System.out.println("packetUnpacker.unpackInputRefreshPacketIntoModel(): " + (time4 - time3));
//			System.out.println("serialCommunicator.clearPacketBufferAndFriends(): " + (time5 - time4));
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