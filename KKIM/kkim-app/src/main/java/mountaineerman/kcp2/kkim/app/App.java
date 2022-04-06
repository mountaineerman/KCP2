
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
		
		
		
		
		KKIMService kkimService = new KKIMService();
		while(true) {
			kkimService.run();
		}
		//System.exit(0);
	}	
}

