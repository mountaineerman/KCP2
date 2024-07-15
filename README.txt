Fly:
1. Connect Control Panel to 12V power.
2. Power on Control Panel.
3. Connect Control Panel to computer.
4. Run uber_launch_KCP2.cmd
5. Launch Flight (or from Space Center?)
6. Start RPC Server (do once):
	Open kRPC settings
	Select "Add server"
	Keep default settings (TBC)
	Select "Start"

If Control Panel (KMega/KNano) is not correctly programmed, run: re-program_control_panel.cmd




Graceful shutdown: (center-right glass cockpit button)

Optional: Open ckan.exe & apply mod updates




Diagnostic Mode:
1. Connect Control Panel to 12V power.
2. Power on Control Panel.
3. Connect Control Panel to computer.
4. Open Arduino IDE.
5. Open Serial Monitor.
6. On Control Panel, select Diagnostic Mode button (center-left).


TROUBLESHOOTING

Symptoms:
-Most lights on.
-Arduino Mega does not "start up".
-Arduino Mega does not respond to Reset signal.
-Programming via Arduino IDE succeeds, but Arduino does not restart.
Cause:
A prototyping sketch was loaded. Everything was working as it should have.