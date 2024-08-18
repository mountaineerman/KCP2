package mountaineerman.kcp2.kkim.integration;

import java.io.IOException;
import java.util.List;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.SpaceCenter;
import krpc.client.services.SpaceCenter.Control;
import krpc.client.services.SpaceCenter.Flight;
import krpc.client.services.SpaceCenter.Part;
import krpc.client.services.SpaceCenter.Resources;
import krpc.client.services.SpaceCenter.Vessel;
import mountaineerman.kcp2.kkim.model.ControlPanel;
import mountaineerman.kcp2.kkim.model.SP3TPosition;


public class KRPCCommunicator {
	
	private ControlPanel controlPanel;
	private Connection connection = null;
	//private KRPC kRPC = null;
	private SpaceCenter spaceCenter = null;
	private Vessel vessel = null;
	private Flight flight = null;
	private Control control = null;
	//private Camera camera = null;
	private Resources currentStageResources = null;
	private Resources vesselResources = null;
	
	
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
			//this.camera = this.spaceCenter.getCamera();
		} catch (RPCException e) {
			e.printStackTrace();
		}
	}

	public void pullInfoFromKSPIntoModel() {//TODO Rewrite

		/*
		 * See: https://krpc.github.io/krpc/java/api/space-center/vessel.html
		 * 		https://krpc.github.io/krpc/java/api/space-center/resources.html#
		 * 
		 * Possible Resources:
		 *   ElectricCharge, MonoPropellant, Food, Water, Oxygen, CarbonDioxide, Waste, WasteWater, LiquidFuel, SolidFuel, Oxidizer, IntakeAir //TODO exotic fuels...
		 */
		
		try {
			this.currentStageResources = this.vessel.resourcesInDecoupleStage(this.control.getCurrentStage()-1, false);
			//System.out.println("Current Stage Resources: " + this.currentStageResources.getNames());
			this.vesselResources = this.vessel.getResources();
			//System.out.println("Vessel Resources: " + this.vesselResources.getNames());
			
			int highestPercentTemperature = 0;
			for (Part part : this.vessel.getParts().getAll()) {
				int t1 = (int) (part.getTemperature() / part.getMaxTemperature() * 100);
				if (t1 > highestPercentTemperature) {
					highestPercentTemperature = t1;
				}
				int t2 = (int) (part.getSkinTemperature() / part.getMaxSkinTemperature() * 100);
				if (t2 > highestPercentTemperature) {
					highestPercentTemperature = t1;
				}
			}
			this.controlPanel.percentTemperatureHealth = 100 - highestPercentTemperature;
			
			this.controlPanel.currentFood = this.vesselResources.amount("Food");
			this.controlPanel.maxFood = this.vesselResources.max("Food");
			this.controlPanel.currentWater = this.vesselResources.amount("Water");
			this.controlPanel.maxWater = this.vesselResources.max("Water");
			this.controlPanel.currentOxygen = this.vesselResources.amount("Oxygen");
			this.controlPanel.maxOxygen = this.vesselResources.max("Oxygen");
			this.controlPanel.gforce = this.flight.getGForce();
			this.controlPanel.mach = this.flight.getMach();
			this.controlPanel.pitch = this.flight.getPitch();
			this.controlPanel.heading = this.flight.getHeading();
			this.controlPanel.currentLiquidFuel = this.currentStageResources.amount("LiquidFuel");
			this.controlPanel.maxLiquidFuel = this.currentStageResources.max("LiquidFuel");
			this.controlPanel.currentSolidFuel = this.currentStageResources.amount("SolidFuel");
			this.controlPanel.maxSolidFuel = this.currentStageResources.max("SolidFuel");
			this.controlPanel.currentElectricCharge = this.vesselResources.amount("ElectricCharge");
			this.controlPanel.maxElectricCharge = this.vesselResources.max("ElectricCharge");
			this.controlPanel.currentMonopropellant = this.vesselResources.amount("MonoPropellant");
			this.controlPanel.maxMonopropellant = this.vesselResources.max("MonoPropellant");
			//FIXME Use Intake part: Flow instead: https://krpc.github.io/krpc/csharp/api/space-center/parts.html#intake
			//for (Intake intake : this.vessel.getParts().getIntakes()) {	
			//}
			
			//this.controlPanel.airDensity = this.flight.getAtmosphereDensity() / CelestialBody.densityAt(Altitude); + Vessel.getSituation()... OR: getStaticPressure()/getStaticPressureAtMSL();
//			this.controlPanel.speed = this.flight.getSpeed();//TODO figure out reference frame?
//			this.controlPanel.verticalSpeed = this.flight.getVerticalSpeed();//TODO figure out reference frame?
			this.controlPanel.altitudeAboveSurface = this.flight.getSurfaceAltitude();
			this.controlPanel.altitudeAboveSeaLevel = this.flight.getMeanAltitude();
		} catch (RPCException e) {
			e.printStackTrace();
		}
	}

	public void sendInfoFromModelToKSP() {//TODO Rewrite. Only call KSP to status change...
		
		//Module A
		if (this.controlPanel.moduleA.stagingButton.getDebouncedStatus()) {
			try {
				this.control.activateNextStage();
			} catch (RPCException e) {e.printStackTrace();}	
		}
		
		//Module B
//		try {
//			this.control.setAbort(this.controlPanel.moduleB.abortButton.getDebouncedStatus());
//		} catch (RPCException e) {e.printStackTrace();}
		if (this.controlPanel.moduleB.abortButton.getDebouncedStatus()) {
			try {
				this.control.activateNextStage();
			} catch (RPCException e) {e.printStackTrace();}	
		}
		
		//Module D
		if (this.controlPanel.moduleD.sasSwitch.getStatus()) {
			try {
				this.control.setSAS(true);
			} catch (RPCException e) {e.printStackTrace();}
			
//			if (this.controlPanel.moduleD.autoHoldButton.getDebouncedStatus()) {//TODO: "Exception in thread "main" java.lang.UnsupportedOperationException: Cannot set SAS mode of vessel"
//				try {
//					this.control.setSASMode(SASMode.STABILITY_ASSIST);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoProgradeButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.PROGRADE);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoRetrogradeButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.RETROGRADE);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoNormalButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.NORMAL);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoAntiNormalButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.ANTI_NORMAL);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoRadialInButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.RADIAL);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoRadialOutButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.ANTI_RADIAL);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoTargetButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.TARGET);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoAntiTargetButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.ANTI_TARGET);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
//			
//			if (this.controlPanel.moduleD.autoManeuverButton.getDebouncedStatus()) {
//				try {
//					this.control.setSASMode(SASMode.MANEUVER);
//				} catch (RPCException e) {e.printStackTrace();}
//			}
			
		} else {
			try {
				this.control.setSAS(false);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		try {
			this.control.setRCS(this.controlPanel.moduleD.rcsSwitch.getStatus());
		} catch (RPCException e) {e.printStackTrace();}
		
		try {
			this.control.setLights(this.controlPanel.moduleD.lightsSwitch.getStatus());
		} catch (RPCException e) {e.printStackTrace();}
		
		try {//TODO more detailed tweaking required?
			if(this.controlPanel.moduleD.gearSwitch.getStatus()) {
				this.control.setGear(true);
				this.control.setLegs(true);
			} else {
				this.control.setGear(false);
				this.control.setLegs(false);
			}
		} catch (RPCException e) {e.printStackTrace();}
		
//		try { //TODO More detailed tweaking required
//			if (this.controlPanel.moduleD.mapSwitch.getStatus()) {
//				this.camera.setMode(CameraMode.MAP);
//			} else {
//				this.camera.setMode(CameraMode.AUTOMATIC);
//			}
//		} catch (RPCException e) {e.printStackTrace();}
		
		//Module E
//		if (this.controlPanel.moduleE.scienceSwitch.getDebouncedStatus()) {
//			try {
//				//TBD
//			} catch (RPCException e) {e.printStackTrace();}
//		}
//		
//		if (this.controlPanel.moduleE.resetSwitch.getDebouncedStatus()) {
//			try {
//				//TBD
//			} catch (RPCException e) {e.printStackTrace();}
//		}
		
		if (this.controlPanel.moduleE.solarSwitch.getDebouncedStatus()) {
			try {
				this.control.setActionGroup(5, true);//FIXME toggle not working
			} catch (RPCException e) {e.printStackTrace();}
		} else {
			try {
				this.control.setActionGroup(5, false);//FIXME toggle not working
			} catch (RPCException e) {e.printStackTrace();}
		}
		
//		if (this.controlPanel.moduleE.ladderSwitch.getDebouncedStatus()) {
//			try {
//				this.control.setActionGroup(4, true);//FIXME toggle not working
//			} catch (RPCException e) {e.printStackTrace();}
//		}
		
//		if (this.controlPanel.moduleE.atnvSwitch.getDebouncedStatus()) {
//			try {
//				//TBD
//			} catch (RPCException e) {e.printStackTrace();}
//		}
		
//		if (this.controlPanel.moduleE.ag1Switch.getDebouncedStatus()) {
//			try {
//				this.control.setActionGroup(1, true);
//			} catch (RPCException e) {e.printStackTrace();}
//		}
//		
//		if (this.controlPanel.moduleE.ag2Switch.getDebouncedStatus()) {
//			try {
//				this.control.setActionGroup(2, true);
//			} catch (RPCException e) {e.printStackTrace();}
//		}
//		
//		if (this.controlPanel.moduleE.ag3Switch.getDebouncedStatus()) {
//			try {
//				this.control.setActionGroup(3, true);
//			} catch (RPCException e) {e.printStackTrace();}
//		}
//		
		if (this.controlPanel.moduleE.fairingButton.getDebouncedStatus()) {
			try {
				this.control.setActionGroup(7, true);
			} catch (RPCException e) {e.printStackTrace();}
		} else {
			try {
				this.control.setActionGroup(7, false);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleE.chuteButton.getDebouncedStatus()) {//TODO repack parachute?
			try {
				this.control.setActionGroup(6, true);
				//this.control.setParachutes(true);
			} catch (RPCException e) {e.printStackTrace();}
		} else {
			try {
				this.control.setActionGroup(6, false);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		//Module F
		
		
		//Multi-Module
		try {
			this.control.setBrakes(this.controlPanel.brake);
		} catch (RPCException e) {e.printStackTrace();}
		
		
		//TODO Disable other controls when switching between modes...
		if (this.controlPanel.moduleE.sp3tVehicleModeSwitch.getPosition() == SP3TPosition.TOP) {//RKT
			try {
				this.control.setThrottle(this.controlPanel.throttleLever);
			} catch (RPCException e) {e.printStackTrace();}
			
			//Rotation Mode
			try {
				this.control.setPitch(this.controlPanel.joystick_FwdBck);
			} catch (RPCException e) {e.printStackTrace();}
			try {
				this.control.setYaw(this.controlPanel.joystick_LftRgh);
			} catch (RPCException e) {e.printStackTrace();}
			try {
				this.control.setRoll(this.controlPanel.joystick_Twist);
			} catch (RPCException e) {e.printStackTrace();}
			
//			//TODO Translation Mode
//			try {
//				this.control.setForward(this.controlPanel.joystick_Twist);
//			} catch (RPCException e) {e.printStackTrace();}
//			try {
//				this.control.setUp(this.controlPanel.joystick_FwdBck);
//			} catch (RPCException e) {e.printStackTrace();}
//			try {
//				this.control.setRight(this.controlPanel.joystick_LftRgh);
//			} catch (RPCException e) {e.printStackTrace();}
		} else if (this.controlPanel.moduleE.sp3tVehicleModeSwitch.getPosition() == SP3TPosition.CENTER) {//PLN
			try {
				this.control.setThrottle(this.controlPanel.throttleLever);
			} catch (RPCException e) {e.printStackTrace();}
			try {
				this.control.setPitch(this.controlPanel.joystick_FwdBck);
			} catch (RPCException e) {e.printStackTrace();}
			try {
				this.control.setYaw(this.controlPanel.joystick_Twist);
			} catch (RPCException e) {e.printStackTrace();}
			try {
				this.control.setRoll(this.controlPanel.joystick_LftRgh);
			} catch (RPCException e) {e.printStackTrace();}
		} else if (this.controlPanel.moduleE.sp3tVehicleModeSwitch.getPosition() == SP3TPosition.BOTTOM) {//RVR
			try {
				this.control.setWheelSteering(this.controlPanel.joystick_LftRgh);
			} catch (RPCException e) {e.printStackTrace();}
			
			//Forward "Gear"
			try {
				this.control.setWheelThrottle(this.controlPanel.throttleLever);
			} catch (RPCException e) {e.printStackTrace();}
			//TODO Reverse "Gear"
//			try {
//				this.control.setWheelThrottle(this.controlPanel.throttleLever * -1);
//			} catch (RPCException e) {e.printStackTrace();}
		} else {//INVALID
			//TODO
		}
	}
}





















