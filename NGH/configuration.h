/*
 * NGH Configuration Settings
 */
#ifndef configuration_h
#define configuration_h

#include <Arduino.h>

//===========================================================================================================================================================================
//KMega Interface
static const String COMMUNICATION_PORT = "COM6";
static const int BAUD_RATE = 9600;//Options: (from Arduino IDE Serial Monitor)  300  1,200  2,400  4,800  9,600  19,200  38,400  57,600  74,880  115,200  230,400  250,000  500,000  1,000,000  2,000,000
static const int CYCLICAL_SLEEP_TIME_IN_MILLISECONDS = 5; //TODO confirm needed
static const int SERIAL_READ_TIMEOUT_IN_MILLISECONDS = 10000; //The maximum amount of time NGH will wait before timing out during a serial read operation
static const byte PACKET_DELIMITER_BYTE = 0x3C; // 0x3C = '<'
static const int NUMBER_OF_PACKET_DELIMITER_BYTES = 3; //The number of consecutive packet delimiter bytes that mark the beginning of a packet
static const int GAUGE_PACKET_LENGTH_IN_BYTES = 21;//Length of Header + Payload. Does not include Packet Start Delimiter bytes.

//===========================================================================================================================================================================
//Stepper Motors
static const int STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS = 1;
static const int STEPPER_AVERAGE_RUNSTEPPERIFNECESSARY_TIME_IN_MICROSECONDS = 19;
static const int STEPPER_CCW_LIMIT = 0;
static const int STEPPER_SPEED = 4000; // (steps per second)
static const int STEPPER_CW_LIMIT = 3779;

//===========================================================================================================================================================================
//Pins
//1 UNASSIGNED //TODO just a format example. Update based on actual pin assignment...
//2 UNASSIGNED
//3 UNASSIGNED
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

#endif