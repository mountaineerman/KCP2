package mountaineerman.kcp2.kkim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KKIMProp {

	private static int kMegaPortBaudrate = 0;
	private static String kMegaPortNumber = "";
	private static byte allPacketsDelimiterByte = 0x00;
	private static byte allPacketsNullByte = 0x00;
	private static int allPacketsNumberOfDelimiterBytes = 0;
	private static int kMegaOutputRefreshPacketLengthInBytes = 0;
	private static int kMegaOutputRefreshPacketSendRateInMilliseconds = 0;
	private static int kMegaInputRefreshPacketLengthInBytes = 0;
	private static int kMegaInputRefreshPacketReadRateInMilliseconds = 0;
	private static int kmegaMinPWM = 0;
	private static int kmegaMaxPWM = 0;
	
	private static int kkimInitialStartupDelayInMilliseconds = 0;
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
		kMegaOutputRefreshPacketSendRateInMilliseconds = Integer.valueOf(properties.getProperty("kmega.outputRefreshPacket.sendRateInMilliseconds"));
		kMegaInputRefreshPacketLengthInBytes = Integer.valueOf(properties.getProperty("kmega.inputRefreshPacket.lengthInBytes"));
		kMegaInputRefreshPacketReadRateInMilliseconds = Integer.valueOf(properties.getProperty("kmega.inputRefreshPacket.readRateInMilliseconds"));
		kmegaMinPWM = Integer.valueOf(properties.getProperty("kmega.minPWM"));
		kmegaMaxPWM = Integer.valueOf(properties.getProperty("kmega.maxPWM"));
		
		kkimInitialStartupDelayInMilliseconds = Integer.valueOf(properties.getProperty("kkim.initialStartupDelayInMilliseconds"));
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
	
	public static int getkMegaOutputRefreshPacketSendRateInMilliseconds() {
		return kMegaOutputRefreshPacketSendRateInMilliseconds;
	}
	
	public static int getkMegaInputRefreshPacketLengthInBytes() {
		return kMegaInputRefreshPacketLengthInBytes;
	}
	
	public static int getkMegaInputRefreshPacketReadRateInMilliseconds() {
		return kMegaInputRefreshPacketReadRateInMilliseconds;
	}
	
	public static int getkmegaMinPWM() {
		return kmegaMinPWM;
	}
	
	public static int getkmegaMaxPWM() {
		return kmegaMaxPWM;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int getkkimInitialStartupDelayInMilliseconds() {
		return kkimInitialStartupDelayInMilliseconds;
	}
	
	public static int getkkimRefreshFrequencyInMilliseconds() {
		return kkimRefreshFrequencyInMilliseconds;
	}
}
