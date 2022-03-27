/*
 * KMega Configuration Settings
 */
#ifndef configuration_h
#define configuration_h

#include <Arduino.h>

//===========================================================================================================================================================================
//Computer Interface
static const String COMMUNICATION_PORT = "COM4";
static const unsigned long COMPUTER_BAUD_RATE = 38400;//TODO raise rate://115200; //Options: (from Arduino IDE Serial Monitor)  300  1,200  2,400  4,800  9,600  19,200  38,400  57,600  74,880  115,200  230,400  250,000  500,000  1,000,000  2,000,000
static const int REFRESH_PERIOD_IN_MILLISECONDS = 10;
static const int SERIAL_READ_TIMEOUT_IN_MILLISECONDS = 10000; //The maximum amount of time kMega will wait before timing out during a serial read operation

static const byte PACKET_DELIMITER_BYTE = 0x3C; // 0x3C = '<'
static const int NUMBER_OF_PACKET_DELIMITER_BYTES = 3; //The number of consecutive packet delimiter bytes that mark the beginning of a packet
static const int OUTPUT_REFRESH_PACKET_LENGTH_IN_BYTES = 195;//Length of Header + Payload. Does not include Packet Start Delimiter bytes.
static const int INPUT_REFRESH_PACKET_LENGTH_IN_BYTES = 31;  //Length of Delimiter + Header + Payload.

static const int MAX_TALLY_TIME_FOR_DIAGNOSTICS_IN_MILLISECONDS = 1000;

//===========================================================================================================================================================================
//KNano Interface
static const unsigned long KNANO_BAUD_RATE = COMPUTER_BAUD_RATE;
static const float STARTING_ALTITUDE = -1.0e1;

//===========================================================================================================================================================================
//Multiplexer
static const int MULTIPLEXER_IO_ROW_1 = 1;
static const int MULTIPLEXER_IO_ROW_2 = 2;
static const int MULTIPLEXER_IO_ROW_3 = 3;

//ROW 1 (Input Pullup)
// 0 UNASSIGNED
static const int PIN_MUX_BRAKE_BUTTON = 1;
static const int PIN_MUX_JOYSTICK_BUTTON = 2;
static const int PIN_MUX_BRAKE_SWITCH = 3;
static const int PIN_MUX_AUTOPILOT_HOLD_BUTTON = 4;
static const int PIN_MUX_AUTOPILOT_PROGRADE_BUTTON = 5;
static const int PIN_MUX_AUTOPILOT_RETROGRADE_BUTTON = 6;
static const int PIN_MUX_AUTOPILOT_NORMAL_BUTTON = 7;
static const int PIN_MUX_AUTOPILOT_ANTINORMAL_BUTTON = 8;
static const int PIN_MUX_AUTOPILOT_RADIALIN_BUTTON = 9;
static const int PIN_MUX_AUTOPILOT_RADIALOUT_BUTTON = 10;
static const int PIN_MUX_AUTOPILOT_ANTITARGET_BUTTON = 11;
static const int PIN_MUX_AUTOPILOT_MANEUVER = 12;
static const int PIN_MUX_AUTOPILOT_TARGET_BUTTON = 13;
static const int PIN_MUX_FAIRING_BUTTON = 14;
static const int PIN_MUX_CHUTE_BUTTON = 15;

//ROW 2 (Input)
static const int PIN_MUX_STAGING_BUTTON = 0;
static const int PIN_MUX_ABORT_BUTTON = 1;
static const int PIN_MUX_PITCH_TRIM_SWITCH = 2;
static const int PIN_MUX_YAW_TRIM_SWITCH = 3;
static const int PIN_MUX_ROLL_TRIM_SWITCH = 4;
static const int PIN_MUX_SAS_SWITCH = 5;
static const int PIN_MUX_RCS_SWITCH = 6;
static const int PIN_MUX_LIGHTS_SWITCH = 7;
static const int PIN_MUX_GEAR_SWITCH = 8;
static const int PIN_MUX_MAP_SWITCH = 9;
static const int PIN_MUX_MUTE_SWITCH = 10;
// 11 UNASSIGNED
// 12 UNASSIGNED
static const int PIN_MUX_SFC_SWITCH = 13;
static const int PIN_MUX_TGT_SWITCH = 14;
static const int PIN_MUX_RKT_SWITCH = 15;

