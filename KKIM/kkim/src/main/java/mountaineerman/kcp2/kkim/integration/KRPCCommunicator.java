package mountaineerman.kcp2.kkim.integration;

import java.io.IOException;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.SpaceCenter;
import krpc.client.services.KRPC;
import krpc.client.services.KRPC.GameScene;
import krpc.client.services.SpaceCenter.Control;
import krpc.client.services.SpaceCenter.Flight;
import krpc.client.services.SpaceCenter.Orbit;
import krpc.client.services.SpaceCenter.Part;
import krpc.client.services.SpaceCenter.Resources;
import krpc.client.services.SpaceCenter.Vessel;
import krpc.client.services.SpaceCenter.Camera;
import krpc.client.services.SpaceCenter.CameraMode;
import krpc.client.services.SpaceCenter.SASMode;
import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.model.ControlPanel;
import mountaineerman.kcp2.kkim.model.SP3TPosition;


public class KRPCCommunicator {
	
	private ControlPanel controlPanel;
	private Connection connection = null;
	private KRPC kRPC = null;
	private SpaceCenter spaceCenter = null;
	private Vessel vessel = null;
	private Flight flight = null;
	private Flight flight_OrbitBody_NormalReferenceFrame = null;
	private Flight flight_OrbitBody_OrbitalReferenceFrame = null;
	private Orbit orbit = null;
	private Control control = null;
	private Camera camera = null;
	private Resources currentStageResources = null;
	private Resources vesselResources = null;
	
	
	public KRPCCommunicator(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public void establishKRPCLink() {
		
		int numberOfAttempts = 0;
		int maxTries = 12;
		while(true) {
			numberOfAttempts++;
			try {
				System.out.print("Establishing connection to kRPC... ");
				this.connection = Connection.newInstance();
				System.out.println("DONE");
				break;
			} catch (IOException io_e) {
				
				System.out.println("FAILED. Message: \"" + io_e.getMessage() + "\"");

				if (numberOfAttempts >= maxTries) {
					io_e.printStackTrace();
					System.out.println("Attempted to establish kRPC connection " + numberOfAttempts + " times. Aborting...");
					System.exit(-1);
				}

				System.out.println("Sleeping for " + KKIMProp.getkkimStartupModeSleepIntervalInMilliseconds() + "ms, then trying again.");
				try {
					Thread.sleep(KKIMProp.getkkimStartupModeSleepIntervalInMilliseconds());
				} catch (InterruptedException i_e) {i_e.printStackTrace();}
			}
		}
		this.kRPC = KRPC.newInstance(connection);
		this.spaceCenter = SpaceCenter.newInstance(connection);
	}

	/**
	 * Set kRPC objects: vessel, flight, orbit, reference frames, control, camera.
	 */
	public void establishKRPCFlightHooks() {
		try {
			this.vessel = this.spaceCenter.getActiveVessel();
			this.flight = this.vessel.flight(this.vessel.getSurfaceReferenceFrame());
			this.orbit = this.vessel.getOrbit();
			this.flight_OrbitBody_NormalReferenceFrame = this.vessel.flight(this.orbit.getBody().getReferenceFrame());
			this.flight_OrbitBody_OrbitalReferenceFrame = this.vessel.flight(this.orbit.getBody().getOrbitalReferenceFrame());
			this.control = this.vessel.getControl();
			this.camera = this.spaceCenter.getCamera();
		} catch (RPCException e) {
			e.printStackTrace();
			System.exit(-1);
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
			this.controlPanel.currentAirDensity = this.flight.getAtmosphereDensity();
			this.controlPanel.maxAirDensity = (float) this.orbit.getBody().densityAt(0.0);
			this.controlPanel.surfaceReferenceFrame_speed         = flight_OrbitBody_NormalReferenceFrame.getSpeed();
			this.controlPanel.surfaceReferenceFrame_verticalSpeed = flight_OrbitBody_NormalReferenceFrame.getVerticalSpeed();
			this.controlPanel.orbitalReferenceFrame_speed         = flight_OrbitBody_OrbitalReferenceFrame.getSpeed();
			this.controlPanel.orbitalReferenceFrame_verticalSpeed = flight_OrbitBody_OrbitalReferenceFrame.getVerticalSpeed();
			this.controlPanel.vesselSituation = this.vessel.getSituation();
			this.controlPanel.altitudeAboveSurface = this.flight.getSurfaceAltitude();
			this.controlPanel.altitudeAboveSeaLevel = this.flight.getMeanAltitude();
			this.controlPanel.currentSASMode = this.control.getSASMode();
		} catch (RPCException e) {
			e.printStackTrace();
		}
	}

	public GameScene fetchCurrentGameSceneInKSP() {
		GameScene scene = null;
		try{
			scene = this.kRPC.getCurrentGameScene();
		} catch (RPCException e) {e.printStackTrace();}
		return scene;
	}

	public void sendInfoFromModelToKSP() {
		
		//Module A
		if (this.controlPanel.moduleA.stagingButton.getDebouncedStatus()) {
			try {
				this.control.activateNextStage();
			} catch (RPCException e) {e.printStackTrace();}	
		}
		
		//Module B
		if (this.controlPanel.moduleB.abortButton.getDebouncedStatus()) {
			try {
				this.control.activateNextStage();
			} catch (RPCException e) {e.printStackTrace();}	
		}
		if (this.controlPanel.moduleB.timeWarpDownButton.getDebouncedStatus()) {
			this.requestLowerTimeWarp();
		}
		if (this.controlPanel.moduleB.timeWarpUpButton.getDebouncedStatus()) {
			this.requestHigherTimeWarp();
		}
		
		//Module D
		if (this.controlPanel.moduleD.sasSwitch.statusChanged()) {
			try {
				this.control.setSAS(this.controlPanel.moduleD.sasSwitch.getStatus());
			} catch (RPCException e) {e.printStackTrace();}
		}

		if (this.controlPanel.moduleD.sasSwitch.getStatus()) {
			try {
				try {
					if (this.controlPanel.moduleD.autoHoldButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.STABILITY_ASSIST);
					} else if (this.controlPanel.moduleD.autoProgradeButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.PROGRADE);
					} else if (this.controlPanel.moduleD.autoRetrogradeButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.RETROGRADE);
					} else if (this.controlPanel.moduleD.autoNormalButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.NORMAL);
					} else if (this.controlPanel.moduleD.autoAntiNormalButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.ANTI_NORMAL);
					} else if (this.controlPanel.moduleD.autoRadialInButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.ANTI_RADIAL);
					} else if (this.controlPanel.moduleD.autoRadialOutButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.RADIAL);
					} else if (this.controlPanel.moduleD.autoTargetButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.TARGET);
					} else if (this.controlPanel.moduleD.autoAntiTargetButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.ANTI_TARGET);
					} else if (this.controlPanel.moduleD.autoManeuverButton.getDebouncedStatus()) {
						this.control.setSASMode(SASMode.MANEUVER);
					}
				} catch (UnsupportedOperationException uo_e) {} //If unable to switch to requested SASMode, do nothing
			} catch (RPCException rpc_e) {rpc_e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleD.rcsSwitch.statusChanged()) {
			try {
				this.control.setRCS(this.controlPanel.moduleD.rcsSwitch.getStatus());
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleD.lightsSwitch.statusChanged()) {
			try {
				this.control.setLights(this.controlPanel.moduleD.lightsSwitch.getStatus());
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleD.gearSwitch.statusChanged()) {
			try {
				this.control.setGear(this.controlPanel.moduleD.gearSwitch.getStatus());
				this.control.setLegs(this.controlPanel.moduleD.gearSwitch.getStatus());
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleD.mapSwitch.statusChanged()) {
			try {
				if (this.controlPanel.moduleD.mapSwitch.getStatus()) {
					this.camera.setMode(CameraMode.MAP);
				} else {
					this.camera.setMode(CameraMode.AUTOMATIC);
				}
			} catch (RPCException e) {e.printStackTrace();}
		}

		//Module E
		if (this.controlPanel.moduleE.scienceSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(4);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleE.resetSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(5);
			} catch (RPCException e) {e.printStackTrace();}
		}		
	
		if (this.controlPanel.moduleE.solarSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(6);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleE.ladderSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(7);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
//		if (this.controlPanel.moduleE.atnvSwitch.getDebouncedStatus()) {
//			//TBD
//		}
		
		if (this.controlPanel.moduleE.ag1Switch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(1);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleE.ag2Switch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(2);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleE.ag3Switch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(3);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleE.fairingButton.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(8);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		if (this.controlPanel.moduleE.chuteButton.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(9);
			} catch (RPCException e) {e.printStackTrace();}
		}
		
		//Module F
		//TODO Trim...

		
		//Multi-Module
		if (this.controlPanel.brake.statusChanged()) {
			try {
				this.control.setBrakes(this.controlPanel.brake.getStatus());
			} catch (RPCException e) {e.printStackTrace();}
		}
		
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
				this.control.setPitch(-this.controlPanel.joystick_FwdBck);
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


	/* https://wiki.kerbalspaceprogram.com/wiki/Time_warp
	 * 
	 * Physics Warp Factor		Time Multiplier
	 * 			0				1x
	 * 			1				2x
	 * 			2				3x
	 * 			3				4x
	 * 
	 * Rails Warp Factor		Time Multiplier
	 * 			0				1x
	 * 			1				5x
	 * 			2				10x
	 * 			3				50x
	 * 			4				100x
	 * 			5				1,000x
	 * 			6				10,000x
	 * 			7				100,000x */
	private void requestLowerTimeWarp() {
		try {
			if (spaceCenter.canRailsWarpAt(1)) {// rails warp is possible
				int currentWarp = spaceCenter.getRailsWarpFactor();
				if (currentWarp > 0) {
					spaceCenter.setRailsWarpFactor(currentWarp-1);
				}
			} else {// physics warp is possible
				int currentWarp = spaceCenter.getPhysicsWarpFactor();
				if (currentWarp > 0) {
					spaceCenter.setPhysicsWarpFactor(currentWarp-1);
				}
			}
		} catch (RPCException e) {e.printStackTrace();}	
	}

	private void requestHigherTimeWarp() {
		try { 
			if (spaceCenter.canRailsWarpAt(1)) {// rails warp is possible
				int currentWarp = spaceCenter.getRailsWarpFactor();
				int maxWarp = spaceCenter.getMaximumRailsWarpFactor();
				if (currentWarp < maxWarp) {
					spaceCenter.setRailsWarpFactor(currentWarp+1);
				}
			} else {// physics warp is possible
				int currentWarp = spaceCenter.getPhysicsWarpFactor();
				if (currentWarp < 3) {
					spaceCenter.setPhysicsWarpFactor(currentWarp+1);
				}
			}
		} catch (RPCException e) {e.printStackTrace();}	
	}
}