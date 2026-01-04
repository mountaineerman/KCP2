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
			kkimService.kRPCCommunicator.terminateAllKRPCStreams();
			kkimService.setCurrentOperatingMode(IdleMode.getInstance());
			return;
		}

		//Pull Information from Serial Port (KMega):
		long time_ingestDataFromSerialPort_before = 0;
		long time_ingestDataFromSerialPort_after = 0;
		long time_unpackInputRefreshPacketIntoModel_before = 0;
		long time_unpackInputRefreshPacketIntoModel_after = 0;
		if (KKIMProp.kMegaIsActive) {
			if ( KKIMProp.kMegaSendPacketType.equals("outputRefreshPacket") ) {
				if ( (System.currentTimeMillis() - this.serialPortLastReadTimeInMilliseconds) > KKIMProp.kMegaInputRefreshPacketReadRateInMilliseconds ) {
					time_ingestDataFromSerialPort_before = System.currentTimeMillis();
					kkimService.serialCommunicator.ingestDataFromSerialPortToPacketBuffer();
					time_ingestDataFromSerialPort_after = System.currentTimeMillis();

					this.serialPortLastReadTimeInMilliseconds = System.currentTimeMillis();
					if (kkimService.serialCommunicator.getisValidPacketInPacketBuffer()) {
						switch (kkimService.serialCommunicator.getPacketTypeInPacketBuffer()) {
							case INPUT_REFRESH_PACKET:
								//kkimService.packetUnpacker.displayPacketInDecimal(kkimService.serialCommunicator.getinputRefreshPacketBuffer());
								time_unpackInputRefreshPacketIntoModel_before = System.currentTimeMillis();
								kkimService.packetUnpacker.unpackInputRefreshPacketIntoModel(kkimService.serialCommunicator.getPacketBuffer());
								time_unpackInputRefreshPacketIntoModel_after = System.currentTimeMillis();

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
								break;

							case KKIM_TERMINAL_DISPLAY_PACKET:
								byte[] kkimTerminalDisplayPacket = kkimService.serialCommunicator.getPacketBuffer();
								String payload = new String(Arrays.copyOfRange(kkimTerminalDisplayPacket, KKIMProp.allPacketsHeaderLengthInBytes, kkimTerminalDisplayPacket.length));
								System.out.println("KMEGA: " + payload);
								kkimService.serialCommunicator.clearPacketBufferAndFriends();
								break;

							default:
								break;
						}
					}
				}
			} else if ( KKIMProp.kMegaSendPacketType.equals("gaugePacketA") ||
						KKIMProp.kMegaSendPacketType.equals("gaugePacketB") ) {
				kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel();
				kkimService.controlPanel.refresh();
			} else {
				throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.kMegaSendPacketType);
			}
		}

		//Pull Information from KSP:
		long time_pullInfoFromKSPIntoModel_before = 0;
		long time_pullInfoFromKSPIntoModel_after = 0;
		time_pullInfoFromKSPIntoModel_before = System.currentTimeMillis();
		kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel();
		time_pullInfoFromKSPIntoModel_after = System.currentTimeMillis();

		//Refresh control panel:
		long time_controlPanelRefresh_before = 0;
		long time_controlPanelRefresh_after = 0;
		time_controlPanelRefresh_before = System.currentTimeMillis();
		kkimService.controlPanel.refresh();
		time_controlPanelRefresh_after = System.currentTimeMillis();

		//Send packet to KMega:
		long time_sendPacketToKMega_before = 0;
		long time_sendPacketToKMega_after = 0;
		if (KKIMProp.kMegaIsActive) {
			if ( (System.currentTimeMillis() - this.outputRefreshPacketLastSentTimeInMilliseconds) > KKIMProp.kMegaAllPacketsSendRateInMilliseconds) { //TODO add function like kPhoCommunicator.enoughTimeHasPassedSinceLastKPhoPacketWasSent()
				time_sendPacketToKMega_before = System.currentTimeMillis();
				byte[] packet = null;
				if ( KKIMProp.kMegaSendPacketType.equals("outputRefreshPacket") ) {//TODO Move into PacketAssembler to simplify StandardOperatingMode
					packet = kkimService.packetAssembler.assembleOutputRefreshPacket();
				} else if ( KKIMProp.kMegaSendPacketType.equals("gaugePacketA") ) {
					packet = kkimService.packetAssembler.assembleGaugePacketA();
				} else if ( KKIMProp.kMegaSendPacketType.equals("gaugePacketB") ) {
					packet = kkimService.packetAssembler.assembleGaugePacketB();
				} else {
					throw new RuntimeException("Unrecognized kMegaSendPacketType: " + KKIMProp.kMegaSendPacketType);
				}

				kkimService.serialCommunicator.sendPacket(packet);
				this.outputRefreshPacketLastSentTimeInMilliseconds = System.currentTimeMillis();
				time_sendPacketToKMega_after = System.currentTimeMillis();
			}
		}

		//Send packet to KPho:
		if (KKIMProp.kPhoIsActive) {
			if ( kkimService.kPhoCommunicator.enoughTimeHasPassedSinceLastKPhoPacketWasSent() ) {
				byte[] kPhoPacket = kkimService.packetAssembler.assembleKPhoPacket();
				kkimService.kPhoCommunicator.sendKPhoPacket(kPhoPacket);
			}
		}

		//Send Information to KSP:
		long time_sendInfoFromModelToKSP_before = 0;
		long time_sendInfoFromModelToKSP_after = 0;
		if (KKIMProp.kMegaIsActive) {
			time_sendInfoFromModelToKSP_before = System.currentTimeMillis();
			kkimService.kRPCCommunicator.sendInfoFromModelToKSP();
			time_sendInfoFromModelToKSP_after = System.currentTimeMillis();
		}

		// (OPTIONAL) Display Diagnostic Information:
		if ( KKIMProp.kkimDisplayCommunicationsDiagnosticInformation ) {
			kkimService.serialCommunicator.printCommunicationsDiagnosticInformation();
		}
		if ( KKIMProp.kkimDisplayTaskDurationsDiagnosticInformation ) {
			if(time_ingestDataFromSerialPort_before != 0 &&
				time_unpackInputRefreshPacketIntoModel_before != 0 &&
				time_pullInfoFromKSPIntoModel_before != 0 &&
				time_controlPanelRefresh_before != 0 &&
				time_sendPacketToKMega_before != 0 &&
				time_sendInfoFromModelToKSP_before != 0) {
				System.out.println("------------------------------------------------------------");
				System.out.println("Reminder: only showing data when all time recordings are non-zero (see if statement in code).");
				System.out.println();
				System.out.println("kkimService.serialCommunicator.ingestDataFromSerialPortToPacketBuffer(): " + (time_ingestDataFromSerialPort_after - time_ingestDataFromSerialPort_before));
				System.out.println("kkimService.packetUnpacker.unpackInputRefreshPacketIntoModel(...): " + (time_unpackInputRefreshPacketIntoModel_after - time_unpackInputRefreshPacketIntoModel_before));
				System.out.println();
				System.out.println("kkimService.kRPCCommunicator.pullInfoFromKSPIntoModel(): " + (time_pullInfoFromKSPIntoModel_after - time_pullInfoFromKSPIntoModel_before));
				System.out.println("kkimService.controlPanel.refresh(): " + (time_controlPanelRefresh_after - time_controlPanelRefresh_before));
				System.out.println();
				System.out.println("Send packet (usually outputRefreshPacket) to KMega: " + (time_sendPacketToKMega_after - time_sendPacketToKMega_before));
				System.out.println("Send packet to KPHo: TODO");//TODO
				System.out.println("kkimService.kRPCCommunicator.sendInfoFromModelToKSP(): " + (time_sendInfoFromModelToKSP_after - time_sendInfoFromModelToKSP_before));
				System.out.println();
				System.out.println("Idle (TODO: optimize idleIfNecessary()): " + KKIMProp.kkimRefreshFrequencyInMilliseconds);
			}
		}
		
        kkimService.idleIfNecessary();//TODO replace?
        kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
    }
}