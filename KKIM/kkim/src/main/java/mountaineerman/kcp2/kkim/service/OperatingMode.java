package mountaineerman.kcp2.kkim.service;

//Implements Singleton for the actual modes
public interface OperatingMode {
	public void run(KKIMService kkimService);
}
