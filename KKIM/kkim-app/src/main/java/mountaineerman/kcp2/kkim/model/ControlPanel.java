package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.KKIMProp;

/* MkII Control Panel
 */
public class ControlPanel implements LEDAggregator, StepperMotorAggregator {

	public ModuleA moduleA = null;
	public ModuleB moduleB = null;
	public ModuleC moduleC = null;
	public ModuleD moduleD = null;
	public ModuleE moduleE = null;
	public ModuleF moduleF = null;
	public ModuleG moduleG = null;
	public ModuleH moduleH = null;
	public ModuleI moduleI = null;
	public ModuleGT moduleGT = null;
	
	public float fuel = 0;//TODO replace
	
	public ControlPanel() {
		
		this.moduleA = new ModuleA();
		this.moduleB = new ModuleB();
		this.moduleC = new ModuleC();
		this.moduleD = new ModuleD();
		this.moduleE = new ModuleE();
		this.moduleF = new ModuleF();
		this.moduleG = new ModuleG();
		this.moduleH = new ModuleH();
		this.moduleI = new ModuleI();
		this.moduleGT = new ModuleGT();
		
		/*
		// =================================================================================================================
		// PARTS - Inputs
		// =================================================================================================================
		
		// ========================================== AnalogInput ==========================================================
		AnalogInput throttlePot = new AnalogInput("throttlePot", ModuleID.A, 0, 1023);
		//TODO create joystick mapping between axis number and Rotation (Pitch/Yaw/Roll), Translation (Up-Down/Left-Right/In-Out):
		AnalogInput joystickAxis1Pot = new AnalogInput("joystickAxis1Pot", ModuleID.B, 0, 1023); 
		AnalogInput joystickAxis2Pot = new AnalogInput("joystickAxis2Pot", ModuleID.B, 0, 1023);
		AnalogInput joystickAxis3Pot = new AnalogInput("joystickAxis3Pot", ModuleID.B, 0, 1023);
		AnalogInput multiUsePot = new AnalogInput("multiUsePot", ModuleID.F, 0, 1023);
		AnalogInput currentSensor = new AnalogInput("currentSensor", ModuleID.F, 0, 1023);
		
		
		
		
		// =================================================================================================================
		// PARTS - Outputs
		// =================================================================================================================
		
		// ========================================== LED_PWM ==============================================================
		
		LED_PWM moduleDBrakeLED = new LED_PWM("moduleDBrakeLED", ModuleID.D);
		LED_PWM autoHoldLED = new LED_PWM("autoHoldLED", ModuleID.D);
		LED_PWM autoProgradeLED = new LED_PWM("autoProgradeLED", ModuleID.D);
		LED_PWM autoRetrogradeLED = new LED_PWM("autoRetrogradeLED", ModuleID.D);
		LED_PWM autoManeuverLED = new LED_PWM("autoManeuverLED", ModuleID.D);
		
		LED_PWM fairingLED = new LED_PWM("fairingLED", ModuleID.E);
		LED_PWM chuteLED = new LED_PWM("chuteLED", ModuleID.E);
		LED_PWM SP3T_Speed_ORB_LED = new LED_PWM("SP3T_Speed_ORB_LED", ModuleID.E);
		LED_PWM SP3T_Mode_PLN_LED = new LED_PWM("SP3T_Mode_PLN_LED", ModuleID.E);
		LED_PWM SP3T_Pitch_30_LED = new LED_PWM("SP3T_Pitch_30_LED", ModuleID.E);
		
		LED_PWM inputRange100LED = new LED_PWM("inputRange100LED", ModuleID.F);
		LED_PWM inputRange75LED = new LED_PWM("inputRange75LED", ModuleID.F);
		LED_PWM inputRange50LED = new LED_PWM("inputRange50LED", ModuleID.F);
		LED_PWM inputRange25LED = new LED_PWM("inputRange25LED", ModuleID.F);
		
		LED_PWM commsLED = new LED_PWM("commsLED", ModuleID.G);
		
		LED_PWM glassTL_LED = new LED_PWM("glassTL_LED", ModuleID.H);
		LED_PWM glassCL_LED = new LED_PWM("glassCL_LED", ModuleID.H);
		LED_PWM glassBL_LED = new LED_PWM("glassBL_LED", ModuleID.H);
		LED_PWM glassTR_LED = new LED_PWM("glassTR_LED", ModuleID.H);
		LED_PWM glassCR_LED = new LED_PWM("glassCR_LED", ModuleID.H);
		LED_PWM glassBR_LED = new LED_PWM("glassBR_LED", ModuleID.H);
		
		// ========================================== LED_PWM_RGB ==========================================================
		LED_PWM tempRedLED = new LED_PWM("heatRedLED", ModuleID.C);
		LED_PWM tempGrnLED = new LED_PWM("heatGrnLED", ModuleID.C);
		LED_PWM tempBluLED = new LED_PWM("heatBluLED", ModuleID.C);
		LED_PWM_RGB heatRGBLED = new LED_PWM_RGB("heatRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("lifeRedLED", ModuleID.C);
		tempGrnLED = new LED_PWM("lifeGrnLED", ModuleID.C);
		tempBluLED = new LED_PWM("lifeBluLED", ModuleID.C);
		LED_PWM_RGB lifeRGBLED = new LED_PWM_RGB("lifeRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("gforceRedLED", ModuleID.C);
		tempGrnLED = new LED_PWM("gforceGrnLED", ModuleID.C);
		tempBluLED = new LED_PWM("gforceBluLED", ModuleID.C);
		LED_PWM_RGB gforceRGBLED = new LED_PWM_RGB("gforceRGBLED", ModuleID.C, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoNormalRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoNormalBluLED", ModuleID.D);
		LED_PWM_RGB autoNormalRGBLED = new LED_PWM_RGB("autoNormalRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoAntiNormalRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoAntiNormalBluLED", ModuleID.D);
		LED_PWM_RGB autoAntiNormalRGBLED = new LED_PWM_RGB("autoAntiNormalRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempGrnLED = new LED_PWM("autoRadialInGrnLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoRadialInBluLED", ModuleID.D);
		LED_PWM_RGB autoRadialInRGBLED = new LED_PWM_RGB("autoRadialInRGBLED", ModuleID.D, null, tempGrnLED, tempBluLED);
		tempGrnLED = null; tempBluLED = null;
		
		tempGrnLED = new LED_PWM("autoRadialOutGrnLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoRadialOutBluLED", ModuleID.D);
		LED_PWM_RGB autoRadialOutRGBLED = new LED_PWM_RGB("autoRadialOutRGBLED", ModuleID.D, null, tempGrnLED, tempBluLED);
		tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoTargetRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoTargetBluLED", ModuleID.D);
		LED_PWM_RGB autoTargetRGBLED = new LED_PWM_RGB("autoTargetRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("autoAntiTargetRedLED", ModuleID.D);
		tempBluLED = new LED_PWM("autoAntiTargetBluLED", ModuleID.D);
		LED_PWM_RGB autoAntiTargetRGBLED = new LED_PWM_RGB("autoAntiTargetRGBLED", ModuleID.D, tempRedLED, null, tempBluLED);
		tempRedLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("machRedLED", ModuleID.G);
		tempGrnLED = new LED_PWM("machGrnLED", ModuleID.G);
		tempBluLED = new LED_PWM("machBluLED", ModuleID.G);
		LED_PWM_RGB machRGBLED = new LED_PWM_RGB("machRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("pitchRedLED", ModuleID.G);
		tempGrnLED = new LED_PWM("pitchGrnLED", ModuleID.G);
		tempBluLED = new LED_PWM("pitchBluLED", ModuleID.G);
		LED_PWM_RGB pitchRGBLED = new LED_PWM_RGB("pitchRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("headingRedLED", ModuleID.G);
		tempGrnLED = new LED_PWM("headingGrnLED", ModuleID.G);
		tempBluLED = new LED_PWM("headingBluLED", ModuleID.G);
		LED_PWM_RGB headingRGBLED = new LED_PWM_RGB("headingRGBLED", ModuleID.G, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("fuelRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("fuelGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("fuelBluLED", ModuleID.I);
		LED_PWM_RGB fuelRGBLED = new LED_PWM_RGB("fuelRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("chargeRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("chargeGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("chargeBluLED", ModuleID.I);
		LED_PWM_RGB chargeRGBLED = new LED_PWM_RGB("chargeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("deltaChargeRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("deltaChargeGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("deltaChargeBluLED", ModuleID.I);
		LED_PWM_RGB deltaChargeRGBLED = new LED_PWM_RGB("deltaChargeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("mnprpRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("mnprpGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("mnprpBluLED", ModuleID.I);
		LED_PWM_RGB mnprpRGBLED = new LED_PWM_RGB("mnprpRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("intakeRedLED", ModuleID.I);
		tempGrnLED = new LED_PWM("intakeGrnLED", ModuleID.I);
		tempBluLED = new LED_PWM("intakeBluLED", ModuleID.I);
		LED_PWM_RGB intakeRGBLED = new LED_PWM_RGB("intakeRGBLED", ModuleID.I, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("densityRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("densityGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("densityBluLED", ModuleID.GT);
		LED_PWM_RGB densityRGBLED = new LED_PWM_RGB("densityRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("speedRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("speedGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("speedBluLED", ModuleID.GT);
		LED_PWM_RGB speedRGBLED = new LED_PWM_RGB("speedRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("vertSpeedRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("vertSpeedGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("vertSpeedBluLED", ModuleID.GT);
		LED_PWM_RGB vertSpeedRGBLED = new LED_PWM_RGB("vertSpeedRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		tempRedLED = new LED_PWM("radAltRedLED", ModuleID.GT);
		tempGrnLED = new LED_PWM("radAltGrnLED", ModuleID.GT);
		tempBluLED = new LED_PWM("radAltBluLED", ModuleID.GT);
		LED_PWM_RGB radAltRGBLED = new LED_PWM_RGB("radAltRGBLED", ModuleID.GT, tempRedLED, tempGrnLED, tempBluLED);
		tempRedLED = null; tempGrnLED = null; tempBluLED = null;
		
		// ========================================== StepperMotor =========================================================
		StepperMotor heatLifeStepper = new StepperMotor("heatLifeStepper", ModuleID.C);
		StepperMotor gforceStepper = new StepperMotor("gforceStepper", ModuleID.C);
		StepperMotor machStepper = new StepperMotor("machStepper", ModuleID.G);
		StepperMotor pitchStepper = new StepperMotor("pitchStepper", ModuleID.G);
		StepperMotor fuelStepper = new StepperMotor("fuelStepper", ModuleID.I);
		StepperMotor chargeStepper = new StepperMotor("chargeStepper", ModuleID.I);
		StepperMotor mnprpIntakeStepper = new StepperMotor("mnprpIntakeStepper", ModuleID.I);
		StepperMotor densityStepper = new StepperMotor("densityStepper", ModuleID.GT);
		StepperMotor speedStepper = new StepperMotor("speedStepper", ModuleID.GT);
		StepperMotor vertSpeedStepper = new StepperMotor("vertSpeedStepper", ModuleID.GT);
		StepperMotor radAltStepper = new StepperMotor("radAltStepper", ModuleID.GT);
		
		// ========================================== NEMA17StepperMotor ===================================================
		StepperMotorNEMA17 headingStepper = new StepperMotorNEMA17("headingStepper", ModuleID.G);
		
		// ========================================== AltitudeGauge ========================================================
		AltitudeGauge altitudeGauge = new AltitudeGauge("altitudeGauge", ModuleID.GT);
		*/
		
		this.disableLEDOverride();
		this.setAllLEDsOff();
	}
	
