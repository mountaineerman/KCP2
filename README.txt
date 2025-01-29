Fly:
1. Connect Control Panel to 12V power.
2. Power on Control Panel.
3. Connect Control Panel to computer.
4. Run uber_launch_KCP2_play.cmd (or uber_launch_KCP2_dev.cmd)
5. Launch Flight.
6. Start RPC Server (do once):
	Open kRPC settings
	Select "Add server"
	Keep default settings (TBC)
	Select "Start"
7. If doing development, launch KKIM via F5 from VS Code.

If Control Panel (KMega/KNano) is not correctly programmed, run: re-program_control_panel.cmd

Re-generate KKIM JAR file:
1. Open KKIM in VS Code.
2. Check branch is correct in Git Bash.
3. In the Explorer on the left side, expand JAVA PROJECTS.
4. Beside JAVA PROJECTS, select "Export Jar..." button.
	a. Select main class: Main (mountaineerman.kcp2.kkim.main.Main).
	b. Leave default elements selected.
5. Move/rename: C:\dev\KCP2\KCP2.jar to C:\dev\KCP2\KKIM\KKIM.jar

Graceful shutdown: (center-right glass cockpit button)

Optional: Open ckan.exe & apply mod updates


Logging in to GitHub: use Personal Access Token


KMega Diagnostic Mode (KKIM not used):
1. Connect Control Panel to 12V power.
2. Power on Control Panel.
3. Connect Control Panel to computer.
4. Open Arduino IDE.
5. Open Tools > Serial Monitor.
6. On Control Panel, select Diagnostic Mode button (center-left).






(1) TROUBLESHOOTING ===================================================================================================================================================================================

(A) DEVELOPMENT =======================================================================================================================================================================================

Symptoms:
When trying to program the Arduino board, get message: "the selected serial port does not exist or your board is not connected"
Fix:
In Arduino IDE, select the port to activate it.

Symptoms:
1. When trying to program the Arduino board, get errors: "stk500v2_ReceiveMessage(): timeout"
2. Inspecting Arduino IDE > Tools > Port only shows COM1, instead of COM3.
Fix:
Check USB cable connecting KMega to computer is plugged in and in "ON" position.

Symptoms:
1. When trying to program the Arduino board, get errors: "stk500v2_ReceiveMessage(): timeout"
2. Inspecting Arduino IDE > Tools > Port shows "COM3 (Arduino Mega or Mega 2560)".
Cause 1:
The code is caught in a tight loop, which is preventing the bootloader from connecting.
Fix 1:
Select the "Upload" button. Right before bootloader gets to orange text, hit the RST (reset) mom switch.
Cause 2:
https://forum.arduino.cc/t/avrdude-stk500v2_receivemessage-timeout-sometimes-random/586515
It is necessary to reset the ATmega2560 microcontroller on the Arduino Mega in order to activate the bootloader, which handles the upload. The bootloader only runs for a short time waiting for the computer to start the upload. Since the Arduino IDE spends some time compiling the sketch before actually starting the upload, you have to get the timing right, otherwise the bootloader will have already timed out and exited to the sketch previously loaded on the board before the computer even starts the upload. There is a circuit on the Mega that causes this reset to happen automatically at just the right time. What is connected to the Reset pin can disrupt the automatic circuit.
Fix 2a (temporary):
Disconnected the reset wire to Arduino Mega. The Mega started being programmable reliably.
Fix 2b (TBC):
Remove the 5V and Blue LED from the Reset circuit.

Symptoms: (Delete after settingsFilename absolute path is replaced with relative path)
Changes to config.properties are not being seen by the system
Fix:
Check KKIMProp:settingsFilename is pointing to the correct file.

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

Symptoms:
During "full sweep" of a stepper motor, it "bounces" off an edge and stops in the middle instead of in the CCW position.
Cause:
Initial miscalibration combined with "resonant speed".
Fix:
Do full sweep of motor at a slower speed (300 steps/second) using KMega diagnostic mode.