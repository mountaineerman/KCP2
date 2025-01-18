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
static const int ALTITUDE_PACKET_LENGTH_IN_BYTES = 19;//Length of Header + Payload. Does not include Packet Start Delimiter bytes.
static const int MAX_TIME_WITHOUT_PACKET_BEFORE_GAUGE_RESET_IN_MILLISECONDS = 5000;//The length of time without receiving a packet before all Gauges are set to the CCW position

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