	//Re-calculate state of higher-level members based on state of lower-level members
	public void refresh() {
		
		if (this.moduleA.brakeButton.getStatus() ^ this.moduleD.brakeSwitch.getStatus()) {//XOR
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMaxPWM());
		} else {
			this.moduleA.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
			this.moduleD.brakeLED.setPWM(KKIMProp.getkmegaMinPWM());
		}
		
		if (this.fuel > 0) { //TODO replace
			this.moduleI.stepperLED_Fuel_Green.setPWM(KKIMProp.getkmegaMaxPWM());
		} else {
			this.moduleI.stepperLED_Fuel_Green.setPWM(KKIMProp.getkmegaMinPWM());
		}
		
		//TODO add remaining parts
		
	}
	
	@Override
	public String toString() {
		
		return  this.moduleA.toString() +
				this.moduleB.toString() +
				this.moduleC.toString() +
				this.moduleD.toString() +
				this.moduleE.toString() +
				this.moduleF.toString() +
				this.moduleG.toString() +
				this.moduleH.toString() +
				this.moduleI.toString() +
				this.moduleGT.toString();
	}
	
	public void setAllLEDsOff() {//TODO
		
	}
	
	public void setAllLEDsOn() {//TODO
		
	}
	
	public void activateLEDOverride() {//TODO
		
	}
	
	public void disableLEDOverride() {//TODO
		
	}
}