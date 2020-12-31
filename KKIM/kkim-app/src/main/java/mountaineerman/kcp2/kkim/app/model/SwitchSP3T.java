package mountaineerman.kcp2.kkim.app.model;

public class SwitchSP3T extends Part {

	private SwitchSP2T sp2tTopSwitch;
	private SwitchSP2T sp2tBottomSwitch;
	private SwitchSP3TPosition position;
	
	public SwitchSP3T(String name, ModuleID moduleID, SwitchSP2T top_switch, SwitchSP2T bottom_switch) {
		super(name, moduleID);
		this.sp2tTopSwitch = top_switch; //TODO confirm format
		this.sp2tBottomSwitch = bottom_switch; //TODO confirm format
	}

	public SwitchSP3TPosition getPosition() {
		return position;
	}

	public void setTopSwitchStatus(boolean status) {
		this.sp2tTopSwitch.setStatus(status);
	}
	
	public void setBottomSwitchStatus(boolean status) {
		this.sp2tBottomSwitch.setStatus(status);
	}

	public void updatePosition() {
		if(this.sp2tTopSwitch.getStatus() == true) {
			if(this.sp2tBottomSwitch.getStatus() == true) {
				this.position = SwitchSP3TPosition.INVALID;
				String message = String.format("%s is in an invalid position. Top and Bottom SP2T switches are both ON.", this.name);
				throw new IllegalArgumentException(message); //TODO Replace with better exception type; Handle exception gracefully...
			} else {
				this.position = SwitchSP3TPosition.TOP;
			}
		} else {
			if(this.sp2tBottomSwitch.getStatus() == true) {
				this.position = SwitchSP3TPosition.BOTTOM;
			} else {
				this.position = SwitchSP3TPosition.CENTER;
			}
		}	
	}
}
