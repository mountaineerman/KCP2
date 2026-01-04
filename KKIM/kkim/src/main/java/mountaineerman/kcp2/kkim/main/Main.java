package mountaineerman.kcp2.kkim.main;

import mountaineerman.kcp2.kkim.service.KKIMService;


public class Main
{
	public static void main(String[] args)
	{
		System.out.println("=============================================================================================");
		System.out.println("Kerbal Control Panel 2 - Kerbal Kontroller Interface Module (KKIM)");
		System.out.println("=============================================================================================");
		
		KKIMService kkimService = new KKIMService();
		while(true) {
			kkimService.run();
		}
		//System.exit(0);
	}	
}

// import java.io.IOException;
// 
// public class Main {
//     public static void main(String[] args) {
//         System.out.println("Hello world!");
//         System.out.println("this is Anton");
//         System.out.println("java.version: " + System.getProperty("java.version"));
//         System.out.println("this is Anton again");
//     }
// }