//ROW 3 (Input)
static const int PIN_MUX_RVR_SWITCH = 0;
static const int PIN_MUX_90_DEG_SWITCH = 1;
static const int PIN_MUX_9_DEG_SWITCH = 2;
static const int PIN_MUX_TRIM_SWITCH = 3;
static const int PIN_MUX_ACTION_GROUP_1_SWITCH = 4;
static const int PIN_MUX_ACTION_GROUP_2_SWITCH = 5;
static const int PIN_MUX_ACTION_GROUP_3_SWITCH = 6;
static const int PIN_MUX_SCIENCE_SWITCH = 7;
static const int PIN_MUX_RESET_SWITCH = 8;
static const int PIN_MUX_SOLAR_SWITCH = 9;
static const int PIN_MUX_LADDER_SWITCH = 10;
static const int PIN_MUX_AUTONAVIGATION_SWITCH = 11;
// 12 UNASSIGNED
// 13 UNASSIGNED
// 14 UNASSIGNED
// 15 UNASSIGNED

//===========================================================================================================================================================================
//LED Driver Boards
static const int NUMBER_OF_LED_DRIVER_BOARDS = 4;
static const int PWM_LED_MINIMUM = 0;
static const int PWM_LED_MAXIMUM = 1500;//4095;

static const int PIN_LEDDB_BRAKE_LED_MODULE_A = 48;				//	(Pin 0)		[LED Board 1]
static const int PIN_LEDDB_BRAKE_LED_MODULE_D = 49;				//	(Pin 1)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_HOLD = 50;				//	(Pin 2)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_PROGRADE = 51;			//	(Pin 3)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_RETROGRADE = 52;		//	(Pin 4)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_NORMAL_RED = 53;		//	(Pin 5)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_NORMAL_BLU = 54;		//	(Pin 6)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_ANTINORMAL_RED = 55;	//	(Pin 7)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_ANTINORMAL_BLUE = 56;	//	(Pin 8)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_RADIALIN_GRN = 57;		//	(Pin 9)		[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_RADIALIN_BLU = 58;		//	(Pin 10)	[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_RADIALOUT_GRN = 59;	//	(Pin 11)	[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_RADIALOUT_BLU = 60;	//	(Pin 12)	[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_TARGET_RED = 61;		//	(Pin 13)	[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_TARGET_BLU = 62;		//	(Pin 14)	[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_ANTITARGET_RED = 63;	//	(Pin 15)	[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_ANTITARGET_BLU = 64;	//	(Pin 16)	[LED Board 1]
static const int PIN_LEDDB_AUTOPILOT_LED_MANEUVER = 65;			//	(Pin 17)	[LED Board 1]
static const int PIN_LEDDB_FUEL_RGBLED_RED = 66;				//	(Pin 18)	[LED Board 1]
static const int PIN_LEDDB_FUEL_RGBLED_GRN = 67;				//	(Pin 19)	[LED Board 1]
static const int PIN_LEDDB_FUEL_RGBLED_BLU = 68;				//	(Pin 20)	[LED Board 1]
static const int PIN_LEDDB_CHARGE_RGBLED_RED = 69;				//	(Pin 21)	[LED Board 1]
static const int PIN_LEDDB_CHARGE_RGBLED_GRN = 70;				//	(Pin 22)	[LED Board 1]
static const int PIN_LEDDB_CHARGE_RGBLED_BLU = 71;				//	(Pin 23)	[LED Board 1]

