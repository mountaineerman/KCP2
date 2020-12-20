#include <Arduino.h>
//#include <SevenSeg.h>

void setup()
{
	Serial.begin(115200);
	Serial.println("Hello World! (from Arduino Nano)");
}

int n = 0;

void loop()
{
	Serial.println(n++);
	delay(1000);
}
