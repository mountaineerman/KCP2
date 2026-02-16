#ifndef Configs_h
#define Configs_h

#include "Arduino.h"


/*------------------------------------------------------------------------------

Purpose: All configurable settings for Kerbal Control Panel - Mk II

------------------------------------------------------------------------------*/

//Data Rate of serial data transmission with computer (bits/second)
// Available rates: 300, 600, 1200, 2400, 4800, 9600, 14400, 19200, 28800,
//					38400, 57600, or 115200.
// Default: TBD
const int BAUDRATE = 115200; //TBD: consider dropping this. Might overfill buffer...



#endif