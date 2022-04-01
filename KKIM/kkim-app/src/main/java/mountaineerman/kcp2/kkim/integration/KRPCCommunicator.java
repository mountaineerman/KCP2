package mountaineerman.kcp2.kkim.integration;

import java.io.IOException;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.KRPC;


public class KRPCCommunicator {
	
	private KRPC kRPC = null;
	
	public KRPCCommunicator() {
		// TODO Auto-generated constructor stub
	}

	public void establishKRPCLink() {
		 
 		System.out.print("Establishing connection to kRPC... ");
 		
		try (Connection connection = Connection.newInstance()) {
			this.kRPC = KRPC.newInstance(connection);
			//System.out.println("Connected to kRPC version " + kRPC.getStatus().getVersion());
		//} catch (RPCException e) {
		//	e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 		
 		System.out.println("DONE");
	}
}
