#include "Arduino.h"

#include "configuration.h"
#include <KNanoService.h>

/* REMINDER: 
When programming KNano, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\KNano
	2. Tools > Board = Arduino Nano
	3. Tools > Processor = ATmega328P (Old Bootloader)
	4. Tools > Port = COM6
*/

void setup() {
	KNanoService kNanoService;
	exit(EXIT_SUCCESS);
}