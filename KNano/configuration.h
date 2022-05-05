/*
 * KNano Configuration Settings
 */
#ifndef configuration_h
#define configuration_h

#include <Arduino.h>

//===========================================================================================================================================================================
//KMega Interface
static const String COMMUNICATION_PORT = "COM6";
static const int BAUD_RATE = 9600;//Options: (from Arduino IDE Serial Monitor)  300  1,200  2,400  4,800  9,600  19,200  38,400  57,600  74,880  115,200  230,400  250,000  500,000  1,000,000  2,000,000
static const int CYCLICAL_SLEEP_TIME_IN_MILLISECONDS = 5;
static const int SERIAL_READ_TIMEOUT_IN_MILLISECONDS = 10000; //The maximum amount of time kNano will wait before timing out during a serial read operation
static const byte PACKET_DELIMITER_BYTE = 0x3C; // 0x3C = '<'
static const int NUMBER_OF_PACKET_DELIMITER_BYTES = 3; //The number of consecutive packet delimiter bytes that mark the beginning of a packet
static const int ALTITUDE_PACKET_LENGTH_IN_BYTES = 13;//Length of Header + Payload. Does not include Packet Start Delimiter bytes.

static const long MINIMUM_QUIET_TIME_PRIOR_TO_ERROR_MODE_IN_MILLISECONDS = 1000;

//===========================================================================================================================================================================
static const int SEQUENTIAL_LED_TIME_IN_MILLISECONDS = 1000; //The amount of time a single LED will be turned on for before moving on to the next LED.

//===========================================================================================================================================================================
//Arduino Nano
static const int PIN_SEVENSEGMENTDISPLAY_e = 2; 			//	  aaa
static const int PIN_SEVENSEGMENTDISPLAY_d = 3;				//	 f   b
static const int PIN_SEVENSEGMENTDISPLAY_decimalPoint = 4;  //	 f   b
static const int PIN_SEVENSEGMENTDISPLAY_c = 5;				//	  ggg
static const int PIN_SEVENSEGMENTDISPLAY_g = 6;				//	 e   c
static const int PIN_SEVENSEGMENTDISPLAY_b = 7;				//	 e   c
static const int PIN_SEVENSEGMENTDISPLAY_digit3 = 8;        //	  ddd
static const int PIN_SEVENSEGMENTDISPLAY_digit2 = 9;
static const int PIN_SEVENSEGMENTDISPLAY_f = 10;
static const int PIN_SEVENSEGMENTDISPLAY_a = 11;
static const int PIN_SEVENSEGMENTDISPLAY_digit1 = 12;
//13 UNASSIGNED
//A0 UNASSIGNED
static const int PIN_ALTITUDE_m = A1;
static const int PIN_ALTITUDE_km = A2;
static const int PIN_ALTITUDE_Mm = A3;
static const int PIN_ALTITUDE_Gm = A4;
//A5 UNASSIGNED
//A6 UNASSIGNED
//A7 UNASSIGNED

//===========================================================================================================================================================================
//Seven Segment Display
static const int SEVEN_SEGMENT_DISPLAY_BRIGHTNESS = 20; // Range: 0 to 100
static const bool SEVEN_SEGMENT_DISPLAY__SHOW_LEADING_ZEROS = false;

static const String ALL_LEDs_ON = "8.8.8.";
static const String NO_BYTES_RECEIVED = "nO.b.";
static const String NO_PACKET_RECEIVED = "nO.P.";
static const String NUMBER_TOO_SMALL_TO_DISPLAY__NEGATIVE_NUMBER_ERROR = "n.n.E.";
static const String NUMBER_TOO_BIG_TO_DISPLAY__LARGE_NUMBER_ERROR = "L.n.E.";

static const int DECIMAL_OFF = -1;
static const int DECIMAL_LEFT = 2;
static const int DECIMAL_CENTER = 1;
//static const int DECIMAL_RIGHT = 0; //Note: not used


#endif
