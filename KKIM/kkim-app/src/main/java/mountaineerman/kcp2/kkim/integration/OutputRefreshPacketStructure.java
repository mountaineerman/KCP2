package mountaineerman.kcp2.kkim.integration;

public enum OutputRefreshPacketStructure {
	
	ModuleABrake(11, 12, "Brake Button"),
	ModuleDBrake(13, 14, "sasas");
	
	public final int firstByteOffset; 
	public final int lastByteOffset;
	public String partName;
	
	private OutputRefreshPacketStructure(int firstByteOffset, int lastByteOffset, String partName) {
		this.firstByteOffset = firstByteOffset;
		this.lastByteOffset = lastByteOffset;
		this.partName = partName;
	}
	
}
