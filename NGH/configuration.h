/*
 * NGH Configuration Settings
 */
#ifndef configuration_h
#define configuration_h

#include <Arduino.h>

enum TravelStatus {MOVING, STOPPED};

//===========================================================================================================================================================================
static const bool NGH_A = true;//true == NGH_A; false == NGH_B
//===========================================================================================================================================================================
//KMega Interface
static const int BAUD_RATE = 9600;//Did not work:115200 //Options: (from Arduino IDE Serial Monitor)  300  1,200  2,400  4,800  9,600  19,200  38,400  57,600  74,880  115,200  230,400  250,000  500,000  1,000,000  2,000,000
static const int SERIAL_READ_TIMEOUT_IN_MILLISECONDS = 10000; //The maximum amount of time NGH will wait before timing out during a serial read operation
static const byte PACKET_DELIMITER_BYTE = 0x3C; // 0x3C = '<'
static const int NUMBER_OF_PACKET_DELIMITER_BYTES = 3; //The number of consecutive packet delimiter bytes that mark the beginning of a packet
static const int GAUGE_PACKET_LENGTH_IN_BYTES = 21;//Length of Header + Payload. Does not include Packet Start Delimiter bytes.
static const int MAX_TIME_WITHOUT_PACKET_BEFORE_GAUGE_RESET_IN_MILLISECONDS = 5000;//The length of time without receiving a packet before all Gauges are set to the CCW position

//===========================================================================================================================================================================
//Stepper Motors
static const int STEPPER_MINIMUM_PULSE_WIDTH_IN_MICROSECONDS = 2; //The delay used for the "step" pulse for geared and NEMA17 stepper motors. Affects gauge speed when using StepperMotor2/NEMA17StepperMotor::runStepperIfNecessary().
static const int STEPPER_MINIMUM_WAIT_FOR_DIRECTION_CHANGE_IN_MICROSECONDS = 200;//The delay to re-polarize the coil whenever a Geared or NEMA17 stepper changes direction //TODO Optionally decrease for optimization. Google AI suggested 50 or 100 microseconds initially.
static const int STEPPER_AVERAGE_RUNSTEPPERIFNECESSARY_TIME_IN_MICROSECONDS = 22;
static const int STEPPER_CCW_LIMIT = 0;
static const int STEPPER_CW_LIMIT = 3779;
static const int MAX_GEARED_STEPPER_SPEED = 4000; // The maximum speed of the geared stepper motors (not NEMA17), in steps per second.
static const int MIN_GEARED_STEPPER_SPEED = 400; // The minimum speed of the geared stepper motors (not NEMA17), in steps per second. Also the speed they start with from standstill.
static const int GEARED_STEPPER_ACCELERATION_RATE = 20; //The rate by which the currentSpeed is increased/decreased for each iteration of StepperMotor2::runStepperIfNecessary()
static const int GEARED_STEPPER_DECELERATION_PARAM = 10; //See StepperMotor2::runStepperIfNecessary()
static const int NEMA17_STEPPER_MIN_POSITION = 0;
static const int NEMA17_STEPPER_HALF_OF_POSITIONS = 800;
static const int NEMA17_STEPPER_MAX_POSITION = 1599;
static const int NEMA17_TOTAL_NUMBER_OF_POSITIONS = 1600;
static const int NEMA17_MAX_SPEED = 1600; // The maximum speed of the NEMA17 stepper motor, in steps per second.
static const int NEMA17_MIN_SPEED = 100; // The minimum speed of the NEMA17 stepper motor, in steps per second. Also the speed it starts with from standstill.
static const int NEMA17_ACCELERATION_RATE = 8; //The rate by which the currentSpeed is increased/decreased for each iteration of NEMA17StepperMotor::runStepperIfNecessary()
static const int NEMA17_DECELERATION_PARAM = 4; //See NEMA17StepperMotor::runStepperIfNecessary()

//===========================================================================================================================================================================
//Pins

//[GaugePacketA]
//NGH A RX0 ---	connected to --- Arduino Mega TX2 (pin 16)
//NGH A TX1 ---	connected to --- Arduino Mega RX2 (pin 17)
static const int PIN_EASYDRIVER_SLP = 2;
static const int PIN_EASYDRIVER_MS1 = 3;
static const int PIN_EASYDRIVER_MS2 = 4;
static const int PIN_EASYDRIVER_STEP = 5;
static const int PIN_EASYDRIVER_DIR = 6;
static const int PIN_VID6606_1_FREQUENCY_HEATLIFE = 7;
static const int PIN_VID6606_1_DIRECTION_HEATLIFE = 8;
static const int PIN_VID6606_1_FREQUENCY_GFORCE = 9;
static const int PIN_VID6606_1_DIRECTION_GFORCE = 10;
static const int PIN_VID6606_1_FREQUENCY_MACH = 11;
static const int PIN_VID6606_1_DIRECTION_MACH = 12;
//13 UNASSIGNED
static const int PIN_VID6606_1_FREQUENCY_PITCH = A0;
static const int PIN_VID6606_1_DIRECTION_PITCH = A1;
static const int PIN_VID6606_2_FREQUENCY_FUEL = A2;
static const int PIN_VID6606_2_DIRECTION_FUEL = A3;
//A4 UNASSIGNED
//A5 UNASSIGNED
//A6 UNASSIGNED
//A7 UNASSIGNED

//[GaugePacketB]
//NGH B RX0 ---	connected to --- Arduino Mega TX3 (pin 14)
//NGH B TX1 ---	connected to --- Arduino Mega RX3 (pin 15)
static const int PIN_VID6606_2_FREQUENCY_CHARGE = 2;
static const int PIN_VID6606_2_DIRECTION_CHARGE = 3;
static const int PIN_VID6606_2_FREQUENCY_MNPINT = 4;
static const int PIN_VID6606_2_DIRECTION_MNPINT = 5;
static const int PIN_VID6606_3_FREQUENCY_DENSITY = 6;
static const int PIN_VID6606_3_DIRECTION_DENSITY = 7;
static const int PIN_VID6606_3_FREQUENCY_SPEED = 8;
static const int PIN_VID6606_3_DIRECTION_SPEED = 9;
static const int PIN_VID6606_3_FREQUENCY_VERTICALSPEED = 10;
static const int PIN_VID6606_3_DIRECTION_VERTICALSPEED = 11;
//12 UNASSIGNED
//13 UNASSIGNED
static const int PIN_VID6606_3_FREQUENCY_RADARALTITUDE = A0;
static const int PIN_VID6606_3_DIRECTION_RADARALTITUDE = A1;
//A2 UNASSIGNED
//A3 UNASSIGNED
//A4 UNASSIGNED
//A5 UNASSIGNED
//A6 UNASSIGNED
//A7 UNASSIGNED

#endif