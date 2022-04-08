package mountaineerman.kcp2.kkim;

import mountaineerman.kcp2.kkim.model.ModuleID;

//Fixed values associated with the User Output parts and the OutputRefreshPacket
public enum OP {
	
//							 firstByte	lastByte	moduleID		partName
	ModuleABrakeLED			(10,		11,			ModuleID.A,		"Module A Brake LED"),
	
	ModuleDBrakeLED			(12,		13,			ModuleID.D,		"Module D Brake LED"),
	
	StepperLED_Fuel_Green	(116,		117,		ModuleID.I,		"Stepper LED: Fuel: Green");
//	TODO Add remaining outputs
	
	public final int firstByte;//See Onenote:ICD
	public final int lastByte; //See Onenote:ICD
	public final ModuleID moduleID;
	public final String partName;
	
	//TODO public static final OP[] LEDS = {IP.ModuleABrakeLED, ModuleDBrakeLED, StepperLED_Fuel_Green};

	/*
	 * 				PermittedRange	MinBitSize
	 * 	LED_PWM:	0-4095			12
	 * 	Stepper:	0-3779			10			Note: NEMA Stepper Range: 0-1599
	 * 	
	 * 	MinBitSize = The minimum number of bits required to transfer the data for the part.
	 */
	
	private OP(int firstByte, int lastByte, ModuleID moduleID, String partName) {
		this.firstByte = firstByte;
		this.lastByte = lastByte;
		this.moduleID = moduleID;
		this.partName = partName;
	}
}
