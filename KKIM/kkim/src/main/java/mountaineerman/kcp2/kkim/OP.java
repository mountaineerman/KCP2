package mountaineerman.kcp2.kkim;

import mountaineerman.kcp2.kkim.model.ModuleID;

//Fixed values associated with the User Output parts and the OutputRefreshPacket
public enum OP {
	
//LEDs								 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	ModuleABrakeLED					(10,		11,			ModuleID.A,		-1,			-1,			"Module A Brake LED"),
//									 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	StepperRGBLED_Heat				(-1,		-1,			ModuleID.C,		-1,			-1,			"Stepper RGB LED: Heat"),
	StepperLED_Heat_Red				(54,		55,			ModuleID.C,		-1,			-1,			"Stepper LED: Heat: Red"),
	StepperLED_Heat_Green			(56,		57,			ModuleID.C,		-1,			-1,			"Stepper LED: Heat: Green"),
	StepperLED_Heat_Blue			(58,		59,			ModuleID.C,		-1,			-1,			"Stepper LED: Heat: Blue"),
	
	StepperRGBLED_LifeSupport		(-1,		-1,			ModuleID.C,		-1,			-1,			"Stepper RGB LED: Life Support"),
	StepperLED_LifeSupport_Red		(60,		61,			ModuleID.C,		-1,			-1,			"Stepper LED: Life Support: Red"),
	StepperLED_LifeSupport_Green	(62,		63,			ModuleID.C,		-1,			-1,			"Stepper LED: Life Support: Green"),
	StepperLED_LifeSupport_Blue		(64,		65,			ModuleID.C,		-1,			-1,			"Stepper LED: Life Support: Blue"),
	
