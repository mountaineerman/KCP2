package com.example.kpho.integration;

import android.annotation.SuppressLint;
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

    private final Context context;
    private final PhoneViewModel phoneViewModel;
    private BluetoothManager bluetoothManager = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothServerSocket bluetoothServerSocket = null;
    private BluetoothSocket bluetoothSocket = null;


    public BluetoothCommunicator(Context context, PhoneViewModel phoneViewModel) {
        this.context = context;
        this.phoneViewModel = phoneViewModel;
    }

    /**
     * Verifies bluetooth is enabled, opens Bluetooth Server Socket, and starts thread waiting
     * for a connection from KKIM.
     */
    @SuppressLint("MissingPermission")
    public void startBluetoothServer(BluetoothConnectionCallback callback) {
        if(this.isBluetoothSocketActive()){
            Log.w("KPho", "BluetoothCommunicator.startBluetoothServer() called, but bluetoothSocket already active. Returning.");
            return;
        }

        this.phoneViewModel.set_kPhoStatus("Starting bluetooth server...");
        this.bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (this.bluetoothManager == null) {
            this.phoneViewModel.set_kPhoStatus("Failed to initialize BluetoothManager, aborting.");
            Log.e("KPho", "Failed to initialize BluetoothManager");
            return;
        }

        //Note: followed Android dev guide:
        // https://developer.android.com/develop/connectivity/bluetooth/setup?_gl=1*mdrluq*_up*MQ..*_ga*MTYxMDE1NjA5My4xNzM5MzM1ODA3*_ga_6HH9YJMN9M*MTczOTMzNTgwNi4xLjAuMTczOTMzNTgwNi4wLjAuNjA0ODI4ODY3

        this.bluetoothAdapter = this.bluetoothManager.getAdapter();
        if (this.bluetoothAdapter == null) {
            this.phoneViewModel.set_kPhoStatus("Failed to initialize BluetoothAdapter, aborting.");
            Log.e("KPho", "Failed to initialize BluetoothAdapter");
            return;
        }

        if (!this.bluetoothAdapter.isEnabled()) {
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

        new Thread(() -> {
            try {
                Log.i("KPho", "Background Server Thread started.");
                UUID kPhoUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // SPP UUID
                this.bluetoothServerSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord("KPho", kPhoUUID);
            } catch (IOException e) {
                this.phoneViewModel.set_kPhoStatus("Failed to open Bluetooth Server Socket.");
                Log.e("KPho", "Failed to open Bluetooth Server Socket", e);
                return;
            }

            this.phoneViewModel.set_kPhoStatus("Bluetooth Server Socket opened.");
            Log.i("KPho", "Bluetooth Server Socket opened: " + this.bluetoothServerSocket.toString());
            this.phoneViewModel.set_kPhoStatus("Waiting for connection (attempting to fetch Bluetooth Socket).");
            Log.i("KPho", "Waiting for connection (attempting to fetch Bluetooth Socket).");
            try {
                this.bluetoothSocket = this.bluetoothServerSocket.accept();
            } catch (IOException e) {
                this.phoneViewModel.set_kPhoStatus("bluetoothServerSocket.accept() failed.");
                Log.e("KPho", "bluetoothServerSocket.accept() failed.");
            }

            if (this.bluetoothSocket == null) {
                this.phoneViewModel.set_kPhoStatus("Could not open bluetoothSocket, aborting.");
                Log.e("KPho", "Could not open bluetoothSocket. Aborting...");
                try {
                    this.bluetoothServerSocket.close();
                } catch (IOException e) {
                    Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                }
                return;
            } else {
                this.phoneViewModel.set_kPhoStatus("bluetoothSocket open (connection accepted).");
                Log.i("KPho", "BluetoothSocket open (connection accepted).");
                try {
                    this.bluetoothServerSocket.close();
                } catch (IOException e) {
                    Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                }
                if (callback != null) callback.onConnected();
            }
        }).start();
    }

    /*
    private Runnable fetchBluetoothSocket = new Runnable() {
        public void run() {

            Log.i("KPho", "Attempting to fetch Bluetooth Socket. Connection attempt: " + connectionAttempts);

            try {
                BluetoothCommunicator.this.bluetoothSocket = BluetoothCommunicator.this.bluetoothServerSocket.accept(10); //Assuming timeout in milliseconds
            } catch (IOException e) {
                BluetoothCommunicator.this.phoneViewModel.set_kPhoStatus("BSS ok. No contact. Tries: " + connectionAttempts);
                BluetoothCommunicator.this.connectionAttempts++;
                Log.d("KPho", "bluetoothServerSocket's accept() method failed");
            }

            if (BluetoothCommunicator.this.connectionAttempts > 1000) {
                BluetoothCommunicator.this.phoneViewModel.set_kPhoStatus("Could not open bluetoothSocket, aborting.");
                Log.e("KPho", "Could not open bluetoothSocket. Aborting...");
                try {
                    BluetoothCommunicator.this.bluetoothServerSocket.close();
                } catch (IOException e) {
                    Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                }
                return;
            }

            if (BluetoothCommunicator.this.bluetoothSocket != null) {
                BluetoothCommunicator.this.phoneViewModel.set_kPhoStatus("bluetoothSocket open (connection accepted).");
                Log.i("KPho", "BluetoothSocket open (connection accepted).");
                try {
                    BluetoothCommunicator.this.bluetoothServerSocket.close();
                } catch (IOException e) {
                    Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                }
                return;
            }

            //Connection not achieved. Run fetchBluetoothSocket again after the specified amount of time:
            handler.postDelayed(this, 1000);
        }
    };
    */

    /** Returns true if BluetoothCommunicator.bluetoothSocket is not null (the bluetooth connection is active) */
    public boolean isBluetoothSocketActive() {
        if(this.bluetoothSocket != null){
            return true;
        } else {
            return false;
        }
    }

    /** Callback function so KPhoService knows once a bluetooth connection has been established */
    public interface BluetoothConnectionCallback {
        void onConnected();
    }
}
