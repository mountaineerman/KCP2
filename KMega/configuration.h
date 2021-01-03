/*
 * KMega Configuration Settings
 */
#ifndef configuration_h
#define configuration_h

#include <Arduino.h>

static const String COMMUNICATION_PORT = "COM4";
static const int BAUD_RATE = 115200;
static const int REFRESH_PERIOD_IN_MILLISECONDS = 25;

//Multiplexer Settings
static const int MULTIPLEXER_IO_ROW_1 = 1;
static const int MULTIPLEXER_IO_ROW_2 = 2;
static const int MULTIPLEXER_IO_ROW_3 = 3;



#endif
