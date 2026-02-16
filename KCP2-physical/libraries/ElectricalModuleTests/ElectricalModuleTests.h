#ifndef ElectricalModuleTests_h
#define ElectricalModuleTests_h

#include "Arduino.h"


/*------------------------------------------------------------------------------

Purpose: Output system state for each Module to test electrical wiring.

------------------------------------------------------------------------------*/
void displayModuleA()
{
	Serial.print("Switch test: "); Serial.println(v);//switch_test.getState());
}

//==============================================================================
void executeTest()
{
	while(true)
	{
		//Clear screen:
		Serial.print("\n"); // "\r\n" ?
		
		//Display Module Status
		displayModuleA();
		//displayModuleB();
		//displayModuleC();
		//displayModuleD();
		//displayModuleE();
		//displayModuleF();
		//displayModuleG();
		//displayModuleH();
		//displayModuleI();
		//displayModuleJ();
		//displayModuleK();
		//displayModuleL();
		//displayModuleM();
		
		//Wait
		delay(200);
	}
}

#endif