package mountaineerman.kcp2.kkim.app.model;

public abstract class Part {
	
	protected String name;

	protected Part(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
