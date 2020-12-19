/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package mountaineerman.kcp2.kkim.app;

import mountaineerman.kcp2.kkim.kmega.KMegaApi;
import mountaineerman.kcp2.kkim.list.LinkedList;

import static mountaineerman.kcp2.kkim.utilities.StringUtils.join;
import static mountaineerman.kcp2.kkim.utilities.StringUtils.split;

import java.io.IOException;

import krpc.client.Connection;
import krpc.client.RPCException;
import krpc.client.services.KRPC;

import static mountaineerman.kcp2.kkim.app.MessageUtils.getMessage;

public class App
{
	public static void main(String[] args)
	{
		System.out.println("=======================================================================================");
		System.out.println(" Kerbal Control Panel 2");
		System.out.println("=======================================================================================");
		
		//TEMP:
		System.out.println(">>>>> kkim-app: In main method");

		LinkedList tokens;
		tokens = split(getMessage());
		System.out.println(join(tokens));

		System.out.println(KMegaApi.getHelloWorld());
		
		/* 	High-level flow:
		 * 	
		 * 	Initial:
		 * 		-Establish connection to kRPC
		 * 		-Establish connection to kMega
		 * 		-Establish connection to kAndro
		 * 	Loop:
		 * 		-Confirm connection to kRPC
		 * 		-Confirm connection to kMega
		 * 		-Confirm connection between kMega and kNano 
		 * 		-Confirm connection to kAndro
		 *		 
		 * 		A-Fetch latest kRPC values
		 * 		B-Fetch latest kMega values
		 * 		C-Update KIM object states based on A and B
		 * 			-Send "packet" to kMega
		 * 			-Send controlling signals to kRPC
		 * 			-Send "packet" to kAndro
		 * 
		 * 		-Idle	
		 */

		try (Connection connection = Connection.newInstance())
		{
			KRPC krpc = KRPC.newInstance(connection);
			System.out.println("Connected to kRPC version " + krpc.getStatus().getVersion());
		}
		catch (RPCException e)
		{
			System.out.println("Caught RPCException: " + e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("Caught IOException: " + e.getMessage());
		}
	}
}

