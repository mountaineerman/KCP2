#ifndef INTERFACE_LEDAGGREGATOR_h
#define INTERFACE_LEDAGGREGATOR_h

#include <string.h>

/* TBD Description
 *
 * */
class Interface_LEDAggregator
{
public:
	virtual void setAllLEDsOff() = 0;
	virtual void setAllLEDsOn() = 0;
	virtual void testLEDsSequentially() = 0;
};

#endif