/*
 * KMega Configuration Settings
 */
#ifndef configuration_h
#define configuration_h

#include <Arduino.h>

//Computer Interface
static const String COMMUNICATION_PORT = "COM4";
static const unsigned long COMPUTER_BAUD_RATE = 115200; //Options: (from Arduino IDE Serial Monitor)  300  1,200  2,400  4,800  9,600  19,200  38,400  57,600  74,880  115,200  230,400  250,000  500,000  1,000,000  2,000,000
static const int REFRESH_PERIOD_IN_MILLISECONDS = 25;

//KNano Interface
static const unsigned long KNANO_BAUD_RATE = COMPUTER_BAUD_RATE;
static const float STARTING_ALTITUDE = -1.0e1;

//Multiplexer
static const int MULTIPLEXER_IO_ROW_1 = 1;
static const int MULTIPLEXER_IO_ROW_2 = 2;
static const int MULTIPLEXER_IO_ROW_3 = 3;

//LED Driver Boards
static const int NUMBER_OF_LED_DRIVER_BOARDS = 4;




//Arduino Mega Pin Assignment
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
static const int PIN_JOYSTICK_AXIS_1 = A12;
static const int PIN_JOYSTICK_AXIS_2 = A13;
static const int PIN_JOYSTICK_AXIS_3 = A14;
static const int PIN_MULTI_PURPOSE_POTENTIOMETER = A15;
//0 UNASSIGNED (Computer Communication Serial Channel - RX)
//1 UNASSIGNED (Computer Communication Serial Channel - TX)
static const int PIN_MUX_S0 = 2;
static const int PIN_HEAT_LIFESUPPORT_SWITCH = 3;
static const int PIN_MUX_S1 = 4;
static const int PIN_MONOPROPELLANT_INTAKEAIR_SWITCH = 5;
static const int PIN_MUX_S2 = 6;
static const int PIN_MUX_S3 = 7;
static const int LED_DRIVER_BOARDS_LATCH = 8; 	//"LAT"
static const int LED_DRIVER_BOARDS_OVERRIDE = 9;//"/OE". Set High to disable all LEDs. Set Low otherwise.
static const int LED_DRIVER_BOARDS_CLOCK = 10;	//"CLK"
static const int LED_DRIVER_BOARDS_DATA_IN = 11;//"DIN"
static const int PIN_GLASS_COCKPIT_TL_SWITCH = 12;
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
//49 UNASSIGNED
//50 UNASSIGNED
//51 UNASSIGNED
//52 UNASSIGNED
//53 UNASSIGNED

#endif
