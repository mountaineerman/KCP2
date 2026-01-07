package com.example.kpho.integration;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.kpho.model.PhoneViewModel;

import java.io.IOException;
import java.util.UUID;

public class BluetoothCommunicator {

    private int connectionAttempts = 1;
    private boolean serverInitialized = false;
    private final Context context;
    private final PhoneViewModel phoneViewModel;
    private BluetoothManager bluetoothManager = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothServerSocket bluetoothServerSocket = null;
    private BluetoothSocket bluetoothSocket = null;
    private Handler handler = null;


    public BluetoothCommunicator(Context context, PhoneViewModel phoneViewModel) {
        this.context = context;
        this.phoneViewModel = phoneViewModel;
        this.handler = new Handler();
    }

    /**
     * Verifies bluetooth is enabled, opens Bluetooth Server Socket, and starts thread waiting
     * for a connection from KKIM.
     */
    public void startBluetoothServer() {
        Log.i("KPho", "KPhoService.startBluetoothServer()...");//TODO:2
        if(this.serverInitialized){
            return;
        }

        this.phoneViewModel.set_kPhoStatus("Initializing server...");
        this.bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (this.bluetoothManager == null) {
            this.phoneViewModel.set_kPhoStatus("Failed to initialize BluetoothManager, aborting.");
            Log.e("KPho", "Failed to initialize BluetoothManager");
            return;
        }

        //Note: followed Android dev guide:
        // https://developer.android.com/develop/connectivity/bluetooth/setup?_gl=1*mdrluq*_up*MQ..*_ga*MTYxMDE1NjA5My4xNzM5MzM1ODA3*_ga_6HH9YJMN9M*MTczOTMzNTgwNi4xLjAuMTczOTMzNTgwNi4wLjAuNjA0ODI4ODY3

        this.bluetoothAdapter = this.bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            this.phoneViewModel.set_kPhoStatus("Failed to initialize BluetoothAdapter, aborting.");
            Log.e("KPho", "Failed to initialize BluetoothAdapter");
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            this.phoneViewModel.set_kPhoStatus("Bluetooth not enabled, aborting.");
            Log.e("KPho", "Bluetooth not enabled, aborting.");
            return;
        }

//        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//        Log.d("KPho", "There are currently " + pairedDevices.size() + " paired devices."); //Note: paired != connected.
//        if (pairedDevices.size() > 0) {
//            for (BluetoothDevice device : pairedDevices) {
//                Log.d("KPho", "Name: " + device.getName() + ". MAC address: " + device.getAddress());
//                //Note: Desktop's MAC Address: 00:1A:7D:DA:71:15
//            }
//        }

        this.phoneViewModel.set_kPhoStatus("Opening Bluetooth Server Socket.");
        Log.i("KPho", "Opening Bluetooth Server Socket.");
        BluetoothServerSocket socket = null;

        try {
            String name = "KPho"; //Name for Service Discovery Protocol (SDP) record
            //TODO delete: UUID kPhoUUID = UUID.randomUUID(); //Note: changes every time. For example: 4c3f6479-c724-418b-a319-702cbf43fe51
            UUID kPhoUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // SPP UUID
            Log.d("KPho", "UUID: " + kPhoUUID.toString());
            socket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(name, kPhoUUID); //Ignore this error, it works...
        } catch (IOException e) {
            this.phoneViewModel.set_kPhoStatus("Failed to open Bluetooth Server Socket.");
            Log.e("KPho", "Failed to open Bluetooth Server Socket", e);
            return;
        }

        this.phoneViewModel.set_kPhoStatus("Bluetooth Server Socket opened.");
        Log.i("KPho", "Bluetooth Server Socket opened: " + socket.toString());

        this.bluetoothServerSocket = socket;

        this.handler.post(fetchBluetoothSocket); //In practice, runs fetchBluetoothSocket.run()
    }

    private Runnable fetchBluetoothSocket = new Runnable() {
        public void run() {

            Log.i("KPho", "Attempting to fetch Bluetooth Socket. Connection attempt: " + connectionAttempts);

            try {
                bluetoothSocket = bluetoothServerSocket.accept(10); //Assuming timeout in milliseconds
            } catch (IOException e) {
                BluetoothCommunicator.this.phoneViewModel.set_kPhoStatus("BSS ok. No contact. Tries: " + connectionAttempts);
                connectionAttempts++;
                Log.d("KPho", "bluetoothServerSocket's accept() method failed");
            }

            if (connectionAttempts > 1000) {
                BluetoothCommunicator.this.phoneViewModel.set_kPhoStatus("Could not open bluetoothSocket, aborting.");
                Log.e("KPho", "Could not open bluetoothSocket. Aborting...");
                try {
                    bluetoothServerSocket.close();
                } catch (IOException e) {
                    Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                }
                return;
            }

            if (bluetoothSocket != null) {
                //textbox_KPhoStatus.setText("Connection accepted...");
                Log.i("KPho", "Bluetooth Server Socket connection accepted.");
                try {
                    bluetoothServerSocket.close();
                } catch (IOException e) {
                    Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                }
                serverInitialized = true;
                return;
            }

            //Connection not achieved. Run fetchBluetoothSocket again after the specified amount of time:
            handler.postDelayed(this, 1000);
        }
    };
}
