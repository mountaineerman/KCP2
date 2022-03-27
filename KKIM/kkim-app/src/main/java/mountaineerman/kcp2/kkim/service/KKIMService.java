package mountaineerman.kcp2.kkim.service;

import mountaineerman.kcp2.kkim.KKIMProperties;
import mountaineerman.kcp2.kkim.integration.PacketUnpacker;
import mountaineerman.kcp2.kkim.integration.SerialCommunicator;
import mountaineerman.kcp2.kkim.model.ControlPanel;

public class KKIMService {

	private OperatingMode currentOperatingMode = null;
	
	protected ControlPanel controlPanel = null;
	protected SerialCommunicator serialCommunicator = null;
	protected PacketUnpacker packetUnpacker = null;
	//protected PacketAssembler packetAssembler = null;
	
	
	public KKIMService() {
		this.currentOperatingMode = StartupMode.getInstance();
		
		this.controlPanel = new ControlPanel();
		this.serialCommunicator = new SerialCommunicator();
		this.packetUnpacker = new PacketUnpacker(this.controlPanel);
	}
	
	public void run() {
		currentOperatingMode.run(this);
    }
	
	protected void setCurrentOperatingMode(OperatingMode mode) {
		this.currentOperatingMode = mode;
	}
	
	protected void idleIfNecessary() {
		try {Thread.sleep(KKIMProperties.getkkimRefreshFrequencyInMilliseconds());} catch (InterruptedException e) {e.printStackTrace();}
	}
}
