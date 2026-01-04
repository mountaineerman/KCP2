package mountaineerman.kcp2.kkim.integration;

import java.io.IOException;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class KPhoCommunicator {

    private LocalDevice localDevice = null;
    private DiscoveryAgent agent = null;
    private MyDiscoveryListener listener = null;
    protected final Object inquiryEventCompleted = new Object(); // Used for synchronization

    public KPhoCommunicator() {
        try {
            this.localDevice = LocalDevice.getLocalDevice();
            //System.out.println("Local Bluetooth Address: " + localDevice.getBluetoothAddress());
            agent = this.localDevice.getDiscoveryAgent();
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not enabled.");
            e.printStackTrace();
        }
        listener = new MyDiscoveryListener(inquiryEventCompleted);
    }

    public void establishBluetoothLinkToKPho() {

        //OPTION 1: MANUAL CONNECTION
        String url3 = "btspp://94652DC65B8D:4;authenticate=false;encrypt=false;master=false"; //TODO Windows used channel 5. First attempt on linux is using channel 4. Enhance to search using device discovery (below) or via terminal: "sdptool browse 94:65:2D:C6:5B:8D" and look for "Service Name: KPho" ...?
        //System.out.println(url3);
        try {
            StreamConnection connection = (StreamConnection) Connector.open(url3);
            System.out.println("Connected to KPho.");
        } catch (IOException e) {
            System.out.println("Failed to connect to KPho. Details:");
            e.printStackTrace();
            System.out.println("Aborting...");
            System.exit(-1);
        }

            // // You can now read/write data to/from the Android device via InputStream/OutputStream
            // // For example:
            // // InputStream inputStream = connection.openInputStream();
            // // OutputStream outputStream = connection.openOutputStream();
            
            // // Close the connection once done
            // connection.close();



        // //OPTION 2: AUTOMATED CONNECTION VIA DEVICE DISCOVERY
        // try {
        //     System.out.println("Starting device discovery...");
        //     this.agent.startInquiry(DiscoveryAgent.GIAC, this.listener); //Shows the list of discoverable bluetooth devices.
        // } catch (BluetoothStateException e) {
        //     System.out.println("Bluetooth is not enabled.");
        //     e.printStackTrace();
        // }

        // // Wait for device discovery to complete
        // synchronized (inquiryEventCompleted) {
        //     try {
        //         inquiryEventCompleted.wait();
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }
        
        // if (this.listener.get_kPhoDiscovered()) {

        //     String kPhoBluetoothAddress = "94:65:2D:C6:5B:8D";
        //     UUID SPP_UUID = new UUID(0x1101);  // Serial Port Profile (SPP) UUID

        //     // Get the remote Bluetooth device and service
        //     //RemoteDevice.getRemoteDevice(null)

        //     //RemoteDevice remoteDevice = RemoteDevice.getRemoteDevice(null)
        //     // RemoteDevice remoteDevice = RemoteDevice.getRemoteDevice(kPhoBluetoothAddress);
        //     // ServiceRecord serviceRecord = findService(remoteDevice, sppUUID);

        //     // //Connect to the discovered device by its address (e.g., BT address of your Android phone)
        //     // String remoteAddress = btDevice.getBluetoothAddress();
        //     // System.out.println("Connecting to: " + remoteAddress);
            
        //     // //Get the UUID of the service you want to connect to on the Android device
        //     // UUID serviceUUID = new UUID("1101", true); // Example: Serial Port Profile (SPP)
            
        //     // //Create a connection to the service
        //     // StreamConnection connection = (StreamConnection) Connector.open("btspp://" + remoteAddress + ":" + serviceUUID + ";authenticate=true;encrypt=true");
            
        //     // System.out.println("Connected to " + remoteAddress);
            
        //     // //Here you can send/receive data from the Android phone
        // }
    }
}