#include "Arduino.h"

#include "configuration.h"
#include <GaugeTower.h>

/* REMINDER: 
When programming KNano, set:
	1. File > Preferences > Sketchbook location = C:\dev\KCP2\KNano
	2. Tools > Board = Arduino Nano
	3. Tools > Processor = ATmega328P (Old Bootloader)
	4. Tools > Port = COM6
*/

void setup() {
	//Serial.setTimeout(SERIAL_READ_TIMEOUT_IN_MILLISECONDS);
	//Serial.begin(BAUD_RATE);
	delay(1000);
}

void loop() {
	delay(1000);

	GaugeTower gaugeTower;

	while(true)
	{
		gaugeTower.testLEDsSequentially();
	}
	
	delay(500);
	exit(EXIT_SUCCESS);
}