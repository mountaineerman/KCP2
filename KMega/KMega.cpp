#include <Arduino.h>

#include "LocalArduinoLibraries/MuxShield.h"

#include "configuration.h"

#include "Parts/SwitchSP2T.h"
#include "Parts/AnalogInput.h"
//#include "Parts/LED_PWM.h"
//#include "Parts/StepperMotor.h"
//#include "Parts/NEMA17StepperMotor.h"
//#include "Parts/AltitudeGauge.h"

MuxShield mux;

void setup() {

	mux.setMode(MULTIPLEXER_IO_ROW_1,DIGITAL_IN_PULLUP);
	mux.setMode(MULTIPLEXER_IO_ROW_2,DIGITAL_IN);
	mux.setMode(MULTIPLEXER_IO_ROW_3,DIGITAL_IN);

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
 * PWM_LED
 * (Written exclusively for being driven by LED Driver Boards, because of design constraints)
 *
 * 		channel (range: LED Driver Board:  Board1:0-23,Board2:24-47,Board3:48-71,Board4:72-95 )
 *
 *		short PWM_Value (range: 0-4095)
 *
 *		setPWMValue(short)
 *
 *
 * StepperMotor
 *
 * NEMA17StepperMotor
 *
 * ArduinoNano
 */
