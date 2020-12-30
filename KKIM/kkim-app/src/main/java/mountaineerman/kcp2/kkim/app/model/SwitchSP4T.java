package mountaineerman.kcp2.kkim.app.model;

//TODO Decide on this approach or to determine position based on 2 SP2T switches
public class SwitchSP4T extends Part {

	private SwitchSP4TPosition position;

	public SwitchSP4T(String name) {
		super(name);
	}

	public SwitchSP4TPosition getPosition() {
		return position;
	}

	public void setPosition(SwitchSP4TPosition position) {
		this.position = position;
	}

}
