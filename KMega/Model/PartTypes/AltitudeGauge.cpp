#include "Arduino.h"
#include "../configuration.h"
#include "AltitudeGauge.h"


AltitudeGauge::AltitudeGauge() {
	this->setAltitude(STARTING_ALTITUDE);
	Serial1.begin(KNANO_BAUD_RATE);
}

void AltitudeGauge::setAltitude(float altitude) {
	this->altitude = altitude;
	//Serial1.println(this->altitude);
}

float AltitudeGauge::getAltitude() {
	return this->altitude;
}
