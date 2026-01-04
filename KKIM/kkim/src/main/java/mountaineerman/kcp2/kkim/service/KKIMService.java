package mountaineerman.kcp2.kkim.service;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.integration.KPhoCommunicator;
import mountaineerman.kcp2.kkim.integration.KRPCCommunicator;
import mountaineerman.kcp2.kkim.integration.PacketAssembler;
import mountaineerman.kcp2.kkim.integration.PacketUnpacker;
import mountaineerman.kcp2.kkim.integration.SerialCommunicator;
import mountaineerman.kcp2.kkim.model.ControlPanel;

public class KKIMService {

	private OperatingMode currentOperatingMode = null;
	
	protected ControlPanel controlPanel = null;
	protected SerialCommunicator serialCommunicator = null;
	protected PacketUnpacker packetUnpacker = null;
	protected PacketAssembler packetAssembler = null;
	protected KRPCCommunicator kRPCCommunicator = null;
	protected KPhoCommunicator kPhoCommunicator = null;
	
	
	public KKIMService() {
		this.currentOperatingMode = StartupMode.getInstance();
		
		this.controlPanel = new ControlPanel();
		this.serialCommunicator = new SerialCommunicator();
		this.packetUnpacker = new PacketUnpacker(this.controlPanel);
		this.packetAssembler = new PacketAssembler(this.controlPanel);
		this.kRPCCommunicator = new KRPCCommunicator(this.controlPanel);
		this.kPhoCommunicator = new KPhoCommunicator();
	}
	
	public void run() {
		currentOperatingMode.run(this);
    }
	
	protected void setCurrentOperatingMode(OperatingMode mode) {
		this.currentOperatingMode = mode;
	}
	
	protected void idleIfNecessary() {
		try {Thread.sleep(KKIMProp.kkimRefreshFrequencyInMilliseconds);} catch (InterruptedException e) {e.printStackTrace();}
	}
}
