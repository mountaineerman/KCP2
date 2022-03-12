#include <Arduino.h>

#include "configuration.h"
#include <SerialCommunicator.h>
#include <PacketAssembler.h>
#include <PacketUnpacker.h>
#include <KMegaService.h>
#include <ControlPanel.h>

/* REMINDER: 
When programming KMega, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\KMega
	2. Tools > Board = Arduino Mega or Mega 2560
	3. Tools > Processor = ATmega2560 (Mega 2560)
	4. Tools > Port = COM4
*/

//int main(void) { //TODO replace setup() and loop() with main()
void setup() {
	ControlPanel controlPanel;
	//SerialCommunicator serialCommunicator;
	//PacketUnpacker packetUnpacker;
	//PacketAssembler packetAssembler;
	KMegaService kMegaService;
	
	exit(EXIT_SUCCESS);
}