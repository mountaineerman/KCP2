package mountaineerman.kcp2.kkim;

import mountaineerman.kcp2.kkim.model.ModuleID;

//Fixed values associated with the User Input parts and the InputRefreshPacket
public enum IP {
	
	//				 firstByte	lastByte	bitNumber	moduleID		partName
	StagingButton	(10,		-1,			1,			ModuleID.A,		"Staging Button"),
	BrakeButton		(10,		-1,			2,			ModuleID.A,		"Brake Button"),
	
	BrakeSwitch		(11,		-1,			6,			ModuleID.D,		"Brake Switch");
//	TODO Add remaining inputs
	
	public final int firstByte;//See Onenote:ICD 
	public final int lastByte; //See Onenote:ICD. Not applicable for Switches.
	public final int bitNumber;//See Onenote:ICD. Only applicable for Switches.
	public final ModuleID moduleID;
	public final String partName;
	
	//TODO public static final IP[] SWITCHES = {IP.StagingButton, IP.BrakeSwitch}; 

	/*
	 * 					PermittedRange	MinBitSize
	 * 	Switch: 		0-1				1
	 * 	AnalogInput: 	0-1023			10
	 * 
	 * 	MinBitSize = The minimum number of bits required to transfer the data for the part.
	 */
	
	private IP(int firstByte, int lastByte, int bitNumber, ModuleID moduleID, String partName) {
		this.firstByte = firstByte;
		this.lastByte = lastByte;
		this.bitNumber = bitNumber;
		this.moduleID = moduleID;
		this.partName = partName;
	}
}
