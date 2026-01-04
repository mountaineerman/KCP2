package mountaineerman.kcp2.kkim;

public class KKIMProp {

	// # KMega /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** If true, attempt to connect to KMega and exchange data. If false, pretend it does not exist. */
	public static final boolean kMegaIsActive = false;

	/** Baud Rate Options from Arduino IDE Serial Monitor: 300 1,200 2,400 4,800 9,600 19,200 38,400 57,600 74,880 115,200 230,400 250,000 500,000 1,000,000 2,000,000.
	 * Note: Normal: 115,200. */
	public static final int kMegaPortBaudrate = 115200;

	/** The port number (on Linux) used by KKIM to connect with KMega. */
	public static final String kMegaPortNumber = "ttyACM0";

	/** The character marking the start of any Packet. Note: 0x60 = '<'. */
	public static final byte allPacketsDelimiterByte = '<';

	/** TODO (0x00) */
	public static final byte allPacketsNullByte = '0';

	/** The number of consecutive allPacketsDelimiterBytes that mark the beginning of a packet. */
	public static final int allPacketsNumberOfDelimiterBytes = 3;

	/** TODO */
	public static final int allPacketsHeaderLengthInBytes = 9;
	
	/** Send a packet to KMega no more frequently than once every X milliseconds.
	 * Note: 100 seems pretty snappy, 500 is noticeably too slow. */
	public static final int kMegaAllPacketsSendRateInMilliseconds = 100;

	/** Note: Length includes Delimiter + Header + Payload. */
	public static final int kMegaOutputRefreshPacketLengthInBytes = 198;

	/** Note: Length includes Delimiter + Header + Payload. */
	public static final int kMegaGaugePacketLengthInBytes = 24;

	/** Which packet type to send to KMega. Options: outputRefreshPacket, gaugePacketA, gaugePacketB (see Joplin). */
	public static final String kMegaSendPacketType = "outputRefreshPacket"; //TODO replace string with enum

	/** Note: Length includes Header + Payload. Does not include Packet Start Delimiter bytes. */
	public static final int kMegaInputRefreshPacketLengthInBytes = 32; //28

	/** Read the serial port for inputRefreshPackets no more frequently than once every X milliseconds. */
	public static final int kMegaInputRefreshPacketReadRateInMilliseconds = 2;

	/** The minimum PWM value sent to the TLC5947 LED Driver Chip, when the LED is OFF.
	 * For more information, see: KMega:Adafruit_TLC5947.h or http://www.adafruit.com/products/1429 */
	public static final int kmegaLEDMinPWM = 0;

	/** The PWM value sent to an LED in order to dimly light it up. Used for backlighting but not to indicate when the LED is ON.
	 * For more information, see: KMega:Adafruit_TLC5947.h or http://www.adafruit.com/products/1429 */
	public static final int kmegaLEDDimPWM = 100;

	/** The PWM value sent to the TLC5947 LED Driver Chip for most LEDs when the LED is ON.
	 * Exceptions: FRNG LED, CHUTE LED, Charge rate-of-change LED.
	 * Note: Absolute max: 4095 (kmegaLEDMaxPWM).
	 * For more information, see: KMega:Adafruit_TLC5947.h or http://www.adafruit.com/products/1429 */
	public static final int kmegaLEDOnPWM = 500;

	/** The maximum possible PWM value sent to the TLC5947 LED Driver Chip for an LED (hardware-defined).
	 * For more information, see: KMega:Adafruit_TLC5947.h or http://www.adafruit.com/products/1429 */
	public static final int kmegaLEDMaxPWM = 4095;

	/** The minimum position of the geared stepper motors (CCW) and the NEMA17 motor (pointing "North"). */
	public static final int kmegaSteppersCCWLimit = 0;

	/** The maximum position of the geared stepper motors (CW). Does not apply to the NEMA17 motor. */
	public static final int kmegaSteppersCWLimit = 3779;

	/** The maximum position of the NEMA17 stepper motor (CW). Does not apply to the geared stepper motors. */
	public static final int kmegaNEMA17SteppersCWLimit = 1599;

	/** The number of needle positions that a stepper motor can occupy. Used to lower the "resolution"
	 * of the possible stepper positions. Added as part of stepper motor optimization at the time
	 * that the Nano Gauge Helpers were added. */
	public static final int kmegaSteppersNumberOfNeedlePositions = 1000; //TODO optimize

	/** The Altitude KKIM instructs the Gauge Tower altimeter to display when it is in an error state */
	public static final float kmegaAltitudeGaugeErrorAltitude = 9.99e11f;
	
	// # KMega /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** If true, attempt to connect to KPho and send data. If false, pretend it does not exist. */
	public static final boolean kPhoIsActive = true;

	// # KKIM /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** During Startup Mode, delay at the end before launching StandardOperatingMode. Needed for KMega to start happily. Exact time needed TBD. */
	public static final int kkimStartupModeInitialStartupDelayInMilliseconds = 3000; //TODO Optimize

	/** During Startup Mode, in the case of failure to establish connection to kRPC, the sleep time before trying again. */
	public static final int kkimStartupModeSleepIntervalInMilliseconds = 15000;

	/** The sleep time during IdleMode in between checking if the GameScene has returned to FLIGHT. */
	public static final int kkimIdleModeSleepIntervalInMilliseconds = 5000;

	/** The period of time that KKIM will repeat its activities (reading packets, refreshing model, sending packets, etc). */
	public static final int kkimRefreshFrequencyInMilliseconds = 1;

	/** TODO */
	public static final int kkimJoystickCenterDeadzoneMinLimit = -120;

	/** TODO */
	public static final int kkimJoystickCenterDeadzoneMaxLimit = 120;

	/** The period of time that a momentary switch will ignore repeat "rising edges" in order to debounce the signal. */
	public static final int kkimSwitchMomDebounceTimeInMilliseconds = 200;

	/** During each StandardOperatingMode cycle, display the Communications Diagnostic Information (that KKIM has access to). */
	public static final boolean kkimDisplayCommunicationsDiagnosticInformation = false;

	/** During each StandardOperatingMode cycle, display the time that is going to each high-level step KKIM spends time on. */
	public static final boolean kkimDisplayTaskDurationsDiagnosticInformation = false;

	/** Display the time breakdown of kRPCCommunicator.pullInfoFromKSPIntoModel(). */
	public static final boolean kkimPullInfoFromKSPIntoModelDisplayTimeDiagnosticInformation = false;

	/** During each ControlPanel.refresh(), clear the screen and display the digital values associated with all stepper motor gauges in the KKIM terminal. */
	public static final boolean kkimDisplayStepperMotorDigitalValues = false;
	
}