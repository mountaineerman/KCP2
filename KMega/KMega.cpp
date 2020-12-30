#include <Arduino.h>
#include <MuxShield.h>

void setup()
{
	//Serial.begin(115200);
	//Serial.println("Hello World! (from Arduino Mega)");

	MuxShield mux;
	mux.setMode(1,DIGITAL_IN_PULLUP);
	mux.setMode(2,DIGITAL_IN);
	mux.setMode(3,DIGITAL_IN);
}

int n = 0;

void loop()
{
	//Serial.println(n++);
	//delay(1000);
}

// ==============================================================================================
// INPUTS
// ==============================================================================================
class Switch
{
	private:
		uint8_t pinNumber; //Range: 0-53, A0-A15. Note: A0-A15 are aliases to some sort of number.
						   //                     They are not used for switches for the KCP2.
		bool isPullupInput;
		bool isMuxInput;
		uint8_t muxRowNumber; //Range: 1-3
		bool status;
	public:
		void refreshStatus();
		bool getStatus();
};

Switch::Switch(unsigned char pinNum, bool isPI)
{
	pinNumber = pinNum;
	isPullupInput = isPI;
	isMuxInput = false;

	if (isPullupInput)
		pinMode(pinNumber, INPUT_PULLUP);
	else
		pinMode(pinNumber, INPUT);
}

Switch::Switch(unsigned char pinNum, bool isPI, bool isMI, unsigned char muxRN)
{
	pinNumber = pinNum;
	isPullupInput = isPI;
	isMuxInput = isMI;
	muxRowNumber = muxRN;

	if(!isMuxInput) //This constructor is exclusively for when the input is read via the Multiplexer
	{
		//TBD:ABORT
	}

	//TBD: Verify the Mux's Row's input type (INPUT_PULLUP/INPUT) matches isPullupInput. ABORT if it does not
}

void Switch::refreshStatus()
{
	if(isMuxInput)
	{
		//TBD
	}
	else //Read via Arduino
	{
		digitalRead(pinNumber);
	}
}

bool Switch::getStatus()
{
	return status;
}

/* ==============================================================================================
 * INPUTS
 * ==============================================================================================
 * Switch
 * 		pinNumber (range: Mux: 				 0-15          )
 * 				  (		  Mega: Theoretical: 0-53, A0-A15  )
 * 				  (		  Mega: By design:   3-21          )
 *
 * 		muxNumber (range: 1-3)
 *
 * 		bool isPullupInput
 * 				TRUE:  pinMode=INPUT_PULLUP
 * 				FALSE: pinMode=INPUT
 *
 * 		bool isMuxInput
 * 				TRUE:  Read via MUX
 * 				FALSE: Read via Mega
 *
 * 		bool status
 * 				TRUE:  HIGH
 * 				FALSE: LOW
 *
 * 		refreshStatus()
 * 		getStatus()
 *
 *
 * AnalogInput
 * 		pinNumber (range: Theoretical: A0-A15  )
 * 				  (		  By Design:   A10-A15 )
 *
 * 		short pinReading (range: 0-1023)
 *
 *		refreshPinReading()
 * 		getPinReading()
 *
 *
 * ==============================================================================================
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
 * 	>Fixed PWM variant... (if Minimalist KKIM>KMega API option is selected)
 * 	>Variable PWM variant...
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
