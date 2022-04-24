package mountaineerman.kcp2.kkim;

import mountaineerman.kcp2.kkim.model.ModuleID;

//Fixed values associated with the User Output parts and the OutputRefreshPacket
public enum OP {
	
//LEDs						 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	ModuleABrakeLED			(10,		11,			ModuleID.A,		-1,			-1,			"Module A Brake LED"),
//							 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	ModuleDBrakeLED			(12,		13,			ModuleID.D,		-1,			-1,			"Module D Brake LED"),
	AutoHoldLED				(14,		15,			ModuleID.D,		-1,			-1,			"Autopilot: Hold LED"),
	AutoProgradeLED			(16,		17,			ModuleID.D,		-1,			-1,			"Autopilot: Prograde LED"),
	AutoRetrogradeLED		(18,		19,			ModuleID.D,		-1,			-1,			"Autopilot: Retrograde LED"),
	AutoManeuverLED			(20,		21,			ModuleID.D,		-1,			-1,			"Autopilot: Maneuver LED"),
	AutoNormalRedLED		(72,		73,			ModuleID.D,		-1,			-1,			"Autopilot: Normal: Red LED"),
	AutoNormalBluLED		(74,		75,			ModuleID.D,		-1,			-1,			"Autopilot: Normal: Blue LED"),
	AutoAntiNormalRedLED	(76,		77,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Normal: Red LED"),
	AutoAntiNormalBluLED	(78,		79,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Normal: Blue LED"),
	AutoRadialInGrnLED		(80,		81,			ModuleID.D,		-1,			-1,			"Autopilot: Radial In: Green LED"),
	AutoRadialInBluLED		(82,		83,			ModuleID.D,		-1,			-1,			"Autopilot: Radial In: Blue LED"),
	AutoRadialOutGrnLED		(84,		85,			ModuleID.D,		-1,			-1,			"Autopilot: Radial Out: Green LED"),
	AutoRadialOutBluLED		(86,		87,			ModuleID.D,		-1,			-1,			"Autopilot: Radial Out: Blue LED"),
	AutoTargetRedLED		(88,		89,			ModuleID.D,		-1,			-1,			"Autopilot: Target: Red LED"),
	AutoTargetBluLED		(90,		91,			ModuleID.D,		-1,			-1,			"Autopilot: Target: Blue LED"),
	AutoAntiTargetRedLED	(92,		93,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Target: Red LED"),
	AutoAntiTargetBluLED	(94,		95,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Target: Blue LED"),
//				 			 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	FairingLED				(22,		23,			ModuleID.E,		-1,			-1,			"Fairing LED"),
	ParachuteLED			(24,		25,			ModuleID.E,		-1,			-1,			"Parachute LED"),
	SP3T_SpeedMode_ORB_LED	(26,		27,			ModuleID.E,		-1,			-1,			"SP3T Speed Mode Switch: ORB LED"),
	SP3T_VehicleMode_PLN_LED(28,		29,			ModuleID.E,		-1,			-1,			"SP3T Vehicle Mode Switch: PLN LED"),
	SP3T_Pitch_30degLED		(30,		31,			ModuleID.E,		-1,			-1,			"SP3T Pitch Switch: 30 degrees LED"),
//	 						 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	Sensitivity100PercentLED(32,		33,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 100% LED"),
	Sensitivity75PercentLED	(34,		35,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 75% LED"),
	Sensitivity50PercentLED	(36,		37,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 50% LED"),
	Sensitivity25PercentLED	(38,		39,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 25% LED"),
//							 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	StepperLED_Fuel_Green	(116,		117,		ModuleID.I,		-1,			-1,			"Stepper LED: Fuel: Green"),
//	TODO Add remaining LEDs
	
//Stepper Motors			 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	Stepper_Gforce			(170,		171,		ModuleID.C,		195,		3430,		"G-Force Stepper Motor");
	
	/*
	// =================================================================================================================
	// PARTS - Outputs
	// =================================================================================================================
	
	// ========================================== LED_PWM ==============================================================
	
	
	LED_PWM commsLED = new LED_PWM("commsLED", ModuleID.G);
	
	LED_PWM glassTL_LED = new LED_PWM("glassTL_LED", ModuleID.H);
	LED_PWM glassCL_LED = new LED_PWM("glassCL_LED", ModuleID.H);
	LED_PWM glassBL_LED = new LED_PWM("glassBL_LED", ModuleID.H);
	LED_PWM glassTR_LED = new LED_PWM("glassTR_LED", ModuleID.H);
	LED_PWM glassCR_LED = new LED_PWM("glassCR_LED", ModuleID.H);
	LED_PWM glassBR_LED = new LED_PWM("glassBR_LED", ModuleID.H);
	
	// ========================================== LED_PWM_RGB ==========================================================
	LED_PWM tempRedLED = new LED_PWM("heatRedLED", ModuleID.C);
	LED_PWM tempGrnLED = new LED_PWM("heatGrnLED", ModuleID.C);
	LED_PWM tempBluLED = new LED_PWM("heatBluLED", ModuleID.C);
	LED_PWM_RGB heatRGBLED = new LED_PWM_RGB("heatRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("lifeRedLED", ModuleID.C);
	tempGrnLED = new LED_PWM("lifeGrnLED", ModuleID.C);
	tempBluLED = new LED_PWM("lifeBluLED", ModuleID.C);
	LED_PWM_RGB lifeRGBLED = new LED_PWM_RGB("lifeRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("gforceRedLED", ModuleID.C);
	tempGrnLED = new LED_PWM("gforceGrnLED", ModuleID.C);
	tempBluLED = new LED_PWM("gforceBluLED", ModuleID.C);
	LED_PWM_RGB gforceRGBLED = new LED_PWM_RGB("gforceRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("machRedLED", ModuleID.G);
	tempGrnLED = new LED_PWM("machGrnLED", ModuleID.G);
	tempBluLED = new LED_PWM("machBluLED", ModuleID.G);
	LED_PWM_RGB machRGBLED = new LED_PWM_RGB("machRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("pitchRedLED", ModuleID.G);
	tempGrnLED = new LED_PWM("pitchGrnLED", ModuleID.G);
	tempBluLED = new LED_PWM("pitchBluLED", ModuleID.G);
	LED_PWM_RGB pitchRGBLED = new LED_PWM_RGB("pitchRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("headingRedLED", ModuleID.G);
	tempGrnLED = new LED_PWM("headingGrnLED", ModuleID.G);
	tempBluLED = new LED_PWM("headingBluLED", ModuleID.G);
	LED_PWM_RGB headingRGBLED = new LED_PWM_RGB("headingRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("fuelRedLED", ModuleID.I);
	tempGrnLED = new LED_PWM("fuelGrnLED", ModuleID.I);
	tempBluLED = new LED_PWM("fuelBluLED", ModuleID.I);
	LED_PWM_RGB fuelRGBLED = new LED_PWM_RGB("fuelRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("chargeRedLED", ModuleID.I);
	tempGrnLED = new LED_PWM("chargeGrnLED", ModuleID.I);
	tempBluLED = new LED_PWM("chargeBluLED", ModuleID.I);
	LED_PWM_RGB chargeRGBLED = new LED_PWM_RGB("chargeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("deltaChargeRedLED", ModuleID.I);
	tempGrnLED = new LED_PWM("deltaChargeGrnLED", ModuleID.I);
	tempBluLED = new LED_PWM("deltaChargeBluLED", ModuleID.I);
	LED_PWM_RGB deltaChargeRGBLED = new LED_PWM_RGB("deltaChargeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("mnprpRedLED", ModuleID.I);
	tempGrnLED = new LED_PWM("mnprpGrnLED", ModuleID.I);
	tempBluLED = new LED_PWM("mnprpBluLED", ModuleID.I);
	LED_PWM_RGB mnprpRGBLED = new LED_PWM_RGB("mnprpRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("intakeRedLED", ModuleID.I);
	tempGrnLED = new LED_PWM("intakeGrnLED", ModuleID.I);
	tempBluLED = new LED_PWM("intakeBluLED", ModuleID.I);
	LED_PWM_RGB intakeRGBLED = new LED_PWM_RGB("intakeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("densityRedLED", ModuleID.GT);
	tempGrnLED = new LED_PWM("densityGrnLED", ModuleID.GT);
	tempBluLED = new LED_PWM("densityBluLED", ModuleID.GT);
	LED_PWM_RGB densityRGBLED = new LED_PWM_RGB("densityRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("speedRedLED", ModuleID.GT);
	tempGrnLED = new LED_PWM("speedGrnLED", ModuleID.GT);
	tempBluLED = new LED_PWM("speedBluLED", ModuleID.GT);
	LED_PWM_RGB speedRGBLED = new LED_PWM_RGB("speedRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("vertSpeedRedLED", ModuleID.GT);
	tempGrnLED = new LED_PWM("vertSpeedGrnLED", ModuleID.GT);
	tempBluLED = new LED_PWM("vertSpeedBluLED", ModuleID.GT);
	LED_PWM_RGB vertSpeedRGBLED = new LED_PWM_RGB("vertSpeedRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	tempRedLED = new LED_PWM("radAltRedLED", ModuleID.GT);
	tempGrnLED = new LED_PWM("radAltGrnLED", ModuleID.GT);
	tempBluLED = new LED_PWM("radAltBluLED", ModuleID.GT);
	LED_PWM_RGB radAltRGBLED = new LED_PWM_RGB("radAltRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
	tempRedLED = null; tempGrnLED = null; tempBluLED = null;
	
	// ========================================== StepperMotor =========================================================
	StepperMotor heatLifeStepper = new StepperMotor("heatLifeStepper", ModuleID.C);
	StepperMotor machStepper = new StepperMotor("machStepper", ModuleID.G);
	StepperMotor pitchStepper = new StepperMotor("pitchStepper", ModuleID.G);
	StepperMotor fuelStepper = new StepperMotor("fuelStepper", ModuleID.I);
	StepperMotor chargeStepper = new StepperMotor("chargeStepper", ModuleID.I);
	StepperMotor mnprpIntakeStepper = new StepperMotor("mnprpIntakeStepper", ModuleID.I);
	StepperMotor densityStepper = new StepperMotor("densityStepper", ModuleID.GT);
	StepperMotor speedStepper = new StepperMotor("speedStepper", ModuleID.GT);
	StepperMotor vertSpeedStepper = new StepperMotor("vertSpeedStepper", ModuleID.GT);
	StepperMotor radAltStepper = new StepperMotor("radAltStepper", ModuleID.GT);
	
	// ========================================== NEMA17StepperMotor ===================================================
	StepperMotorNEMA17 headingStepper = new StepperMotorNEMA17("headingStepper", ModuleID.G);
	
	// ========================================== AltitudeGauge ========================================================
	AltitudeGauge altitudeGauge = new AltitudeGauge("altitudeGauge", ModuleID.GT);
	*/
	
	
	public final int firstByte;//See Onenote:ICD
	public final int lastByte; //See Onenote:ICD
	public final ModuleID moduleID;
	public final int calibrationCCWLimit;
	public final int calibrationCWLimit;
	public final String partName;
	
	//TODO public static final OP[] LEDS = {IP.ModuleABrakeLED, ModuleDBrakeLED, StepperLED_Fuel_Green};

	/*
	 * 				PermittedRange	MinBitSize
	 * 	LED_PWM:	0-4095			12
	 * 	Stepper:	0-3779			10			Note: NEMA Stepper Range: 0-1599
	 * 	
	 * 	MinBitSize = The minimum number of bits required to transfer the data for the part.
	 */
	
	private OP(int firstByte, int lastByte, ModuleID moduleID, int calibrationCCWLimit, int calibrationCWLimit, String partName) {
		this.firstByte = firstByte;
		this.lastByte = lastByte;
		this.moduleID = moduleID;
		this.calibrationCCWLimit = calibrationCCWLimit;
		this.calibrationCWLimit = calibrationCWLimit;
		this.partName = partName;
	}
}
