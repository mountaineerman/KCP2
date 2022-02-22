/*
 * KNano Configuration Settings
 */
#ifndef configuration_h
#define configuration_h

#include <Arduino.h>

//===========================================================================================================================================================================
//KMega Interface
static const String COMMUNICATION_PORT = "COM6";
static const int BAUD_RATE = 38400;//TODO raise rate:115200;//Options: (from Arduino IDE Serial Monitor)  300  1,200  2,400  4,800  9,600  19,200  38,400  57,600  74,880  115,200  230,400  250,000  500,000  1,000,000  2,000,000
static const int REFRESH_PERIOD_IN_MILLISECONDS = 25;
static const int SERIAL_READ_TIMEOUT_IN_MILLISECONDS = 10000; //The maximum amount of time kNano will wait before timing out during a serial read operation

//===========================================================================================================================================================================
//Diagnostic Mode
static const int DIAGNOSTIC_MODE_SEQUENTIAL_LED_TIME_IN_MILLISECONDS = 1000; //In Diagnostic Mode, the amount of time a single LED will be turned on for before moving on to the next LED.

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
static const float STARTING_ALTITUDE = -1.0;
static const int SEVEN_SEGMENT_DISPLAY_BRIGHTNESS = 10; // Range: 0 to 100
static const bool SEVEN_SEGMENT_DISPLAY__SHOW_LEADING_ZEROS = false;



#endif
