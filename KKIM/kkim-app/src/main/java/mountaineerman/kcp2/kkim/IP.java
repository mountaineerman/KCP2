package mountaineerman.kcp2.kkim;

import mountaineerman.kcp2.kkim.model.ModuleID;

//Fixed values associated with the User Input parts and the InputRefreshPacket
public enum IP {
	
//					 			firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	StagingButton				(10,		-1,			1,			-1,			-1,			-1,				-1,				ModuleID.A,		"Staging Button"),
	BrakeButton					(10,		-1,			2,			-1,			-1,			-1,				-1,				ModuleID.A,		"Brake Button"),
	AbortButton					(10,		-1,			3,			-1,			-1,			-1,				-1,				ModuleID.B,		"Abort Button"),
	TrimPitchSwitch				(10,		-1,			4,			-1,			-1,			-1,				-1,				ModuleID.B,		"Trim: Pitch Switch"),
	TrimYawSwitch				(10,		-1,			5,			-1,			-1,			-1,				-1,				ModuleID.B,		"Trim: Yaw Switch"),
	TrimRollSwitch				(10,		-1,			6,			-1,			-1,			-1,				-1,				ModuleID.B,		"Trim: Roll Switch"),
	TimeWarpUpButton			(10,		-1,			7,			-1,			-1,			-1,				-1,				ModuleID.B,		"Time Warp + Button (R)"),
	TimeWarpDownButton			(10,		-1,			8,			-1,			-1,			-1,				-1,				ModuleID.B,		"Time Warp - Button (L)"),
// 								firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	JoystickButton				(11,		-1,			1,			-1,			-1,			-1,				-1,				ModuleID.B,		"Joystick Top Button"),
	SAS_Switch					(11,		-1,			2,			-1,			-1,			-1,				-1,				ModuleID.D,		"SAS Switch"),
	RCS_Switch					(11,		-1,			3,			-1,			-1,			-1,				-1,				ModuleID.D,		"RCS Switch"),
	LightsSwitch				(11,		-1,			4,			-1,			-1,			-1,				-1,				ModuleID.D,		"Lights Switch"),
	GearSwitch					(11,		-1,			5,			-1,			-1,			-1,				-1,				ModuleID.D,		"Gear Switch"),
	BrakeSwitch					(11,		-1,			6,			-1,			-1,			-1,				-1,				ModuleID.D,		"Brake Switch"),
	MapSwitch					(11,		-1,			7,			-1,			-1,			-1,				-1,				ModuleID.D,		"Map Switch"),
	MuteSwitch					(11,		-1,			8,			-1,			-1,			-1,				-1,				ModuleID.D,		"Mute Switch"),
//								firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	AutoHoldButton				(12,		-1,			1,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Hold Button"),
	AutoProgradeButton			(12,		-1,			2,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Prograde Button"),
	AutoRetrogradeButton		(12,		-1,			3,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Retrograde Button"),
	AutoNormalButton			(12,		-1,			4,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Normal Button"),
	AutoAntiNormalButton		(12,		-1,			5,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Anti-normal Button"),
	AutoRadialInButton			(12,		-1,			6,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Radial In Button"),
	AutoRadialOutButton			(12,		-1,			7,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Radial Out Button"),
	AutoTargetButton			(12,		-1,			8,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Target Button"),
//								firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	AutoAntiTargetButton		(13,		-1,			1,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Anti-Target Button"),
	AutoManeuverButton			(13,		-1,			2,			-1,			-1,			-1,				-1,				ModuleID.D,		"Autopilot Maneuver Button"),
	Ag1Switch					(13,		-1,			3,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: Action Group 1"),
	Ag2Switch					(13,		-1,			4,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: Action Group 2"),
	Ag3Switch					(13,		-1,			5,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: Action Group 3"),
	ScienceSwitch				(13,		-1,			6,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: Science"),
	ResetSwitch					(13,		-1,			7,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: Reset"),
	SolarSwitch					(13,		-1,			8,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: Solar Panels"),
//								firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	LadderSwitch				(14,		-1,			1,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: Ladder"),
	ATNV_Switch					(14,		-1,			2,			-1,			-1,			-1,				-1,				ModuleID.E,		"MOM Toggle Switch: AutoNavigation"),
	FairingButton				(14,		-1,			3,			-1,			-1,			-1,				-1,				ModuleID.E,		"Fairing Button"),
	ChuteButton					(14,		-1,			4,			-1,			-1,			-1,				-1,				ModuleID.E,		"Parachute Button"),
	SP3T_SFC_Switch				(14,		-1,			5,			-1,			-1,			-1,				-1,				ModuleID.E,		"SP3T Switch: Speed: SFC"),
	SP3T_TGT_Switch				(14,		-1,			6,			-1,			-1,			-1,				-1,				ModuleID.E,		"SP3T Switch: Speed: TGT"),
	SP3T_RKT_Switch				(14,		-1,			7,			-1,			-1,			-1,				-1,				ModuleID.E,		"SP3T Switch: Control Mode: RKT"),
	SP3T_RVR_Switch				(14,		-1,			8,			-1,			-1,			-1,				-1,				ModuleID.E,		"SP3T Switch: Control Mode: RVR"),
//								firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	SP3T_90degSwitch			(15,		-1,			1,			-1,			-1,			-1,				-1,				ModuleID.E,		"SP3T Switch: Pitch: 90 degrees"),
	SP3T_9degSwitch				(15,		-1,			2,			-1,			-1,			-1,				-1,				ModuleID.E,		"SP3T Switch: Pitch: 9 degrees"),
	TrimPrimarySwitch			(15,		-1,			3,			-1,			-1,			-1,				-1,				ModuleID.F,		"Trim Primary Switch"),
	SP4T_AB						(15,		-1,			4,			-1,			-1,			-1,				-1,				ModuleID.F,		"SP4T Switch: Control Sensitivity: AB"),
	SP4T_CD						(15,		-1,			5,			-1,			-1,			-1,				-1,				ModuleID.F,		"SP4T Switch: Control Sensitivity: CD"),
	HeatLifeSwitch				(15,		-1,			6,			-1,			-1,			-1,				-1,				ModuleID.G,		"Heat/Life Switch"),
	GlassTL_Button				(15,		-1,			7,			-1,			-1,			-1,				-1,				ModuleID.H,		"Glass Cockpit Button - Top Left"),
	GlassCL_Button				(15,		-1,			8,			-1,			-1,			-1,				-1,				ModuleID.H,		"Glass Cockpit Button - Center Left"),
//								firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	GlassBL_Button				(16,		-1,			1,			-1,			-1,			-1,				-1,				ModuleID.H,		"Glass Cockpit Pushbutton - Bottom Left"),
	GlassTR_Button				(16,		-1,			2,			-1,			-1,			-1,				-1,				ModuleID.H,		"Glass Cockpit Pushbutton - Top Right"),
	GlassCR_Button				(16,		-1,			3,			-1,			-1,			-1,				-1,				ModuleID.H,		"Glass Cockpit Pushbutton - Center Right"),
	GlassBR_Button				(16,		-1,			4,			-1,			-1,			-1,				-1,				ModuleID.H,		"Glass Cockpit Pushbutton - Bottom Right"),
	MonopropIntakeSwitch		(16,		-1,			5,			-1,			-1,			-1,				-1,				ModuleID.I,		"Monopropellant/Intake Air Toggle Switch"),
	//Byte 16 Bit 6 unused
	//Byte 16 Bit 7 unused
	//Byte 16 Bit 8 unused
//								firstByte	lastByte	bitNumber	minCalibLim	maxCalibLim	minRescaleLim	maxRescaleLim	moduleID		partName
	AnalogInput_Throttle		(17,		18,			-1,			45,			840,		0,				1000,			ModuleID.A,		"Analog Input: Throttle"),
	AnalogInput_Joystick_FwdBck	(19,		20,			-1,			15,			1007,		-1000,			1000,			ModuleID.B,		"Analog Input: Joystick: Forward-Back"),
	AnalogInput_Joystick_LftRgh	(21,		22,			-1,			15,			1007,		-1000,			1000,			ModuleID.B,		"Analog Input: Joystick: Left-Right"),
	AnalogInput_Joystick_Twist	(23,		24,			-1,			15,			1007,		-1000,			1000,			ModuleID.B,		"Analog Input: Joystick: Twist"),
	AnalogInput_MultiPot		(25,		26,			-1,			10,			1010,		0,				1000,			ModuleID.F,		"Analog Input: Multi-Purpose Potentiometer"),
	AnalogInput_Current			(27,		28,			-1,			0,			1023,		0,				5000,			ModuleID.F,		"Analog Input: Current (mA)");

	
	
	public final int firstByte;//See Onenote:ICD 
	public final int lastByte; //See Onenote:ICD. Not applicable for Switches.
	public final int bitNumber;//See Onenote:ICD. Only applicable for Switches.
	public final int minCalibLim;//Lower physical calibration offset for raw value. Only applicable for AnalogInputs. Minimum: 0
	public final int maxCalibLim;//Upper physical calibration offset for raw value. Only applicable for AnalogInputs. Maximum: 1023
	public final int minRescaleLim;//Lower limit of re-scaled boundValue. Only applicable for AnalogInputs.
	public final int maxRescaleLim;//Upper limit of re-scaled boundValue. Only applicable for AnalogInputs.
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
	
	private IP(int firstByte, int lastByte, int bitNumber, int minCalibLim, int maxCalibLim, int minRescaleLim, int maxRescaleLim, ModuleID moduleID, String partName) {
		this.firstByte = firstByte;
		this.lastByte = lastByte;
		this.bitNumber = bitNumber;
		this.minCalibLim = minCalibLim;
		this.maxCalibLim = maxCalibLim;
		this.minRescaleLim = minRescaleLim;
		this.maxRescaleLim = maxRescaleLim;
		this.moduleID = moduleID;
		this.partName = partName;
	}
}
