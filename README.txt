Fly:
1. Connect Control Panel to 12V power.
2. Power on Control Panel.
3. Connect Control Panel to computer.
4. Run uber_launch_KCP2.cmd
5. Launch Flight
6. Start RPC Server (do once):
	Open kRPC settings
	Select "Add server"
	Keep default settings (TBC)
	Select "Start"
7. Launch KKIM (Ctrl+F11)

If Control Panel (KMega/KNano) is not correctly programmed, run: re-program_control_panel.cmd




Graceful shutdown: (center-right glass cockpit button)

Optional: Open ckan.exe & apply mod updates


Logging in to GitHub: use Personal Access Token


Diagnostic Mode:
1. Connect Control Panel to 12V power.
2. Power on Control Panel.
3. Connect Control Panel to computer.
4. Open Arduino IDE.
5. Open Serial Monitor.
6. On Control Panel, select Diagnostic Mode button (center-left).


(1) TROUBLESHOOTING ===================================================================================================================================================================================

(A) DEVELOPMENT =======================================================================================================================================================================================

Symptoms:
When trying to program the Arduino board, get message: "the selected serial port does not exist or your board is not connected"
Fix:
In Arduino IDE, select the port to activate it.

Symptoms:
When trying to find references to a function or do a refactor, get "The resource is not on the build path of a Java project."
Fix:
TBD

(B) RUNTIME: ==========================================================================================================================================================================================

Symptoms:
-COMMS LED is flashing
-KKIM ok.
-Control Panel not displaying any information from KSP
-Control Panel not able to control any values in KSP
Cause:
KMega is not receiving control packets from KKIM.
Fix:
Double-check the COM port in KMega configuration.h and KKIM config.properties, and they match what Arduino IDE is detecting.

Symptoms:
Control Panel can control the in-game controls, but there is "jitter". For example, if brake is active on the panel, it flickers between on/off in-game. Similar for throttle.
Fix:
Perhaps some garbage coming through on a port that was not properly closed. Restarting Eclipse and KSP, plus power cycling the panel resolved it.

Symptoms:
-Most lights on.
-Arduino Mega does not "start up".
-Arduino Mega does not respond to Reset signal.
-Programming via Arduino IDE succeeds, but Arduino does not restart.
Cause:
A prototyping sketch was loaded. Everything was working as it should have.