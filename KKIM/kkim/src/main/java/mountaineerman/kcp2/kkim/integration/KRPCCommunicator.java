package mountaineerman.kcp2.kkim.integration;

import java.io.IOException;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.Stream;
import krpc.client.StreamException;
import krpc.client.services.SpaceCenter;
import krpc.client.services.KRPC;
import krpc.client.services.KRPC.GameScene;
import krpc.client.services.SpaceCenter.Control;
import krpc.client.services.SpaceCenter.Flight;
import krpc.client.services.SpaceCenter.Orbit;
//import krpc.client.services.SpaceCenter.Parts;
import krpc.client.services.SpaceCenter.Resources;
import krpc.client.services.SpaceCenter.Vessel;
import krpc.client.services.SpaceCenter.VesselSituation;
import krpc.client.services.SpaceCenter.Camera;
import krpc.client.services.SpaceCenter.CameraMode;
import krpc.client.services.SpaceCenter.CelestialBody;
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
	
	private Stream<Integer> stream_currentStageNumber = null;
	private Stream<Resources> stream_vesselResources = null;
	//private Stream<Parts> stream_vesselParts = null;
	private Stream<Float> stream_vesselFoodAmount = null;
	private Stream<Float> stream_vesselFoodMax = null;
	private Stream<Float> stream_vesselWaterAmount = null;
	private Stream<Float> stream_vesselWaterMax = null;
	private Stream<Float> stream_vesselOxygenAmount = null;
	private Stream<Float> stream_vesselOxygenMax = null;
	private Stream<Float> stream_gForce = null;
	private Stream<Float> stream_mach = null;
	private Stream<Float> stream_pitch = null;
	private Stream<Float> stream_heading = null;
	private Stream<Float> stream_stageLiquidFuelAmount = null;
	private Stream<Float> stream_stageLiquidFuelMax = null;
	private Stream<Float> stream_stageSolidFuelAmount = null;
	private Stream<Float> stream_stageSolidFuelMax = null;
	private Stream<Float> stream_vesselElectricChargeAmount = null;
	private Stream<Float> stream_vesselElectricChargeMax = null;
	private Stream<Float> stream_vesselMonopropellantAmount = null;
	private Stream<Float> stream_vesselMonopropellantMax = null;
	private Stream<Float> stream_currentAirDensity = null;
	private Stream<CelestialBody> stream_orbitBody = null;
	private Stream<Double> stream_surfaceReferenceFrame_speed = null;
	private Stream<Double> stream_surfaceReferenceFrame_verticalSpeed = null;
	private Stream<Double> stream_orbitalReferenceFrame_speed = null;
	private Stream<Double> stream_orbitalReferenceFrame_verticalSpeed = null;
	private Stream<VesselSituation> stream_vesselSituation = null;
	private Stream<Double> stream_altitudeAboveSurface = null;
	private Stream<Double> stream_altitudeAboveSeaLevel = null;
	private Stream<SASMode> stream_SASMode = null;
	private Stream<Double> stream_apoapsis = null;
	private Stream<Double> stream_periapsis = null;
	private Stream<Double> stream_secondsUntilApoapsis = null;
	private Stream<Double> stream_secondsUntilPeriapsis = null;

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
				
				KKIMProp.numberOfKKIMExceptions++;
				System.out.println("FAILED. Message: \"" + io_e.getMessage() + "\"");

				if (numberOfAttempts >= maxTries) {
					io_e.printStackTrace();
					System.out.println("Attempted to establish kRPC connection " + numberOfAttempts + " times. Aborting...");
					System.exit(-1);
				}

				System.out.println("Sleeping for " + KKIMProp.kkimStartupModeSleepIntervalInMilliseconds + "ms, then trying again.");
				try {
					Thread.sleep(KKIMProp.kkimStartupModeSleepIntervalInMilliseconds);
				} catch (InterruptedException i_e) {
					KKIMProp.numberOfKKIMExceptions++;
					i_e.printStackTrace();
				}
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
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Create Streams for all information needed from KSP
	 */
	public void establishAllKRPCStreams() {
		try{

			this.stream_vesselResources = this.connection.addStream(this.vessel, "getResources");
			this.stream_vesselResources.startAndWait();
			//this.stream_vesselParts = this.connection.addStream(this.vessel, "getParts");
			Resources vesselResources = this.stream_vesselResources.get();

			this.stream_vesselFoodAmount = this.connection.addStream(vesselResources, "amount", "Food");
			this.stream_vesselFoodAmount.start();
			
			this.stream_vesselFoodMax    = this.connection.addStream(vesselResources, "max", "Food");
			this.stream_vesselFoodMax.start();
			
			this.stream_vesselWaterAmount = this.connection.addStream(vesselResources, "amount", "Water");
			this.stream_vesselWaterAmount.start();
			
			this.stream_vesselWaterMax    = this.connection.addStream(vesselResources, "max", "Water");
			this.stream_vesselWaterMax.start();
			
			this.stream_vesselOxygenAmount = this.connection.addStream(vesselResources, "amount", "Oxygen");
			this.stream_vesselOxygenAmount.start();
			
			this.stream_vesselOxygenMax    = this.connection.addStream(vesselResources, "max", "Oxygen");
			this.stream_vesselOxygenMax.start();
			
			this.stream_vesselElectricChargeAmount = this.connection.addStream(vesselResources, "amount", "ElectricCharge");
			this.stream_vesselElectricChargeAmount.start();
			
			this.stream_vesselElectricChargeMax    = this.connection.addStream(vesselResources, "max", "ElectricCharge");
			this.stream_vesselElectricChargeMax.start();
			
			this.stream_vesselMonopropellantAmount = this.connection.addStream(vesselResources, "amount", "MonoPropellant");
			this.stream_vesselMonopropellantAmount.start();
			
			this.stream_vesselMonopropellantMax    = this.connection.addStream(vesselResources, "max", "MonoPropellant");
			this.stream_vesselMonopropellantMax.start();

			this.stream_gForce = this.connection.addStream(this.flight, "getGForce");
			this.stream_gForce.start();

			this.stream_mach = this.connection.addStream(this.flight, "getMach");
			this.stream_mach.start();

			this.stream_pitch = this.connection.addStream(this.flight, "getPitch");
			this.stream_pitch.start();

			this.stream_heading = this.connection.addStream(this.flight, "getHeading");
			this.stream_heading.start();

			this.stream_currentAirDensity = this.connection.addStream(this.flight, "getAtmosphereDensity");
			this.stream_currentAirDensity.start();

			this.stream_orbitBody = this.connection.addStream(this.orbit, "getBody");
			this.stream_orbitBody.addCallback((CelestialBody newOrbitBody) -> {
				System.out.println("New orbit body: " + newOrbitBody + ". TODO: Update hooks/streams that depend on orbit body.");
				//TODO Update hooks/streams that depend on orbit body
			});
			this.stream_orbitBody.start();

			this.stream_surfaceReferenceFrame_speed = this.connection.addStream(this.flight_OrbitBody_NormalReferenceFrame, "getSpeed");
			this.stream_surfaceReferenceFrame_speed.start();

			this.stream_surfaceReferenceFrame_verticalSpeed = this.connection.addStream(this.flight_OrbitBody_NormalReferenceFrame, "getVerticalSpeed");
			this.stream_surfaceReferenceFrame_verticalSpeed.start();

			this.stream_orbitalReferenceFrame_speed = this.connection.addStream(flight_OrbitBody_OrbitalReferenceFrame, "getSpeed");
			this.stream_orbitalReferenceFrame_speed.start();

			this.stream_orbitalReferenceFrame_verticalSpeed = this.connection.addStream(flight_OrbitBody_OrbitalReferenceFrame, "getVerticalSpeed");
			this.stream_orbitalReferenceFrame_verticalSpeed.start();

			this.stream_vesselSituation = this.connection.addStream(this.vessel, "getSituation");
			this.stream_vesselSituation.start();

			this.stream_altitudeAboveSurface = this.connection.addStream(this.flight, "getSurfaceAltitude");
			this.stream_altitudeAboveSurface.start();

			this.stream_altitudeAboveSeaLevel = this.connection.addStream(this.flight, "getMeanAltitude");
			this.stream_altitudeAboveSeaLevel.start();

			this.stream_SASMode = this.connection.addStream(this.control, "getSASMode");
			this.stream_SASMode.start();

			this.stream_apoapsis = this.connection.addStream(this.orbit, "getApoapsisAltitude");
			this.stream_apoapsis.start();

			this.stream_periapsis = this.connection.addStream(this.orbit, "getPeriapsisAltitude");
			this.stream_periapsis.start();

			this.stream_secondsUntilApoapsis = this.connection.addStream(this.orbit, "getTimeToApoapsis");
			this.stream_secondsUntilApoapsis.start();

			this.stream_secondsUntilPeriapsis = this.connection.addStream(this.orbit, "getTimeToPeriapsis");
			this.stream_secondsUntilPeriapsis.start();

			this.stream_currentStageNumber = this.connection.addStream(this.control, "getCurrentStage");
			this.stream_currentStageNumber.addCallback((Integer newStageNumber) -> {
				System.out.println("New stage number: " + newStageNumber);
				this.terminateStageSpecificKRPCStreams();
				this.establishStageSpecificKRPCStreams();
			});
			this.stream_currentStageNumber.startAndWait();

			this.establishStageSpecificKRPCStreams();

		} catch (StreamException | RPCException e) {
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
		}
	}

	/**
	 * Removes all Streams (which send data from KSP to KKIM)
	 */
	public void terminateAllKRPCStreams() {

		this.terminateStream(this.stream_currentStageNumber);
		this.terminateStream(this.stream_vesselResources);
		//this.stream_vesselParts.remove();
		this.terminateStream(this.stream_vesselFoodAmount);
		this.terminateStream(this.stream_vesselFoodMax);
		this.terminateStream(this.stream_vesselWaterAmount);
		this.terminateStream(this.stream_vesselWaterMax);
		this.terminateStream(this.stream_vesselOxygenAmount);
		this.terminateStream(this.stream_vesselOxygenMax);
		this.terminateStream(this.stream_vesselElectricChargeAmount);
		this.terminateStream(this.stream_vesselElectricChargeMax);
		this.terminateStream(this.stream_vesselMonopropellantAmount);
		this.terminateStream(this.stream_vesselMonopropellantMax);
		this.terminateStream(this.stream_gForce);
		this.terminateStream(this.stream_mach);
		this.terminateStream(this.stream_pitch);
		this.terminateStream(this.stream_heading);
		this.terminateStream(this.stream_currentAirDensity);
		this.terminateStream(this.stream_orbitBody);
		this.terminateStream(this.stream_surfaceReferenceFrame_speed);
		this.terminateStream(this.stream_surfaceReferenceFrame_verticalSpeed);
		this.terminateStream(this.stream_orbitalReferenceFrame_speed);
		this.terminateStream(this.stream_orbitalReferenceFrame_verticalSpeed);
		this.terminateStream(this.stream_vesselSituation);
		this.terminateStream(this.stream_altitudeAboveSurface);
		this.terminateStream(this.stream_altitudeAboveSeaLevel);
		this.terminateStream(this.stream_SASMode);
		this.terminateStream(this.stream_apoapsis);
		this.terminateStream(this.stream_periapsis);
		this.terminateStream(this.stream_secondsUntilApoapsis);
		this.terminateStream(this.stream_secondsUntilPeriapsis);

		this.terminateStageSpecificKRPCStreams();
	}

	/**
	 * Create Streams for all information that is specific to the currently active stage
	 */
	public void establishStageSpecificKRPCStreams() {
		try{

			Resources currentStageResources = this.vessel.resourcesInDecoupleStage(this.stream_currentStageNumber.get()-1, false);
			
			this.stream_stageLiquidFuelAmount = connection.addStream(currentStageResources, "amount", "LiquidFuel");
			this.stream_stageLiquidFuelAmount.start();
			
			this.stream_stageLiquidFuelMax    = connection.addStream(currentStageResources, "max", "LiquidFuel");
			this.stream_stageLiquidFuelMax.start();

			this.stream_stageSolidFuelAmount  = connection.addStream(currentStageResources, "amount", "SolidFuel");
			this.stream_stageSolidFuelAmount.start();

			this.stream_stageSolidFuelMax     = connection.addStream(currentStageResources, "max", "SolidFuel");
			this.stream_stageSolidFuelMax.start();

		} catch (StreamException | RPCException e) {
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
		}
	}

	/**
	 * Removes Streams for all information that is specific to the currently active stage
	 */
	public void terminateStageSpecificKRPCStreams() {
		this.terminateStream(this.stream_stageLiquidFuelAmount);
		this.terminateStream(this.stream_stageLiquidFuelMax);
		this.terminateStream(this.stream_stageSolidFuelAmount);
		this.terminateStream(this.stream_stageSolidFuelMax);
	}

	public void terminateStream(Stream<?> stream) {
		if(stream != null) {
			try {
				stream.remove();
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
	}

	public void closeKRPCLink() {
		
		System.out.print("Closing connection to kRPC... ");
		try {
			this.connection.close();
		} catch (IOException e) {
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
		}
		System.out.print("DONE");
	}

	public void pullInfoFromKSPIntoModel() {

		/*
		 * See: https://krpc.github.io/krpc/java/api/space-center/vessel.html
		 * 		https://krpc.github.io/krpc/java/api/space-center/resources.html#
		 * 
		 * Possible Resources:
		 *   ElectricCharge, MonoPropellant, Food, Water, Oxygen, CarbonDioxide, Waste, WasteWater, LiquidFuel, SolidFuel, Oxidizer, IntakeAir //TODO exotic fuels...
		 */
		
		try {

			long time_3 = System.currentTimeMillis();
			//FIXME Temperature temporarily disabled because it is taking 260-360 milliseconds to run through (see "TROUBLESHOOTING: (Linux) KMega Inputs not transferring to game").
			// int highestPercentTemperature = 0;
			// for (Part part : this.stream_vesselParts.get().getAll()) {
			// 	int t1 = (int) (part.getTemperature() / part.getMaxTemperature() * 100);
			// 	if (t1 > highestPercentTemperature) {
			// 		highestPercentTemperature = t1;
			// 	}
			// 	int t2 = (int) (part.getSkinTemperature() / part.getMaxSkinTemperature() * 100);
			// 	if (t2 > highestPercentTemperature) {
			// 		highestPercentTemperature = t1;
			// 	}
			// }
			// this.controlPanel.percentTemperatureHealth = 100 - highestPercentTemperature;
			
			long time_4 = System.currentTimeMillis();
			this.controlPanel.currentFood = this.stream_vesselFoodAmount.get();
			long time_5 = System.currentTimeMillis();
			this.controlPanel.maxFood = this.stream_vesselFoodMax.get();
			long time_6 = System.currentTimeMillis();
			this.controlPanel.currentWater = this.stream_vesselWaterAmount.get();
			long time_7 = System.currentTimeMillis();
			this.controlPanel.maxWater = this.stream_vesselWaterMax.get();
			long time_8 = System.currentTimeMillis();
			this.controlPanel.currentOxygen = this.stream_vesselOxygenAmount.get();
			long time_9 = System.currentTimeMillis();
			this.controlPanel.maxOxygen = this.stream_vesselOxygenMax.get();
			long time_10 = System.currentTimeMillis();
			this.controlPanel.gforce = this.stream_gForce.get();
			long time_11 = System.currentTimeMillis();
			this.controlPanel.mach = this.stream_mach.get();
			long time_12 = System.currentTimeMillis();
			this.controlPanel.pitch = this.stream_pitch.get();
			long time_13 = System.currentTimeMillis();
			this.controlPanel.heading = this.stream_heading.get();
			long time_14 = System.currentTimeMillis();
			this.controlPanel.currentLiquidFuel = this.stream_stageLiquidFuelAmount.get();
			long time_15 = System.currentTimeMillis();
			this.controlPanel.maxLiquidFuel = this.stream_stageLiquidFuelMax.get();
			long time_16 = System.currentTimeMillis();
			this.controlPanel.currentSolidFuel = this.stream_stageSolidFuelAmount.get();
			long time_17 = System.currentTimeMillis();
			this.controlPanel.maxSolidFuel = this.stream_stageSolidFuelMax.get();
			long time_18 = System.currentTimeMillis();
			this.controlPanel.currentElectricCharge = this.stream_vesselElectricChargeAmount.get();
			long time_19 = System.currentTimeMillis();
			this.controlPanel.maxElectricCharge = this.stream_vesselElectricChargeMax.get();
			long time_20 = System.currentTimeMillis();
			this.controlPanel.currentMonopropellant = this.stream_vesselMonopropellantAmount.get();
			long time_21 = System.currentTimeMillis();
			this.controlPanel.maxMonopropellant = this.stream_vesselMonopropellantMax.get();
			long time_22 = System.currentTimeMillis();
			//FIXME Use Intake part: Flow instead: https://krpc.github.io/krpc/csharp/api/space-center/parts.html#intake
			//for (Intake intake : this.vessel.getParts().getIntakes()) {	
			//}
			this.controlPanel.currentAirDensity = this.stream_currentAirDensity.get();
			long time_23 = System.currentTimeMillis();
			this.controlPanel.maxAirDensity = (float) this.stream_orbitBody.get().densityAt(0.0);
			long time_24 = System.currentTimeMillis();
			this.controlPanel.surfaceReferenceFrame_speed         = this.stream_surfaceReferenceFrame_speed.get();
			long time_25 = System.currentTimeMillis();
			this.controlPanel.surfaceReferenceFrame_verticalSpeed = this.stream_surfaceReferenceFrame_verticalSpeed.get();
			long time_26 = System.currentTimeMillis();
			this.controlPanel.orbitalReferenceFrame_speed         = this.stream_orbitalReferenceFrame_speed.get();
			long time_27 = System.currentTimeMillis();
			this.controlPanel.orbitalReferenceFrame_verticalSpeed = this.stream_orbitalReferenceFrame_verticalSpeed.get();
			long time_28 = System.currentTimeMillis();
			this.controlPanel.vesselSituation = this.stream_vesselSituation.get();
			long time_29 = System.currentTimeMillis();
			this.controlPanel.altitudeAboveSurface = this.stream_altitudeAboveSurface.get();
			long time_30 = System.currentTimeMillis();
			this.controlPanel.altitudeAboveSeaLevel = this.stream_altitudeAboveSeaLevel.get();
			long time_31 = System.currentTimeMillis();
		 	this.controlPanel.currentSASMode = this.stream_SASMode.get();
			long time_32 = System.currentTimeMillis();
			this.controlPanel.apoapsis = this.stream_apoapsis.get().floatValue();
			this.controlPanel.periapsis = this.stream_periapsis.get().floatValue();
			this.controlPanel.secondsUntilApoapsis = this.stream_secondsUntilApoapsis.get().floatValue();
			this.controlPanel.secondsUntilPeriapsis = this.stream_secondsUntilPeriapsis.get().floatValue();

			if ( KKIMProp.kkimPullInfoFromKSPIntoModelDisplayTimeDiagnosticInformation ) {
				System.out.println("------------------------------------------------------------");
				System.out.println("Parts+Temperature: " + (time_4 - time_3));
				System.out.println("currentFood: " + (time_5 - time_4));
				System.out.println("maxFood: " + (time_6 - time_5));
				System.out.println("currentWater: " + (time_7 - time_6));
				System.out.println("maxWater: " + (time_8 - time_7));
				System.out.println("currentOxygen: " + (time_9 - time_8));
				System.out.println("maxOxygen: " + (time_10 - time_9));
				System.out.println("gforce: " + (time_11 - time_10));
				System.out.println("mach: " + (time_12 - time_11));
				System.out.println("pitch: " + (time_13 - time_12));
				System.out.println("heading: " + (time_14 - time_13));
				System.out.println("currentLiquidFuel: " + (time_15 - time_14));
				System.out.println("maxLiquidFuel: " + (time_16 - time_15));
				System.out.println("currentSolidFuel: " + (time_17 - time_16));
				System.out.println("maxSolidFuel: " + (time_18 - time_17));
				System.out.println("currentElectricCharge: " + (time_19 - time_18));
				System.out.println("maxElectricCharge: " + (time_20 - time_19));
				System.out.println("currentMonopropellant: " + (time_21 - time_20));
				System.out.println("maxMonopropellant: " + (time_22 - time_21));
				System.out.println("currentAirDensity: " + (time_23 - time_22));
				System.out.println("maxAirDensity: " + (time_24 - time_23));
				System.out.println("surfaceReferenceFrame_speed: " + (time_25 - time_24));
				System.out.println("surfaceReferenceFrame_verticalSpeed: " + (time_26 - time_25));
				System.out.println("orbitalReferenceFrame_speed: " + (time_27 - time_26));
				System.out.println("orbitalReferenceFrame_verticalSpeed: " + (time_28 - time_27));
				System.out.println("vesselSituation: " + (time_29 - time_28));
				System.out.println("altitudeAboveSurface: " + (time_30 - time_29));
				System.out.println("altitudeAboveSeaLevel: " + (time_31 - time_30));
				System.out.println("currentSASMode: " + (time_32 - time_31));
				System.out.println("apoapsis: TODO");//TODO
				System.out.println("periapsis: TODO");//TODO
				System.out.println("secondsUntilApoapsis: TODO");//TODO
				System.out.println("secondsUntilPeriapsis: TODO");//TODO
			}

		} catch (StreamException | RPCException e) {
			KKIMProp.numberOfKKIMExceptions++;
			//e.printStackTrace();
			System.out.println("KRPCCommunicator:pullInfoFromKSPIntoModel(): Exception: " + e.getMessage());
		}
	}

	public GameScene fetchCurrentGameSceneInKSP() {
		GameScene scene = null;
		try{
			scene = this.kRPC.getCurrentGameScene();
		} catch (RPCException e) {
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
		}
		return scene;
	}

	public void sendInfoFromModelToKSP() {
		
		//Module A
		if (this.controlPanel.moduleA.stagingButton.getDebouncedStatus()) {
			try {
				this.control.activateNextStage();
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		//Module B
		if (this.controlPanel.moduleB.abortButton.getDebouncedStatus()) {
			try {
				this.control.activateNextStage();
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
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
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
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
			} catch (RPCException rpc_e) {
				KKIMProp.numberOfKKIMExceptions++;
				rpc_e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleD.rcsSwitch.statusChanged()) {
			try {
				this.control.setRCS(this.controlPanel.moduleD.rcsSwitch.getStatus());
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleD.lightsSwitch.statusChanged()) {
			try {
				this.control.setLights(this.controlPanel.moduleD.lightsSwitch.getStatus());
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleD.gearSwitch.statusChanged()) {
			try {
				this.control.setGear(this.controlPanel.moduleD.gearSwitch.getStatus());
				this.control.setLegs(this.controlPanel.moduleD.gearSwitch.getStatus());
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleD.mapSwitch.statusChanged()) {
			try {
				if (this.controlPanel.moduleD.mapSwitch.getStatus()) {
					this.camera.setMode(CameraMode.MAP);
				} else {
					this.camera.setMode(CameraMode.AUTOMATIC);
				}
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}

		//Module E
		if (this.controlPanel.moduleE.ag1Switch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(1);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleE.ag2Switch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(2);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleE.ag3Switch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(3);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleE.scienceSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(4);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleE.resetSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(5);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}		
	
		if (this.controlPanel.moduleE.solarSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(6);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleE.ladderSwitch.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(7);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}

		if (this.controlPanel.moduleE.fairingButton.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(8);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		if (this.controlPanel.moduleE.chuteButton.getDebouncedStatus()) {
			try {
				this.control.toggleActionGroup(9);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}

//		if (this.controlPanel.moduleE.atnvSwitch.getDebouncedStatus()) {
//			//TODO. Note: Action Group 10 is UNASSIGNED, could be used by ATNV.
//		}  
		
		//Module F
		//TODO Trim...

		
		//Multi-Module
		if (this.controlPanel.brake.statusChanged()) {
			try {
				this.control.setBrakes(this.controlPanel.brake.getStatus());
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		}
		
		//TODO Disable other controls when switching between modes...
		if (this.controlPanel.moduleE.sp3tVehicleModeSwitch.getPosition() == SP3TPosition.TOP) {//RKT
			try {
				this.control.setThrottle(this.controlPanel.throttleLever);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			
			//Rotation Mode
			try {
				this.control.setPitch(this.controlPanel.joystick_FwdBck);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			try {
				this.control.setYaw(this.controlPanel.joystick_LftRgh);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			try {
				this.control.setRoll(this.controlPanel.joystick_Twist);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			
//			//TODO Translation Mode
//			try {
//				this.control.setForward(this.controlPanel.joystick_Twist);
//			} catch (RPCException e) {
// 				KKIMProp.numberOfKKIMExceptions++;
// 				e.printStackTrace();
//			}
//			try {
//				this.control.setUp(this.controlPanel.joystick_FwdBck);
//			} catch (RPCException e) {
// 				KKIMProp.numberOfKKIMExceptions++;
// 				e.printStackTrace();
//			}
//			try {
//				this.control.setRight(this.controlPanel.joystick_LftRgh);
//			} catch (RPCException e) {
// 				KKIMProp.numberOfKKIMExceptions++;
// 				e.printStackTrace();
//			}
		} else if (this.controlPanel.moduleE.sp3tVehicleModeSwitch.getPosition() == SP3TPosition.CENTER) {//PLN
			try {
				this.control.setThrottle(this.controlPanel.throttleLever);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			try {
				this.control.setPitch(-this.controlPanel.joystick_FwdBck);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			try {
				this.control.setYaw(this.controlPanel.joystick_Twist);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			try {
				this.control.setRoll(this.controlPanel.joystick_LftRgh);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
		} else if (this.controlPanel.moduleE.sp3tVehicleModeSwitch.getPosition() == SP3TPosition.BOTTOM) {//RVR
			try {
				this.control.setWheelSteering(this.controlPanel.joystick_LftRgh);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			
			//Forward "Gear"
			try {
				this.control.setWheelThrottle(this.controlPanel.throttleLever);
			} catch (RPCException e) {
				KKIMProp.numberOfKKIMExceptions++;
				e.printStackTrace();
			}
			//TODO Reverse "Gear"
//			try {
//				this.control.setWheelThrottle(this.controlPanel.throttleLever * -1);
//			} catch (RPCException e) {
//				KKIMProp.numberOfKKIMExceptions++;
//				e.printStackTrace();
//			}
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
		} catch (RPCException e) {
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
		}
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
		} catch (RPCException e) {
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
		}
	}
}