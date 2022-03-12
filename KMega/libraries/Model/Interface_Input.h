#ifndef INTERFACE_INPUT_h
#define INTERFACE_INPUT_h

#include <string.h>

/* An Interface that applies to all Control Panel inputs types (SwitchSP2T and Analog Input)
 *
 * */
class Interface_Input
{
public:
	//Update model's status based on the real-world status of the input
	virtual void refreshInputStatus() = 0;
	
	//Return model's status
	virtual String getInputStatusAsString() = 0;
};

#endif