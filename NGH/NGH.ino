#include <Arduino.h>

#include "configuration.h"
#include <NGHService.h>

/* REMINDER: 
When programming KNano, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\NGH
	2. Tools > Board = Arduino megaAVR Boards > Arduino Nano Every
	3. Tools > Port = COM4 (NGH A) / COM5 (NGH B)

To switch between GaugePacketA and GaugePacketB search for: "[GaugePacketA]" / "[GaugePacketB]"
*/

void setup() {
	NGHService nghService;
	nghService.run();
	exit(EXIT_SUCCESS);
}