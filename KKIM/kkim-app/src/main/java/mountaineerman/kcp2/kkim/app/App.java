
package mountaineerman.kcp2.kkim.app;

import mountaineerman.kcp2.kkim.KKIMProp;
import mountaineerman.kcp2.kkim.service.KKIMService;


public class App
{
	public static void main(String[] args)
	{
		System.out.println("=============================================================================================");
		System.out.println("Kerbal Control Panel 2 - Kerbal Kontroller Interface Module (KKIM)");
		System.out.println("=============================================================================================");
		
		KKIMProp.initializeProperties();

		
		
//		System.out.println("Starting KKIM-kRPC connection test...");
//		try (Connection connection = Connection.newInstance()) {
//			KRPC krpc = KRPC.newInstance(connection);
//			System.out.println("Connected to kRPC version " + krpc.getStatus().getVersion());
//		} catch (RPCException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		float sentAltitude = (float) 4.2975e3;
//		System.out.println("    sentAltitude: " + sentAltitude);
//		
//		byte[] byteArray = new byte[4];
//
//		int intBits1 =  Float.floatToIntBits(sentAltitude);
//		byteArray[0] = (byte) (intBits1 >> 24);
//		byteArray[1] = (byte) (intBits1 >> 16);
//		byteArray[2] = (byte) (intBits1 >> 8);
//		byteArray[3] = (byte) (intBits1);
//		
//		int intBits2 = byteArray[0] << 24 |
//					  (byteArray[1] & 0xFF) << 16 |
//					  (byteArray[2] & 0xFF) << 8 |
//					  (byteArray[3] & 0xFF);
//		float receivedAltitude = Float.intBitsToFloat(intBits2);
//		
//		System.out.println("receivedAltitude: " + receivedAltitude);
		
		KKIMService kkimService = new KKIMService();
		while(true) {
			kkimService.run();
		}
		//System.exit(0);
	}	
}

