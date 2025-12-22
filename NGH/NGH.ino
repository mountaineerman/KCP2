#include <Arduino.h>

#include "configuration.h"
#include <NGHService.h>

/* REMINDER: 
When programming NGH, set:
	1. File > Preferences > Sketchbook location = /home/anton/KerbalSpaceProgram/KCP2/NGH
	2. Tools > Board = Arduino megaAVR Boards > Arduino Nano Every
	3. Tools > Port = /dev/ttyACM0
	4. Tools > Registers Emulation = None (ATMEGA4809)

To switch between NGH A/B, update configuration.h: NGH_A.
To inspect NGH A/B specific sections, search for: "[GaugePacketA]" / "[GaugePacketB]".
*/

void setup() {
	NGHService nghService;
	nghService.run();
	exit(EXIT_SUCCESS);
}
