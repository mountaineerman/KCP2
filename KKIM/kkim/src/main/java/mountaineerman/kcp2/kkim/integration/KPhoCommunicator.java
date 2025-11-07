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
            System.out.println("Local Bluetooth Address: " + localDevice.getBluetoothAddress());
            agent = this.localDevice.getDiscoveryAgent();
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not enabled.");
            e.printStackTrace();
        }
        listener = new MyDiscoveryListener(inquiryEventCompleted);
    }

    public void establishBluetoothLinkToKPho() {

        String url3 = "btspp://94652DC65B8D:5;authenticate=false;encrypt=false;master=false";
        System.out.println(url3);
        try {
            StreamConnection connection = (StreamConnection) Connector.open(url3);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            System.out.println("Starting device discovery...");
            this.agent.startInquiry(DiscoveryAgent.GIAC, this.listener); //Shows the list of discoverable bluetooth devices.
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not enabled.");
            e.printStackTrace();
        }

        // String url = "btspp://94652DC65B8D:1";
        // System.out.println(url);
        // try {
        //     StreamConnection connection = (StreamConnection) Connector.open(url);
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }


        // Wait for device discovery to complete
        synchronized (inquiryEventCompleted) {
            try {
                inquiryEventCompleted.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        if (this.listener.get_kPhoDiscovered()) {

            String kPhoBluetoothAddress = "94:65:2D:C6:5B:8D";
            UUID SPP_UUID = new UUID(0x1101);  // Serial Port Profile (SPP) UUID

            // Get the remote Bluetooth device and service
            //RemoteDevice.getRemoteDevice(null)

            //RemoteDevice remoteDevice = RemoteDevice.getRemoteDevice(null)
            // RemoteDevice remoteDevice = RemoteDevice.getRemoteDevice(kPhoBluetoothAddress);
            // ServiceRecord serviceRecord = findService(remoteDevice, sppUUID);

            // //Connect to the discovered device by its address (e.g., BT address of your Android phone)
            // String remoteAddress = btDevice.getBluetoothAddress();
            // System.out.println("Connecting to: " + remoteAddress);
            
            // //Get the UUID of the service you want to connect to on the Android device
            // UUID serviceUUID = new UUID("1101", true); // Example: Serial Port Profile (SPP)
            
            // //Create a connection to the service
            // StreamConnection connection = (StreamConnection) Connector.open("btspp://" + remoteAddress + ":" + serviceUUID + ";authenticate=true;encrypt=true");
            
            // System.out.println("Connected to " + remoteAddress);
            
            // //Here you can send/receive data from the Android phone
        }
        
        // String kPhoBluetoothAddress = "94:65:2D:C6:5B:8D";
        // try {
        //     // Create a Bluetooth URL for the Serial Port Profile (SPP)
        //     // The ":1" is the SPP service channel for the device
        //     String url = "btspp://" + kPhoBluetoothAddress + ":1";  // Replace :1 if necessary with the actual service channel
        //     System.out.println("url: " + url);

        //     // Open a connection to the Bluetooth device using the URL
        //     StreamConnection connection = (StreamConnection) Connector.open(url);

        //     // Successfully connected to the device
        //     System.out.println("Connected to device: " + kPhoBluetoothAddress);

        //     // You can now read/write data to/from the Android device via InputStream/OutputStream
        //     // For example:
        //     // InputStream inputStream = connection.openInputStream();
        //     // OutputStream outputStream = connection.openOutputStream();
            
        //     // Close the connection once done
        //     connection.close();

        // } catch (IOException e) {
        //     System.err.println("Failed to connect: " + e.getMessage());
        //     e.printStackTrace();
        // }


    }
}