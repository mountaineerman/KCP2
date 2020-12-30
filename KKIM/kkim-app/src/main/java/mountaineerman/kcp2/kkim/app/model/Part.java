package mountaineerman.kcp2.kkim.app.model;

public abstract class Part {
	
	protected String name;
	protected ModuleID moduleID;
	
	protected Part(String name, ModuleID moduleID) {
		super();
		this.name = name;
		this.moduleID = moduleID;
	}

	public String getName() {
		return name;
	}
	
}
