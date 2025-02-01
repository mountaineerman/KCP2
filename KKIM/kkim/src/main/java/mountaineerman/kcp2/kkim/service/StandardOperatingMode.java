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
		
		//Pull Information
		if ( KKIMProp.getkMegaSendPacketType().equals("outputRefreshPacket") ) {
			if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProp.getkMegaInputRefreshPacketReadRateInMilliseconds() ) {
				kkimService.serialCommunicator.ingestDataFromSerialPortToPacketBuffer();
				this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
				if (kkimService.serialCommunicator.getisValidPacketInPacketBuffer()) {
					switch (kkimService.serialCommunicator.getPacketTypeInPacketBuffer()) {
						case INPUT_REFRESH_PACKET:
							//kkimService.packetUnpacker.displayPacketInDecimal(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
							kkimService.packetUnpacker.unpackInputRefreshPacketIntoModel(kkimService.serialCommunicator.getPacketBuffer());
							kkimService.serialCommunicator.clearPacketBufferAndFriends();
							
							if (kkimService.controlPanel.moduleH.glassCL_Button.getDebouncedStatus()) {//KMega Diagnostic Mode selected, KKIM not needed.
								kkimService.setCurrentOperatingMode(ShutdownMode.getInstance());
								return;
							} else if (kkimService.controlPanel.moduleH.glassCR_Button.getDebouncedStatus()) {//KKIM Diagnostic Mode selected
								kkimService.setCurrentOperatingMode(DiagnosticMode.getInstance());
								return;
							} else if (kkimService.controlPanel.moduleH.glassTR_Button.getDebouncedStatus()) {//Graceful Shutdown requested
								kkimService.setCurrentOperatingMode(ShutdownMode.getInstance());
								return;
							}

							kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel();
							kkimService.controlPanel.refresh();
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
		} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacketA") ||
					KKIMProp.getkMegaSendPacketType().equals("gaugePacketB") ) {
			kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel();
			kkimService.controlPanel.refresh();
		} else {
			throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.getkMegaSendPacketType());
		}
		
		//Send packet to KMega
		if ( (System.currentTimeMillis() - this.outputRefreshPacketLastSentTimeInMilliseconds) > KKIMProp.getkMegaAllPacketsSendRateInMilliseconds()) {

			byte[] packet = null;
			if ( KKIMProp.getkMegaSendPacketType().equals("outputRefreshPacket") ) {
				packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
			} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacketA") ) {
				packet = kkimService.packetAssembler.assembleGaugePacketA();
			} else if ( KKIMProp.getkMegaSendPacketType().equals("gaugePacketB") ) {
				packet = kkimService.packetAssembler.assembleGaugePacketB();
			} else {
				throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.getkMegaSendPacketType());
			}

			kkimService.serialCommunicator.sendPacket(packet);
			this.outputRefreshPacketLastSentTimeInMilliseconds = System.currentTimeMillis();
		}
		
		kkimService.kRPCCommunicator.sendInfoFromModelToKSP();
		
		//TODO Send packet to kphoneOutputRefreshPacket
		
        kkimService.idleIfNecessary();//TODO replace?
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
    }
}