#include "Arduino.h"

//Libraries Managed by Eclipse
#include "Adafruit_TLC5947.h" //Adafruit 24-channel PWM/LED driver

//Local Arduino Libraries (downloaded manually and imported into Eclipse)
#include "LocalArduinoLibraries/MuxShield.h"

#include "configuration.h"

//INPUTS
#include "Parts/SwitchSP2T.h"
#include "Parts/AnalogInput.h"
//OUTPUTS
//#include "Parts/LED.h" //Not used because of hardware design
#include "Parts/LED_PWM.h"
#include "Parts/StepperMotor.h"
#include "Parts/StepperMotorNEMA17.h"
#include "Parts/AltitudeGauge.h"



//INITIALIZE PARTS
MuxShield mux; //TODO replace with other constructor call

Adafruit_TLC5947 ledDriverBoards(NUMBER_OF_LED_DRIVER_BOARDS, PIN_LED_DRIVER_BOARDS_CLOCK, PIN_LED_DRIVER_BOARDS_DATA_IN, PIN_LED_DRIVER_BOARDS_LATCH);

//TODO Initialize remaining parts...




void setup() {

	mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP);
	mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);
	mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);

	ledDriverBoards.begin();
	//TODO replace the following 2 lines with an LED OVERRIDE function... And add a bit to kkim>kmega interface...
	pinMode(PIN_LED_DRIVER_BOARDS_OVERRIDE, OUTPUT);
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, LOW);

	Serial.begin(COMPUTER_BAUD_RATE);
	Serial.println("Hello World! (from Arduino Mega)");
}

void loop() {

}

/* ==============================================================================================
 * OUTPUTS
 * ==============================================================================================
 *
 * ArduinoNano
 */
