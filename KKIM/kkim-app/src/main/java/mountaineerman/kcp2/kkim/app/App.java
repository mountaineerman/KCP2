
package mountaineerman.kcp2.kkim.app;

import java.util.Arrays;

import mountaineerman.kcp2.kkim.CommonUtilities;
import mountaineerman.kcp2.kkim.KKIMProperties;
import mountaineerman.kcp2.kkim.model.*;
import mountaineerman.kcp2.kkim.service.KKIMService;

//import krpc.client.Connection;
//import krpc.client.RPCException;
//import krpc.client.services.KRPC;



public class App
{
	public static void main(String[] args)
	{
		System.out.println("=============================================================================================");
		System.out.println("Kerbal Control Panel 2 - Kerbal Kontroller Interface Module (KKIM)");
		System.out.println("=============================================================================================");
		
		KKIMProperties.initializeProperties();
		//System.out.println(KKIMProperties.getkMegaPortBaudrate());
		
		KKIMService kkimService = new KKIMService();
		while(true) {
			kkimService.run();
		}
		//System.exit(0);
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*System.out.println("Starting KKIM-kRPC connection test...");
		try (Connection connection = Connection.newInstance())
		{
			KRPC krpc = KRPC.newInstance(connection);
			System.out.println("Connected to kRPC version " + krpc.getStatus().getVersion());
		}
		catch (RPCException e) {System.out.println("Caught RPCException: " + e.getMessage());}
		catch (IOException e)  {System.out.println("Caught IOException: " + e.getMessage());}
		*/	
	}	
}

