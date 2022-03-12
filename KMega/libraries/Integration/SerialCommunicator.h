#ifndef temporarySerialLibrary_h
#define temporarySerialLibrary_h

char receivedChar;
bool newDataExistsExists = false;

// Example 1 - Receiving single characters
void serialReadOneChar() {
	if (Serial.available() > 0) {
		receivedChar = Serial.read();
		newDataExistsExists = true;
	}
}

void shownewDataExists() {
	if (newDataExistsExists) {
		Serial.print("Received: '");
		Serial.print(receivedChar);
		Serial.println("'");
		newDataExistsExists = false;
	}
}



// Example 6 - Receiving binary data

const byte numBytes = 32; //143;
byte receivedBytes[numBytes];
byte numReceived = 0;

boolean newDataExists = false;

void recvBytesWithStartEndMarkers() {
    static boolean recvInProgress = false;
    static byte ndx = 0;
    byte startMarker = 0x3C;
    byte endMarker = 0x3E;
    byte rb;
   

    while (Serial.available() > 0 && newDataExists == false) {
        rb = Serial.read();

        if (recvInProgress == true) {
            if (rb != endMarker) {
                receivedBytes[ndx] = rb;
                ndx++;
                if (ndx >= numBytes) {
                    ndx = numBytes - 1;
                }
            }
            else {
                receivedBytes[ndx] = '\0'; // terminate the string
                recvInProgress = false;
                numReceived = ndx;  // save the number for use when printing
                ndx = 0;
                newDataExists = true;
            }
        }

        else if (rb == startMarker) {
            recvInProgress = true;
        }
    }
}

void showNewData() {
    if (newDataExists == true) {
        Serial.print("This just in (HEX values)... ");
        for (byte n = 0; n < numReceived; n++) {
            Serial.print(receivedBytes[n], HEX);
            Serial.print(' ');
        }
        Serial.println();
        newDataExists = false;
    }
}

#endif