package com.example.kpho;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

public class KPhoService {

    private int connectionAttempts = 1;
    private boolean outputsAreInitialized = false;
    private boolean serverInitialized = false;

    //Externally passed:
    private BluetoothManager bluetoothManager = null;
    private TextView textbox_KPhoStatus = null;
    private TextView textbox_Apoapsis = null;

    //Internally managed:
    private BluetoothServerSocket bluetoothServerSocket = null;
    private BluetoothSocket bluetoothSocket = null;
    private Handler handler = null;
    private Runnable fetchBluetoothSocket = new Runnable() {
        public void run() {

            Log.i("KPho", "Attempting to fetch Bluetooth Socket. Connection attempt: " + connectionAttempts);
            //Log.i("KPho", "(fetchBluetoothSocket) bluetoothServerSocket: " + bluetoothServerSocket.toString());

            try {
                bluetoothSocket = bluetoothServerSocket.accept(10); //Assuming timeout in milliseconds
            } catch (IOException e) {
                textbox_KPhoStatus.setText("BSS ok. No contact. Tries: " + connectionAttempts);
                connectionAttempts++;
                Log.i("KPho", "bluetoothServerSocket's accept() method failed");
            }

            if (connectionAttempts > 1000) {
                Log.e("KPho", "Could not open bluetoothSocket. Aborting...");
                try {
                    bluetoothServerSocket.close();
                } catch (IOException e) {
                    Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                }
                return;
            }

            if (bluetoothSocket != null) {
                textbox_KPhoStatus.setText("Connection accepted...");
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

    public KPhoService() {
        this.outputsAreInitialized = false;
        this.serverInitialized = false;
        this.handler = new Handler();
    }

    /**
     * Connects KPhoService to all "output" textboxes, allowing it to modify them. If this method
     * has been previously called, it returns.
     */
    public void initializeOutputs(
            BluetoothManager bluetoothManager,
            TextView textbox_KPhoStatus,
            TextView textbox_Apoapsis) {

        if(this.outputsAreInitialized) {
            return;
        }

        this.bluetoothManager = bluetoothManager;
        this.textbox_KPhoStatus = textbox_KPhoStatus;
        this.textbox_Apoapsis = textbox_Apoapsis;

        this.outputsAreInitialized = true;
    }

    public void hideStatusAndActionBars(View decorView, ActionBar actionBar) {
        // Hide the status+navigation bars
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Hide the action bar
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * Verifies bluetooth is enabled, opens Bluetooth Server Socket, and starts thread waiting
     * for a connection from KKIM.
     */
    public void startBluetoothServer() {
        Log.i("KPho", "KPhoService.startBluetoothServer()...");
        if(this.serverInitialized){
            return;
        }

        this.textbox_KPhoStatus.setText("Initializing server...");

        //Note: followed Android dev guide:
        // https://developer.android.com/develop/connectivity/bluetooth/setup?_gl=1*mdrluq*_up*MQ..*_ga*MTYxMDE1NjA5My4xNzM5MzM1ODA3*_ga_6HH9YJMN9M*MTczOTMzNTgwNi4xLjAuMTczOTMzNTgwNi4wLjAuNjA0ODI4ODY3

        BluetoothAdapter bluetoothAdapter = this.bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Log.e("KPho", "Device doesn't support Bluetooth");
            return;
        }

        //SKIPPED: Enable Bluetooth
        if (!bluetoothAdapter.isEnabled()) {
            this.textbox_KPhoStatus.setText("Bluetooth not enabled, aborting...");
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

        this.textbox_KPhoStatus.setText("Opening Bluetooth Server Socket...");
        Log.i("KPho", "Opening Bluetooth Server Socket.");
        BluetoothServerSocket socket = null;

        try {
            String name = "KPho"; //Name for Service Discovery Protocol (SDP) record
            UUID kPhoUUID = UUID.randomUUID(); //Note: changes every time. For example: 4c3f6479-c724-418b-a319-702cbf43fe51
            Log.d("KPho", "UUID: " + kPhoUUID.toString());
            socket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(name, kPhoUUID);
        } catch (IOException e) {
            this.textbox_KPhoStatus.setText("Failed to open BSS.");
            Log.e("KPho", "Failed to open Bluetooth Server Socket", e);
            return;
        }

        this.textbox_KPhoStatus.setText("Bluetooth Server Socket opened.");
        Log.i("KPho", "Bluetooth Server Socket opened: " + socket.toString());

        this.bluetoothServerSocket = socket;
        //Log.i("KPho", "(startBluetoothServerSocket) this.bluetoothServerSocket: " + this.bluetoothServerSocket.toString());

        this.handler.post(fetchBluetoothSocket); //In practice, runs fetchBluetoothSocket.run()
    }
}
