#include "Arduino.h"

//#include <SevenSeg.h>
//#include "LocalArduinoLibraries/..."

#include "configuration.h"

#include "Parts/LED.h"
//#include "Parts/ThreeDigitSevenSegmentDisplay.h"
//#include "Parts/AltitudeGauge.h"

void setup() {
	Serial.begin(BAUD_RATE);
	Serial.println("Hello World! (from Arduino Nano)");
}

void loop() {

}
