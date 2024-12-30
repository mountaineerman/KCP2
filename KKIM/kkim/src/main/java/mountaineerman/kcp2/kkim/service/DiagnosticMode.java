package mountaineerman.kcp2.kkim.service;

public final class DiagnosticMode implements OperatingMode { //SINGLETON

	private static DiagnosticMode INSTANCE;
    
	private DiagnosticMode() {}
	
	public static DiagnosticMode getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DiagnosticMode();
        }
        
        return INSTANCE;
    }
	
	public void run(KKIMService kkimService) 
    {
        //System.out.println("Diagnostic Mode");
        
		//Display:
		kkimService.controlPanel.toString();
		kkimService.controlPanel.moduleF.analogInput_Current.getRescaledValue();//Current Draw in mA...
		
        //kkimService.setCurrentOperatingMode(StandardOperatingMode.getInstance());
    }
}