#include "Arduino.h"

//Libraries Managed by Eclipse
#include "Adafruit_TLC5947.h"

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



//INITIALIZE COMMON PARTS
MuxShield mux;
Adafruit_TLC5947 ledDriverBoards(NUMBER_OF_LED_DRIVER_BOARDS, PIN_LED_DRIVER_BOARDS_CLOCK, PIN_LED_DRIVER_BOARDS_DATA_IN, PIN_LED_DRIVER_BOARDS_LATCH);

void setup() {

	mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP); //TODO is this right?
	mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);		 //TODO is this right?
	mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);		 //TODO is this right?

	ledDriverBoards.begin();
	//TODO replace the following 2 lines with an LED OVERRIDE function... And add a bit to kkim>kmega interface...
	pinMode(PIN_LED_DRIVER_BOARDS_OVERRIDE, OUTPUT);
	digitalWrite(PIN_LED_DRIVER_BOARDS_OVERRIDE, HIGH);//LOW);

	Serial.begin(COMPUTER_BAUD_RATE);
	Serial.println("Hello World! (from Arduino Mega)");
	delay(3000);

}

//TODO Initialize remaining parts...
//Module F
//SwitchSP2T switch_trim(3, false, 3, mux);


int n = 0;
void loop() {
	//switch_trim.refreshStatus();

	Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();
	Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();
	Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();Serial.println();
	Serial.println("MODULE F");
	Serial.print("TRIM: "); Serial.println(mux.digitalReadMS(3,3));//n++switch_trim.getStatus());


	delay(1000);
}


