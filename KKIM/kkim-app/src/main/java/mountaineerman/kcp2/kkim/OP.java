package mountaineerman.kcp2.kkim;

import mountaineerman.kcp2.kkim.model.ModuleID;

//Fixed values associated with the User Output parts and the OutputRefreshPacket
public enum OP {
	
//LEDs						 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	ModuleABrakeLED			(10,		11,			ModuleID.A,		-1,			-1,			"Module A Brake LED"),
//							 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	ModuleDBrakeLED			(12,		13,			ModuleID.D,		-1,			-1,			"Module D Brake LED"),
//	TODO Add remaining LEDs
//							 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	StepperLED_Fuel_Green	(116,		117,		ModuleID.I,		-1,			-1,			"Stepper LED: Fuel: Green"),
//	TODO Add remaining LEDs
	
//Stepper Motors			 firstByte	lastByte	moduleID		calibCCWLim	calibCWLim	partName
	Stepper_Gforce			(170,		171,		ModuleID.C,		 195,		3430,		"G-Force Stepper Motor");
	
	
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
