#include <Arduino.h>

#include "configuration.h"
#include <NGHService.h>

/* REMINDER: 
When programming KNano, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\KNano
	2. Tools > Board = Arduino Nano
	3. Tools > Processor = ATmega328P (Old Bootloader)
	4. Tools > Port = COM6 //TODO TBC

To switch between GaugePacketA and GaugePacketB search for: "[GaugePacketA]" / "[GaugePacketB]"
*/

void setup() {
	NGHService nghService;
	exit(EXIT_SUCCESS);
}