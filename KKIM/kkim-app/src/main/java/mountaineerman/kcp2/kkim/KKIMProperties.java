package mountaineerman.kcp2.kkim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KKIMProperties {

	private static int kMegaPortBaudrate = 0;
	private static String kMegaPortNumber = "";
	private static byte allPacketsDelimiterByte = 0x00;
	private static byte allPacketsNullByte = 0x00;
	private static int allPacketsNumberOfDelimiterBytes = 0;
	private static int kMegaOutputRefreshPacketLengthInBytes = 0;
	private static int kMegaInputRefreshPacketLengthInBytes = 0;
	private static int kkimRefreshFrequencyInMilliseconds = 0;
	
	public static void initializeProperties () {
		
		System.out.print("Initializing properties... ");
		
		Properties properties = loadPropertiesFromFile();
		
		kMegaPortBaudrate = Integer.valueOf(properties.getProperty("kmega.port.baudrate"));
		kMegaPortNumber = properties.getProperty("kmega.port.number");
		allPacketsDelimiterByte = Byte.valueOf(properties.getProperty("allPackets.delimiter.byte"));
		allPacketsNullByte = Byte.valueOf(properties.getProperty("allPackets.null.byte"));
		allPacketsNumberOfDelimiterBytes = Integer.valueOf(properties.getProperty("allPackets.numberOfDelimiterBytes"));
		kMegaOutputRefreshPacketLengthInBytes = Integer.valueOf(properties.getProperty("kmega.outputRefreshPacket.lengthInBytes"));
		kMegaInputRefreshPacketLengthInBytes = Integer.valueOf(properties.getProperty("kmega.inputRefreshPacket.lengthInBytes"));
		kkimRefreshFrequencyInMilliseconds = Integer.valueOf(properties.getProperty("kkim.refreshFrequencyInMilliseconds"));
		
		System.out.println("DONE");
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
	
	public static byte getallPacketsDelimiterByte() {
		return allPacketsDelimiterByte;
	}
	
	public static byte getallPacketsNullByte() {
		return allPacketsNullByte;
	}
	
	public static int getallPacketsNumberOfDelimiterBytes() {
		return allPacketsNumberOfDelimiterBytes;
	}
	
	public static int getkMegaOutputRefreshPacketLengthInBytes() {
		return kMegaOutputRefreshPacketLengthInBytes;
	}
	
	public static int getkMegaInputRefreshPacketLengthInBytes() {
		return kMegaInputRefreshPacketLengthInBytes;
	}
	
	public static int getkkimRefreshFrequencyInMilliseconds() {
		return kkimRefreshFrequencyInMilliseconds;
	}
}