static const int PIN_LEDDB_DELTA_CHARGE_RGBLED_RED = 0;			//	(Pin 0)		[LED Board 2]
static const int PIN_LEDDB_DELTA_CHARGE_RGBLED_GRN = 1;			//	(Pin 1)		[LED Board 2]
static const int PIN_LEDDB_DELTA_CHARGE_RGBLED_BLU = 2;			//	(Pin 2)		[LED Board 2]
static const int PIN_LEDDB_MONOPROPELLANT_RGBLED_RED = 3;		//	(Pin 3)		[LED Board 2]
static const int PIN_LEDDB_MONOPROPELLANT_RGBLED_GRN = 4;		//	(Pin 4)		[LED Board 2]
static const int PIN_LEDDB_MONOPROPELLANT_RGBLED_BLU = 5;		//	(Pin 5)		[LED Board 2]
static const int PIN_LEDDB_HEAT_RGBLED_RED = 30;				//	(Pin 6)		[LED Board 2]
static const int PIN_LEDDB_HEAT_RGBLED_GRN = 31;				//	(Pin 7)		[LED Board 2]
static const int PIN_LEDDB_HEAT_RGBLED_BLU = 32;				//	(Pin 8)		[LED Board 2]
static const int PIN_LEDDB_LIFE_SUPPORT_RGBLED_RED = 33;		//	(Pin 9)		[LED Board 2]
static const int PIN_LEDDB_LIFE_SUPPORT_RGBLED_GRN = 34;		//	(Pin 10)	[LED Board 2]
static const int PIN_LEDDB_LIFE_SUPPORT_RGBLED_BLU = 35;		//	(Pin 11)	[LED Board 2]
static const int PIN_LEDDB_GFORCE_RGBLED_RED = 36;				//	(Pin 12)	[LED Board 2]
static const int PIN_LEDDB_GFORCE_RGBLED_GRN = 37;				//	(Pin 13)	[LED Board 2]
static const int PIN_LEDDB_GFORCE_RGBLED_BLU = 38;				//	(Pin 14)	[LED Board 2]
static const int PIN_LEDDB_MACH_RGBLED_RED = 39;				//	(Pin 15)	[LED Board 2]
static const int PIN_LEDDB_MACH_RGBLED_GRN = 40;				//	(Pin 16)	[LED Board 2]
static const int PIN_LEDDB_MACH_RGBLED_BLU = 41;				//	(Pin 17)	[LED Board 2]
static const int PIN_LEDDB_PITCH_RGBLED_RED = 42;				//	(Pin 18)	[LED Board 2]
static const int PIN_LEDDB_PITCH_RGBLED_GRN = 43;				//	(Pin 19)	[LED Board 2]
static const int PIN_LEDDB_PITCH_RGBLED_BLU = 44;				//	(Pin 20)	[LED Board 2]
static const int PIN_LEDDB_HEADING_RGBLED_RED = 45;				//	(Pin 21)	[LED Board 2]
static const int PIN_LEDDB_HEADING_RGBLED_GRN = 46;				//	(Pin 22)	[LED Board 2]
static const int PIN_LEDDB_HEADING_RGBLED_BLU = 47;				//	(Pin 23)	[LED Board 2]

static const int PIN_LEDDB_GLASS_COCKPIT_TL = 24;				//	(Pin 0)		[LED Board 3]
static const int PIN_LEDDB_GLASS_COCKPIT_CL = 25;				//	(Pin 1)		[LED Board 3]
static const int PIN_LEDDB_GLASS_COCKPIT_BL = 26;				//	(Pin 2)		[LED Board 3]
static const int PIN_LEDDB_GLASS_COCKPIT_TR = 27;				//	(Pin 3)		[LED Board 3]
static const int PIN_LEDDB_GLASS_COCKPIT_CR = 28;				//	(Pin 4)		[LED Board 3]
static const int PIN_LEDDB_GLASS_COCKPIT_BR = 29;				//	(Pin 5)		[LED Board 3]
static const int PIN_LEDDB_ORB_LED = 6;							//	(Pin 6)		[LED Board 3]
static const int PIN_LEDDB_PLN_LED = 7;							//	(Pin 7)		[LED Board 3]
static const int PIN_LEDDB_30_DEG_LED = 8;						//	(Pin 8)		[LED Board 3]
static const int PIN_LEDDB_FAIRING_LED = 9;						//	(Pin 9)		[LED Board 3]
static const int PIN_LEDDB_CHUTE_LED = 10;						//	(Pin 10)	[LED Board 3]
static const int PIN_LEDDB_TWIST_SWITCH_100 = 11;				//	(Pin 11)	[LED Board 3]
static const int PIN_LEDDB_TWIST_SWITCH_75 = 12;				//	(Pin 12)	[LED Board 3]
static const int PIN_LEDDB_TWIST_SWITCH_50 = 13;				//	(Pin 13)	[LED Board 3]
static const int PIN_LEDDB_TWIST_SWITCH_25 = 14;				//	(Pin 14)	[LED Board 3]
static const int PIN_LEDDB_COMMS_LED = 15;						//	(Pin 15)	[LED Board 3]
static const int PIN_LEDDB_INTAKE_RGBLED_RED = 16;				//	(Pin 16)	[LED Board 3]
static const int PIN_LEDDB_INTAKE_RGBLED_GRN = 17;				//	(Pin 17)	[LED Board 3]
static const int PIN_LEDDB_INTAKE_RGBLED_BLU = 18;				//	(Pin 18)	[LED Board 3]
//19 UNASSIGNED													//	(Pin 19)	[LED Board 3]
//20 UNASSIGNED													//	(Pin 20)	[LED Board 3]
//21 UNASSIGNED													//	(Pin 21)	[LED Board 3]
//22 UNASSIGNED													//	(Pin 22)	[LED Board 3]
//23 UNASSIGNED													//	(Pin 23)	[LED Board 3]

