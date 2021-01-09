#include <Arduino.h>

//Libraries Managed by Eclipse
#include "Adafruit_TLC5947.h"

//Local Arduino Libraries (downloaded manually and imported into Eclipse)
#include "LocalArduinoLibraries/MuxShield.h"

#include "configuration.h"

//INPUTS
#include "Parts/SwitchSP2T.h"
#include "Parts/AnalogInput.h"
//OUTPUTS
//#include "Parts/LED.h" //Note: not used because of hardware design
#include "Parts/LED_PWM.h"
#include "Parts/StepperMotor.h"
#include "Parts/NEMA17StepperMotor.h"
#include "Parts/AltitudeGauge.h"



//INITIALIZE PARTS
MuxShield mux; //TODO replace with other constructor call

Adafruit_TLC5947 ledDriverBoards = Adafruit_TLC5947(NUMBER_OF_LED_DRIVER_BOARDS, LED_DRIVER_BOARDS_CLOCK, LED_DRIVER_BOARDS_DATA_IN, LED_DRIVER_BOARDS_LATCH);

//TODO Initialize remaining parts...

#include "LocalArduinoLibraries/MuxShield.h"


void setup() {

	mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP);
	mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);
	mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);

	ledDriverBoards.begin();
	//TODO replace the following 2 lines with an LED OVERRIDE function... Add bit to interface...
	pinMode(LED_DRIVER_BOARDS_OVERRIDE, OUTPUT);
	digitalWrite(LED_DRIVER_BOARDS_OVERRIDE, LOW);

	Serial.begin(BAUD_RATE);
	Serial.println("Hello World! (from Arduino Mega)");
}

void loop() {

}

/* ==============================================================================================
 * OUTPUTS
 * ==============================================================================================
 * LED
 * (Written exclusively for being driven by Arduino, because of design constraints)
 *
 * 		pinNumber (range: Nano: Theoretical: 0-12, A0-A7	)
 * 				  (		  Nano: By design:   A1-A4			)
 * 				  (		  Mega: Theoretical: 0-53, A0-A15	)
 * 				  (		  Mega: By design:   None			)
 * 				  (		  Mux:  By design:   None			)
 *
 * 		bool status
 * 				TRUE:  ON
 * 				FALSE: OFF
 *
 * 		setStatus(bool)
 *
 *
 *
 * StepperMotor
 *
 * NEMA17StepperMotor
 *
 * ArduinoNano
 */
