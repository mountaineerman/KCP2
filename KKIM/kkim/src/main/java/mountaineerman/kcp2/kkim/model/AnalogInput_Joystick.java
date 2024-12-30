package mountaineerman.kcp2.kkim.model;

import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.IP;
import mountaineerman.kcp2.kkim.KKIMProp;

public class AnalogInput_Joystick extends AnalogInput {

	/** Range: lowerRescaleLimit - upperRescaleLimit */
	private int centerDeadzonedValue = 0;
	
	public AnalogInput_Joystick(IP ip) {
		super(ip);
	}

	public AnalogInput_Joystick(String name, ModuleID moduleID, int lowerCalibrationLimit, int upperCalibrationLimit) {//TODO SCRAP
		super(name, moduleID, lowerCalibrationLimit, upperCalibrationLimit);
	}
	
	@Override
	public void setRawValueAndCalculateCalibratedValues(int rawValue) {
		super.setRawValueAndCalculateCalibratedValues(rawValue);
		
		
		if(this.rescaledValue < KKIMProp.getkkimJoystickCenterDeadzoneMinLimit()) {
			this.centerDeadzonedValue = CommonUtilities.rescaleValue(this.rescaledValue,
																	 this.lowerRescaleLimit, KKIMProp.getkkimJoystickCenterDeadzoneMinLimit(),
					  												 this.lowerRescaleLimit, 0);
		} else if (this.rescaledValue > KKIMProp.getkkimJoystickCenterDeadzoneMaxLimit()) {
			this.centerDeadzonedValue = CommonUtilities.rescaleValue(this.rescaledValue,
																	 KKIMProp.getkkimJoystickCenterDeadzoneMaxLimit(), this.upperRescaleLimit,
					 												 0, this.upperRescaleLimit);
		} else {
			this.centerDeadzonedValue = 0;
		}
	}
	
	public int getCenterDeadzonedValue() {
		return centerDeadzonedValue;
	}

	@Override
	public String toString() {
		return this.getModuleID() + ": " + this.getName() + ": RAW[" + this.getRawValue() + "],\tBOUND["+ this.getBoundValue() + "]\tRESCALED[" + this.getRescaledValue() + "]\tDEADZONED[" + this.getCenterDeadzonedValue() + "]\n";
	}
}
