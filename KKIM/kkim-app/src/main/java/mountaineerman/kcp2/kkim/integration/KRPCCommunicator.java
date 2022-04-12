package mountaineerman.kcp2.kkim.integration;

import java.io.IOException;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.SpaceCenter;
import krpc.client.services.SpaceCenter.Control;
import krpc.client.services.SpaceCenter.Flight;
import krpc.client.services.SpaceCenter.Resources;
import krpc.client.services.SpaceCenter.Vessel;
import mountaineerman.kcp2.kkim.model.ControlPanel;


public class KRPCCommunicator {
	
	private ControlPanel controlPanel;
	private Connection connection = null;
	//private KRPC kRPC = null;
	private SpaceCenter spaceCenter = null;
	private Vessel vessel = null;
	private Flight flight = null;
	private Control control = null;
	private Resources resources = null;
	
	
	public KRPCCommunicator(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public void establishKRPCLink() {
		 
		try {
			System.out.print("Establishing connection to kRPC... ");
			this.connection = Connection.newInstance();
			//this.kRPC = KRPC.newInstance(connection);
			System.out.println("DONE");
			//System.out.println("Connected to kRPC version " + kRPC.getStatus().getVersion());
			this.spaceCenter = SpaceCenter.newInstance(connection);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.vessel = this.spaceCenter.getActiveVessel();
			//this.flight = this.vessel.flight(this.vessel.getReferenceFrame());
			this.flight = this.vessel.flight(this.vessel.getSurfaceReferenceFrame());
			//this.flight = this.vessel.flight(this.vessel.getOrbitalReferenceFrame());
			this.control = this.vessel.getControl();
		} catch (RPCException e) {
			e.printStackTrace();
		}
	}

	public void pullInfoFromKSPIntoModel() {

		try {
			this.resources = this.vessel.resourcesInDecoupleStage(this.control.getCurrentStage()-1, false);
			this.controlPanel.fuel = this.resources.amount("LiquidFuel");//TODO pull constants into config file?
			//System.out.println("fuel: " + this.resources.amount("LiquidFuel"));
			this.controlPanel.gforce = this.flight.getGForce();
			//System.out.println("altitude: " + this.flight.getMeanAltitude());
		} catch (RPCException e) {
			e.printStackTrace();
		}
	}

	public void sendInfoFromModelToKSP() {
		if (this.controlPanel.moduleA.stagingButton.getDebouncedStatus()) {
			try {
				this.control.activateNextStage();
			} catch (RPCException e) {
				e.printStackTrace();
			}	
		}
	}
}








