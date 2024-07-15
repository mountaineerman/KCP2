#include <Arduino.h>

#include "configuration.h"
#include <KMegaService.h>

/* REMINDER: 
When programming KMega, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\KMega
	2. Tools > Board = Arduino Mega or Mega 2560
	3. Tools > Processor = ATmega2560 (Mega 2560)
	4. Tools > Port = COM3 << If this changes, need to update C:\dev\KCP2\KMega\configuration.h
*/

void setup() {
	KMegaService kMegaService;
	exit(EXIT_SUCCESS);
}