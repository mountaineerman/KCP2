#include <KMegaService.h>
#include <SerialCommunicator.h>

KMegaService::KMegaService() {
}

void KMegaService::startupMode() {
	////Control Panel Startup Test:
	////controlPanel.setAllLEDsOn();
	////controlPanel.setAllLEDsOff();
	////TODO Add startup test for Stepper Motors
	//
	////Establish KKIM Serial Communication Channel:
	//controlPanel.moduleH.ledPWM_GlassCockpit_TR.setPWM(PWM_LED_MAXIMUM); //Indicate Start of Establishing KKIM Serial Communication Channel
	//serialCommunicator.establishSerialLink();
	//controlPanel.moduleH.ledPWM_GlassCockpit_BR.setPWM(PWM_LED_MAXIMUM); //Indicate End of Establishing KKIM Serial Communication Channel
	//
	////TODO Establish KNano Serial Communication Channel. Indicate Start and Finish.
}

void KMegaService::standardOperatingMode() {
	////controlPanel.refreshInputStatus();
	////Assemble toFlightComputer Packet
	////Send toFlightComputer Packet
	//serialCommunicator.ingestData();
	////Refresh outputs?
	////Send toKNano packet?
	////Idle if necessary
}

//void KMegaService::diagnosticMode() {
//	//controlPanel.runDiagnosticMode();
//}
//
//void KMegaService::shutdownMode() {
//	//controlPanel.resetStepperToStartingPosition();
//	serialCommunicator.teardownSerialLink();
//}