	StepperRGBLED_GForce			(-1,		-1,			ModuleID.C,		-1,			-1,			"Stepper RGB LED: G-Force"),
	StepperLED_GForce_Red			(66,		67,			ModuleID.C,		-1,			-1,			"Stepper LED: G-Force: Red"),
	StepperLED_GForce_Green			(68,		69,			ModuleID.C,		-1,			-1,			"Stepper LED: G-Force: Green"),
	StepperLED_GForce_Blue			(70,		71,			ModuleID.C,		-1,			-1,			"Stepper LED: G-Force: Blue"),
//									 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	ModuleDBrakeLED					(12,		13,			ModuleID.D,		-1,			-1,			"Module D Brake LED"),
	AutoHoldLED						(14,		15,			ModuleID.D,		-1,			-1,			"Autopilot: Hold LED"),
	AutoProgradeLED					(16,		17,			ModuleID.D,		-1,			-1,			"Autopilot: Prograde LED"),
	AutoRetrogradeLED				(18,		19,			ModuleID.D,		-1,			-1,			"Autopilot: Retrograde LED"),
	AutoManeuverLED					(20,		21,			ModuleID.D,		-1,			-1,			"Autopilot: Maneuver LED"),
	AutoNormalRedLED				(72,		73,			ModuleID.D,		-1,			-1,			"Autopilot: Normal: Red LED"),
	AutoNormalBluLED				(74,		75,			ModuleID.D,		-1,			-1,			"Autopilot: Normal: Blue LED"),
	AutoAntiNormalRedLED			(76,		77,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Normal: Red LED"),
	AutoAntiNormalBluLED			(78,		79,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Normal: Blue LED"),
	AutoRadialInGrnLED				(80,		81,			ModuleID.D,		-1,			-1,			"Autopilot: Radial In: Green LED"),
	AutoRadialInBluLED				(82,		83,			ModuleID.D,		-1,			-1,			"Autopilot: Radial In: Blue LED"),
	AutoRadialOutGrnLED				(84,		85,			ModuleID.D,		-1,			-1,			"Autopilot: Radial Out: Green LED"),
	AutoRadialOutBluLED				(86,		87,			ModuleID.D,		-1,			-1,			"Autopilot: Radial Out: Blue LED"),
	AutoTargetRedLED				(88,		89,			ModuleID.D,		-1,			-1,			"Autopilot: Target: Red LED"),
	AutoTargetBluLED				(90,		91,			ModuleID.D,		-1,			-1,			"Autopilot: Target: Blue LED"),
	AutoAntiTargetRedLED			(92,		93,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Target: Red LED"),
	AutoAntiTargetBluLED			(94,		95,			ModuleID.D,		-1,			-1,			"Autopilot: Anti-Target: Blue LED"),
//				 					 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	FairingLED						(22,		23,			ModuleID.E,		-1,			-1,			"Fairing LED"),
	ParachuteLED					(24,		25,			ModuleID.E,		-1,			-1,			"Parachute LED"),
	SP3T_SpeedMode_ORB_LED			(26,		27,			ModuleID.E,		-1,			-1,			"SP3T Speed Mode Switch: ORB LED"),
	SP3T_VehicleMode_PLN_LED		(28,		29,			ModuleID.E,		-1,			-1,			"SP3T Vehicle Mode Switch: PLN LED"),
	SP3T_Pitch_30degLED				(30,		31,			ModuleID.E,		-1,			-1,			"SP3T Pitch Switch: 30 degrees LED"),
//	 								 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	Sensitivity100PercentLED		(32,		33,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 100% LED"),
	Sensitivity75PercentLED			(34,		35,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 75% LED"),
	Sensitivity50PercentLED			(36,		37,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 50% LED"),
	Sensitivity25PercentLED			(38,		39,			ModuleID.F,		-1,			-1,			"Sensitivity Switch: 25% LED"),
//									 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	CommunicationsLED				(40,		41,			ModuleID.G,		-1,			-1,			"Communications LED"),
	
	StepperRGBLED_Mach				(-1,		-1,			ModuleID.G,		-1,			-1,			"Stepper RGB LED: Mach Number"),
	StepperLED_Mach_Red				(96,		97,			ModuleID.G,		-1,			-1,			"Stepper LED: Mach Number: Red"),
	StepperLED_Mach_Green			(98,		99,			ModuleID.G,		-1,			-1,			"Stepper LED: Mach Number: Green"),
	StepperLED_Mach_Blue			(100,		101,		ModuleID.G,		-1,			-1,			"Stepper LED: Mach Number: Blue"),
	
	StepperRGBLED_Pitch				(-1,		-1,			ModuleID.G,		-1,			-1,			"Stepper RGB LED: Pitch"),
	StepperLED_Pitch_Red			(102,		103,		ModuleID.G,		-1,			-1,			"Stepper LED: Pitch: Red"),
	StepperLED_Pitch_Green			(104,		105,		ModuleID.G,		-1,			-1,			"Stepper LED: Pitch: Green"),
	StepperLED_Pitch_Blue			(106,		107,		ModuleID.G,		-1,			-1,			"Stepper LED: Pitch: Blue"),
	
	StepperRGBLED_Heading			(-1,		-1,			ModuleID.G,		-1,			-1,			"Stepper RGB LED: Heading"),
	StepperLED_Heading_Red			(108,		109,		ModuleID.G,		-1,			-1,			"Stepper LED: Heading: Red"),
	StepperLED_Heading_Green		(110,		111,		ModuleID.G,		-1,			-1,			"Stepper LED: Heading: Green"),
	StepperLED_Heading_Blue			(112,		113,		ModuleID.G,		-1,			-1,			"Stepper LED: Heading: Blue"),
//									 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	GlassCockpitLED_TL				(42,		43,			ModuleID.H,		-1,			-1,			"Glass Cockpit LED: Top-Left (white)"),
	GlassCockpitLED_CL				(44,		45,			ModuleID.H,		-1,			-1,			"Glass Cockpit LED: Center-Left (yellow)"),
	GlassCockpitLED_BL				(46,		47,			ModuleID.H,		-1,			-1,			"Glass Cockpit LED: Bottom-Left (white)"),
	GlassCockpitLED_TR				(48,		49,			ModuleID.H,		-1,			-1,			"Glass Cockpit LED: Top-Right (white)"),
	GlassCockpitLED_CR				(50,		51,			ModuleID.H,		-1,			-1,			"Glass Cockpit LED: Center-Right (yellow)"),
	GlassCockpitLED_BR				(52,		53,			ModuleID.H,		-1,			-1,			"Glass Cockpit LED: Bottom-Right (white)"),
//									 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	StepperRGBLED_Fuel				(-1,		-1,			ModuleID.I,		-1,			-1,			"Stepper RGB LED: Fuel"),
	StepperLED_Fuel_Red				(114,		115,		ModuleID.I,		-1,			-1,			"Stepper LED: Fuel: Red"),
	StepperLED_Fuel_Green			(116,		117,		ModuleID.I,		-1,			-1,			"Stepper LED: Fuel: Green"),
	StepperLED_Fuel_Blue			(118,		119,		ModuleID.I,		-1,			-1,			"Stepper LED: Fuel: Blue"),
	
	StepperRGBLED_Charge			(-1,		-1,			ModuleID.I,		-1,			-1,			"Stepper RGB LED: Charge"),
	StepperLED_Charge_Red			(120,		121,		ModuleID.I,		-1,			-1,			"Stepper LED: Charge: Red"),
	StepperLED_Charge_Green			(122,		123,		ModuleID.I,		-1,			-1,			"Stepper LED: Charge: Green"),
	StepperLED_Charge_Blue			(124,		125,		ModuleID.I,		-1,			-1,			"Stepper LED: Charge: Blue"),
	
	RGBLED_deltaCharge				(-1,		-1,			ModuleID.I,		-1,			-1,			"RGB LED: delta Charge"),
	DeltaChargeLED_Red				(126,		127,		ModuleID.I,		-1,			-1,			"LED: delta Charge: Red"),
	DeltaChargeLED_Green			(128,		129,		ModuleID.I,		-1,			-1,			"LED: delta Charge: Green"),
	DeltaChargeLED_Blue				(130,		131,		ModuleID.I,		-1,			-1,			"LED: delta Charge: Blue"),
	
	StepperRGBLED_Monopropellant	(-1,		-1,			ModuleID.I,		-1,			-1,			"Stepper RGB LED: Monopropellant"),
	StepperLED_Monopropellant_Red	(132,		133,		ModuleID.I,		-1,			-1,			"Stepper LED: Monopropellant: Red"),
	StepperLED_Monopropellant_Green	(134,		135,		ModuleID.I,		-1,			-1,			"Stepper LED: Monopropellant: Green"),
	StepperLED_Monopropellant_Blue	(136,		137,		ModuleID.I,		-1,			-1,			"Stepper LED: Monopropellant: Blue"),
	
	StepperRGBLED_IntakeAir			(-1,		-1,			ModuleID.I,		-1,			-1,			"Stepper RGB LED: Intake Air"),
	StepperLED_IntakeAir_Red		(138,		139,		ModuleID.I,		-1,			-1,			"Stepper LED: Intake Air: Red"),
	StepperLED_IntakeAir_Green		(140,		141,		ModuleID.I,		-1,			-1,			"Stepper LED: Intake Air: Green"),
	StepperLED_IntakeAir_Blue		(142,		143,		ModuleID.I,		-1,			-1,			"Stepper LED: Intake Air: Blue"),
//									 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	StepperRGBLED_AirDensity		(-1,		-1,			ModuleID.GT,	-1,			-1,			"Stepper RGB LED: Air Density"),
	StepperLED_AirDensity_Red		(144,		145,		ModuleID.GT,	-1,			-1,			"Stepper LED: Air Density: Red"),
	StepperLED_AirDensity_Green		(146,		147,		ModuleID.GT,	-1,			-1,			"Stepper LED: Air Density: Green"),
	StepperLED_AirDensity_Blue		(148,		149,		ModuleID.GT,	-1,			-1,			"Stepper LED: Air Density: Blue"),
	
	StepperRGBLED_Speed				(-1,		-1,			ModuleID.GT,	-1,			-1,			"Stepper RGB LED: Speed"),
	StepperLED_Speed_Red			(150,		151,		ModuleID.GT,	-1,			-1,			"Stepper LED: Speed: Red"),
	StepperLED_Speed_Green			(152,		153,		ModuleID.GT,	-1,			-1,			"Stepper LED: Speed: Green"),
	StepperLED_Speed_Blue			(154,		155,		ModuleID.GT,	-1,			-1,			"Stepper LED: Speed: Blue"),
	
	StepperRGBLED_VerticalSpeed		(-1,		-1,			ModuleID.GT,	-1,			-1,			"Stepper RGB LED: Vertical Speed"),
	StepperLED_VerticalSpeed_Red	(156,		157,		ModuleID.GT,	-1,			-1,			"Stepper LED: Vertical Speed: Red"),
	StepperLED_VerticalSpeed_Green	(158,		159,		ModuleID.GT,	-1,			-1,			"Stepper LED: Vertical Speed: Green"),
	StepperLED_VerticalSpeed_Blue	(160,		161,		ModuleID.GT,	-1,			-1,			"Stepper LED: Vertical Speed: Blue"),
	
	StepperRGBLED_RadarAltitude		(-1,		-1,			ModuleID.GT,	-1,			-1,			"Stepper RGB LED: Radar Altitude"),
	StepperLED_RadarAltitude_Red	(162,		163,		ModuleID.GT,	-1,			-1,			"Stepper LED: Radar Altitude: Red"),
	StepperLED_RadarAltitude_Green	(164,		165,		ModuleID.GT,	-1,			-1,			"Stepper LED: Radar Altitude: Green"),
	StepperLED_RadarAltitude_Blue	(166,		167,		ModuleID.GT,	-1,			-1,			"Stepper LED: Radar Altitude: Blue"),

//Stepper Motors					 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	Stepper_HeatLife				(168,		169,		ModuleID.C,		120,		3722,		"Heat/Life Support Stepper Motor"),
	Stepper_Gforce					(170,		171,		ModuleID.C,		224,		3470,		"G-Force Stepper Motor"),

	Stepper_Mach					(172,		173,		ModuleID.G,		0,			3219,		"Mach Number Stepper Motor"), //Note: calibCWLim is for Mach 24
	Stepper_Pitch					(174,		175,		ModuleID.G,		1210,		3364,		"Pitch Stepper Motor"),
	Stepper_Heading					(176,		177,		ModuleID.G,		-1,			-1,			"Heading NEMA17 Stepper Motor"),

	Stepper_Fuel					(178,		179,		ModuleID.I,		80,			3760,		"Fuel Stepper Motor"),
	Stepper_Charge					(180,		181,		ModuleID.I,		55,			3660,		"Charge Stepper Motor"),
	Stepper_MonopropellantIntake	(182,		183,		ModuleID.I,		80,			3680,		"Monopropellant/Intake Air Stepper Motor"),

	Stepper_AirDensity				(184,		185,		ModuleID.GT,	55,			3660,		"AirDensity Stepper Motor"),
	Stepper_Speed					(186,		187,		ModuleID.GT,	-1,			-1,			"Speed Stepper Motor"),//See ControlPanel for "Speed calibration settings"
	Stepper_VerticalSpeed			(188,		189,		ModuleID.GT,	-1,			-1,			"Vertical Speed Stepper Motor"),//See ControlPanel for "Vertical Speed calibration settings"
	Stepper_RadarAltitude			(190,		191,		ModuleID.GT,	-1,			-1,			"Radar Altitude Stepper Motor"),//See ControlPanel for "Radar Altitude calibration settings"

//Altitude
	Altitude						(192,		195,		ModuleID.GT,	-1,			-1,			"Altitude (float)");


	
	
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
