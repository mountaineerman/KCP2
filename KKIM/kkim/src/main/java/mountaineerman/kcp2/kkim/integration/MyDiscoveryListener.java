package mountaineerman.kcp2.kkim.integration;

import java.io.IOException;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import mountaineerman.kcp2.kkim.KKIMProp;

public class MyDiscoveryListener implements DiscoveryListener {

    private String kPhoBluetoothAddress = "94652DC65B8D";//94:65:2D:C6:5B:8D
    private boolean kPhoDiscovered = false;
    private Object inquiryEventCompleted = null;

    public MyDiscoveryListener(Object inquiryEventCompleted) {
        this.inquiryEventCompleted = inquiryEventCompleted;
    }
    
    @Override
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {

        String bluetoothAddress = null;
        try {
            bluetoothAddress = btDevice.getBluetoothAddress();
            System.out.println("Found device: " + bluetoothAddress + " - " + btDevice.getFriendlyName(true));
        } catch (IOException e) {
			KKIMProp.numberOfKKIMExceptions++;
			e.printStackTrace();
        }

        if (bluetoothAddress.equals(this.kPhoBluetoothAddress)) {
            this.kPhoDiscovered = true;
            System.out.println("Oneplus 5T found. Attempting to connect...");
            try {
                //String url = "btspp://" + btDevice.getBluetoothAddress() + ":1;authenticate=false;encrypt=false;master=false";
                String url = "btspp://94652DC65B8D:1";
                System.out.println(url);
                StreamConnection connection = (StreamConnection) Connector.open(url);
            } catch (IOException e) {
                KKIMProp.numberOfKKIMExceptions++;
                e.printStackTrace();
            }

            System.out.println("Starting service search...");
            try {
                DiscoveryAgent agent = LocalDevice.getLocalDevice().getDiscoveryAgent();
                UUID[] uuidSet = new UUID[] { new UUID(0x1101) };  // UUID for Serial Port Profile (SPP)
                agent.searchServices(null, uuidSet, btDevice, this);  // Start searching for services on the device
            } catch (BluetoothStateException e) {
                KKIMProp.numberOfKKIMExceptions++;
                e.printStackTrace();
            }
        }     
        
        // System.out.println("Starting service search...");
        // DiscoveryAgent agent = LocalDevice.getLocalDevice().getDiscoveryAgent();
        // //UUID[] uuidSet = new UUID[] { new UUID(0x1101) };  // UUID for Serial Port Profile (SPP)
        // //agent.searchServices(null, uuidSet, btDevice, this);
        // agent.searchServices(null, null, btDevice, this);
    }
    
    @Override
    public void inquiryCompleted(int discType) {
        System.out.println("Device discovery completed.");
        synchronized (inquiryEventCompleted) {
            inquiryEventCompleted.notify(); // Notify the main thread that the discovery is finished
        }
    }
    
    @Override
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        for (ServiceRecord record : servRecord) {
            DataElement serviceName = record.getAttributeValue(0x0100);  // Attribute ID for service name
            if (serviceName != null) {
                System.out.println("Service Name: " + serviceName.getValue());
            } else {
                System.out.println("Service Name: Not available");
            }

            System.out.println("Attempting to connect...");
            String url2 = record.getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            System.out.println("url2: " + url2);
            try {
                StreamConnection conn = (StreamConnection) Connector.open(url2);
            } catch (IOException e) {
                KKIMProp.numberOfKKIMExceptions++;
                e.printStackTrace();
            }
        }
    }

    @Override
    public void serviceSearchCompleted(int transID, int status) {
        System.out.println("Service search completed.");
    }

    public boolean get_kPhoDiscovered() {
        return this.kPhoDiscovered;
    }
}