#include <Arduino.h>
#include "C:\dev\KCP2\KMega\configuration.h"
#include "C:\dev\KCP2\KMega\Model\PartTypes\AltitudeGauge.h"


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
