package mountaineerman.kcp2.kkim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KKIMProperties {

	private static int kMegaPortBaudrate = 0;
	private static String kMegaPortNumber = "";
	
	public static void initializeProperties () {
		
		Properties properties = loadPropertiesFromFile();
		
		kMegaPortBaudrate = Integer.valueOf(properties.getProperty("kmega.port.baudrate"));
		kMegaPortNumber = properties.getProperty("kmega.port.number");
	}
	
	private static Properties loadPropertiesFromFile() {
		Properties properties = new Properties();
		String settingsFilename = "C:/dev/KCP2/KKIM/config.properties";
		try ( FileInputStream fileInputStream = new FileInputStream(settingsFilename) ) {
		    properties.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);//TODO move to App.main
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);//TODO move to App.main
		}
	    return properties;
	}
	
	public static int getkMegaPortBaudrate() {
		return kMegaPortBaudrate;
	}

	public static String getkMegaPortNumber() {
		return kMegaPortNumber;
	}
}
