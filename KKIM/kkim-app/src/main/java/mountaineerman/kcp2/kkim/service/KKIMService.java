package mountaineerman.kcp2.kkim.service;

import java.util.Arrays;
//import java.util.Timer;//TODO

import mountaineerman.kcp2.kkim.KKIMProperties;
import mountaineerman.kcp2.kkim.integration.SerialCommunicator;

public class KKIMService {

	//byte[] outputRefreshPacket = new byte[KKIMProperties.getkMegaOutputRefreshPacketLengthInBytes()]; //READ-ONLY after initialization //TODO remove?
	//byte[] inputRefreshPacket = new byte[KKIMProperties.getkMegaInputRefreshPacketLengthInBytes()]; //READ-ONLY after initialization//TODO remove?
	
	private OperatingMode currentOperatingMode = null;
	//private Timer timer;//TODO
	
	//private ControlPanel controlPanel;
	protected SerialCommunicator serialCommunicator = null;
	//private PacketUnpacker packetUnpacker;
	//private PacketAssembler packetAssembler;
	

	
	public KKIMService() {
		//Arrays.fill(outputRefreshPacket, ((byte) 0) );//TODO remove?
		//Arrays.fill(inputRefreshPacket, ((byte) 0) );//TODO remove?
		
		this.currentOperatingMode = StartupMode.getInstance();
		this.serialCommunicator = new SerialCommunicator();
	}
	
	public void run() {
		currentOperatingMode.run(this);
    }
	
	protected void setCurrentOperatingMode(OperatingMode mode) {
		this.currentOperatingMode = mode;
	}
	
	protected void idleIfNecessary() {
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
/*
private:
	void clearOutputRefreshPacket();
	void clearInputRefreshPacket();
	//void displayOutputRefreshPacket(); //TODO remove
	void displayInputRefreshPacket(); //TODO remove		
*/
}