static const int PIN_LEDDB_DENSITY_RGBLED_RED = 72;				//	(Pin 0)		[LED Board 4]
static const int PIN_LEDDB_DENSITY_RGBLED_GRN = 73;				//	(Pin 1)		[LED Board 4]
static const int PIN_LEDDB_DENSITY_RGBLED_BLU = 74;				//	(Pin 2)		[LED Board 4]
static const int PIN_LEDDB_SPEED_RGBLED_RED = 75;				//	(Pin 3)		[LED Board 4]
static const int PIN_LEDDB_SPEED_RGBLED_GRN = 76;				//	(Pin 4)		[LED Board 4]
static const int PIN_LEDDB_SPEED_RGBLED_BLU = 77;				//	(Pin 5)		[LED Board 4]
static const int PIN_LEDDB_VERTSPEED_RGBLED_RED = 78;			//	(Pin 6)		[LED Board 4]
static const int PIN_LEDDB_VERTSPEED_RGBLED_GRN = 79;			//	(Pin 7)		[LED Board 4]
static const int PIN_LEDDB_VERTSPEED_RGBLED_BLU = 80;			//	(Pin 8)		[LED Board 4]
static const int PIN_LEDDB_RADARALT_RGBLED_RED = 81;			//	(Pin 9)		[LED Board 4]
static const int PIN_LEDDB_RADARALT_RGBLED_GRN = 82;			//	(Pin 10)	[LED Board 4]
static const int PIN_LEDDB_RADARALT_RGBLED_BLU = 83;			//	(Pin 11)	[LED Board 4]
//84 UNASSIGNED													//	(Pin 12)	[LED Board 4]
//85 UNASSIGNED													//	(Pin 13)	[LED Board 4]
//86 UNASSIGNED													//	(Pin 14)	[LED Board 4]
//87 UNASSIGNED													//	(Pin 15)	[LED Board 4]
//88 UNASSIGNED													//	(Pin 16)	[LED Board 4]
//89 UNASSIGNED													//	(Pin 17)	[LED Board 4]
//90 UNASSIGNED													//	(Pin 18)	[LED Board 4]
//91 UNASSIGNED													//	(Pin 19)	[LED Board 4]
//92 UNASSIGNED													//	(Pin 20)	[LED Board 4]
//93 UNASSIGNED													//	(Pin 21)	[LED Board 4]
//94 UNASSIGNED													//	(Pin 22)	[LED Board 4]
//95 UNASSIGNED													//	(Pin 23)	[LED Board 4]

//===========================================================================================================================================================================
//Stepper Motors
static const float STEPPER_MAX_SPEED = 4000.0; //Maximum Permitted Speed (steps per second). The maximum speed achievable depends on your processor and clock speed.
static const float STEPPER_MAX_ACCELERATION = 8000.0; //Maximum Permitted Acceleration/Deceleration Rate (steps per second squared). Must be > 0.0.
static const long STEPPER_CCW_LIMIT = 0;
static const long STEPPER_CW_LIMIT = 3779;

//===========================================================================================================================================================================
//Heading Gauge NEMA17 Stepper Motor
static const float NEMA17_MAX_SPEED = 1000.0; //Maximum Permitted Speed (steps per second). The maximum speed achievable depends on your processor and clock speed.
static const float NEMA17_MAX_ACCELERATION = 200.0; //Maximum Permitted Acceleration/Deceleration Rate (steps per second squared). Must be > 0.0.
static const long NEMA17_CCW_LIMIT = 0;
static const long NEMA17_CW_LIMIT = 1599;

//===========================================================================================================================================================================
//Control Panel
static const int DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS = 1000; //In Diagnostic Mode, the amount of time a single LED will be turned on for before moving on to the next LED.

//===========================================================================================================================================================================
//Arduino Mega
static const int ANALOG_INPUT_MAXIMUM = 1023;

