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
	
	/**
	 * Rescales int "value" from range [oldLowerLimit,oldUpperLimit] to [newLowerLimit,newUpperLimit].
	 * 		If "value" is outside of [oldLowerLimit,oldUpperLimit], it is set to the applicable valid limit.
	 */
	public static int rescaleValue(int value, int oldLowerLimit, int oldUpperLimit, int newLowerLimit, int newUpperLimit) {
		if (value < oldLowerLimit) {
			value = oldLowerLimit;
		} else if (value > oldUpperLimit) {
			value = oldUpperLimit;
		}

		return (value - oldLowerLimit) * (newUpperLimit - newLowerLimit) / (oldUpperLimit - oldLowerLimit) + newLowerLimit;
	}

	public static String convertByteToBinaryString(byte theByte) {
		return String.format("%8s", Integer.toBinaryString(theByte & 0xFF)).replace(' ', '0');
	}

	/**
	 * Converts float to an integer.
	 * 	If the float is greater than Integer.MAX_VALUE (+2,147,483,647), the MAX_VALUE is returned.
	 * 	If the float is less than    Integer.MIN_VALUE (-2,147,483,648), the MIN_VALUE is returned.
	 */
	public static int convertFloatToInteger(float value) {
		if (value > (float) Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		} else if (value < (float) Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		} else {
			return (int) value;
		}
	}
}
