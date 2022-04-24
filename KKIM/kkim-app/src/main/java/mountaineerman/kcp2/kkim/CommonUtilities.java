package mountaineerman.kcp2.kkim;

public class CommonUtilities {
	
	public static void clearScreen() {
		
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
		//Does not work in Eclipse:
//		System.out.print("\033[H\033[2J");  
//		System.out.flush();
		
		//Does not work in Eclipse:
//		Runtime.getRuntime().exec("cls");
		
		//Does not work in Eclipse:
//		try {
//			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public static int rescaleValue(int value, int oldLowerLimit, int oldUpperLimit, int newLowerLimit, int newUpperLimit) {//TODO add verification of inputs (see AnalogInput.validateCalibrationLimits())
		return (value - oldLowerLimit) * (newUpperLimit - newLowerLimit) / (oldUpperLimit - oldLowerLimit) + newLowerLimit;
	}
	
	public static String convertByteToBinaryString(byte theByte) {
		return String.format("%8s", Integer.toBinaryString(theByte & 0xFF)).replace(' ', '0');
	}
}
