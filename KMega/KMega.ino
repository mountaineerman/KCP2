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

void setup() { //TODO replace with main()
	ControlPanel controlPanel;
	SerialCommunicator serialCommunicator;
	//PacketUnpacker packetUnpacker;
	//PacketAssembler packetAssembler;
	KMegaService kMegaService;
	
	//kMegaService.startupMode();
	controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWM(PWM_LED_MAXIMUM);
	serialCommunicator.establishSerialLink();
	controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWM(PWM_LED_MAXIMUM);

	while(true) {
		//kMegaService.standardOperatingMode();
		serialCommunicator.ingestData();
		delay(REFRESH_PERIOD_IN_MILLISECONDS); //TODO
	}
	
	exit(EXIT_SUCCESS);
}