static const int PIN_MUX_IO_1 = A0;
static const int PIN_MUX_IO_2 = A1;
static const int PIN_MUX_IO_3 = A2;
//A3 UNASSIGNED
//A4 UNASSIGNED
//A5 UNASSIGNED
//A6 UNASSIGNED
//A7 UNASSIGNED
//A8 UNASSIGNED
//A9 UNASSIGNED
static const int PIN_CURRENT_SENSOR = A10;
static const int PIN_THROTTLE = A11;
static const int PIN_JOYSTICK_PITCH = A12;
static const int PIN_JOYSTICK_YAW = A13;
static const int PIN_JOYSTICK_ROLL = A14;
static const int PIN_MULTI_PURPOSE_POTENTIOMETER = A15;
//0 UNASSIGNED (Computer Communication Serial Channel - RX)
//1 UNASSIGNED (Computer Communication Serial Channel - TX)
static const int PIN_MUX_S0 = 2;
static const int PIN_HEAT_LIFESUPPORT_SWITCH = 3;
static const int PIN_MUX_S1 = 4;
static const int PIN_MONOPROPELLANT_INTAKEAIR_SWITCH = 5;
static const int PIN_MUX_S2 = 6;
static const int PIN_MUX_S3 = 7;
static const int PIN_LED_DRIVER_BOARDS_LATCH = 8; 	//"LAT"
static const int PIN_LED_DRIVER_BOARDS_OVERRIDE = 9;//"/OE". Set High to disable all LEDs. Set Low otherwise.
static const int PIN_LED_DRIVER_BOARDS_CLOCK = 10;	//"CLK"
static const int PIN_LED_DRIVER_BOARDS_DATA_IN = 11;//"DIN"
//12 UNASSIGNED. Appears faulty...
static const int PIN_GLASS_COCKPIT_CL_SWITCH = 13;
static const int PIN_GLASS_COCKPIT_BL_SWITCH = 14;
static const int PIN_GLASS_COCKPIT_TR_SWITCH = 15;
static const int PIN_GLASS_COCKPIT_CR_SWITCH = 16;
static const int PIN_GLASS_COCKPIT_BR_SWITCH = 17;
static const int PIN_ARDUINO_NANO_RX = 18;
static const int PIN_ARDUINO_NANO_TX = 19;
static const int PIN_TIME_WARP_DOWN = 20;
static const int PIN_TIME_WARP_UP = 21;
static const int PIN_VID6606_1_FREQUENCY_HEATLIFE = 22;
static const int PIN_VID6606_1_DIRECTION_HEATLIFE = 23;
static const int PIN_VID6606_1_FREQUENCY_GFORCE = 24;
static const int PIN_VID6606_1_DIRECTION_GFORCE = 25;
static const int PIN_VID6606_1_FREQUENCY_MACH = 26;
static const int PIN_VID6606_1_DIRECTION_MACH = 27;
static const int PIN_VID6606_1_FREQUENCY_PITCH = 28;
static const int PIN_VID6606_1_DIRECTION_PITCH = 29;
static const int PIN_VID6606_2_FREQUENCY_FUEL = 30;
static const int PIN_VID6606_2_DIRECTION_FUEL = 31;
static const int PIN_VID6606_2_FREQUENCY_CHARGE = 32;
static const int PIN_VID6606_2_DIRECTION_CHARGE = 33;
static const int PIN_VID6606_2_FREQUENCY_MNPINT = 34;
static const int PIN_VID6606_2_DIRECTION_MNPINT = 35;
static const int PIN_VID6606_3_FREQUENCY_DENSITY = 36;
static const int PIN_VID6606_3_DIRECTION_DENSITY = 37;
static const int PIN_VID6606_3_FREQUENCY_SPEED = 38;
static const int PIN_VID6606_3_DIRECTION_SPEED = 39;
static const int PIN_VID6606_3_FREQUENCY_VERTICALSPEED = 40;
static const int PIN_VID6606_3_DIRECTION_VERTICALSPEED = 41;
static const int PIN_VID6606_3_FREQUENCY_RADARALTITUDE = 42;
static const int PIN_VID6606_3_DIRECTION_RADARALTITUDE = 43;
static const int PIN_EASYDRIVER_SLP = 44;
static const int PIN_EASYDRIVER_MS1 = 45;
static const int PIN_EASYDRIVER_MS2 = 46;
static const int PIN_EASYDRIVER_STEP = 47;
static const int PIN_EASYDRIVER_DIR = 48;
static const int PIN_4POS_SWITCH_AB = 49;
static const int PIN_4POS_SWITCH_CD = 50;
//51 UNASSIGNED
static const int PIN_GLASS_COCKPIT_TL_SWITCH = 52;
//53 UNASSIGNED

#endif
