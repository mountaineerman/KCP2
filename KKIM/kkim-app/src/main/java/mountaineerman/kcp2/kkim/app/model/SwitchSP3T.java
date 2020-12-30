package mountaineerman.kcp2.kkim.app.model;

//TODO Decide on this approach or to determine position based on 2 SP2T switches
public class SwitchSP3T extends Part {

	private SwitchSP3TPosition position;

	public SwitchSP3T(String name, ModuleID moduleID) {
		super(name, moduleID);
	}

	public SwitchSP3TPosition getPosition() {
		return position;
	}

	public void setPosition(SwitchSP3TPosition position) {
		this.position = position;
	}

}
