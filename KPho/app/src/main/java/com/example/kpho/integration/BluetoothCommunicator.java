package com.example.kpho.integration;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.kpho.KPhoProp;
import com.example.kpho.model.PhoneViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

public class BluetoothCommunicator {

    private final Context context;
    private final PhoneViewModel phoneViewModel;
    private BluetoothManager bluetoothManager = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothServerSocket bluetoothServerSocket = null;
    private BluetoothSocket bluetoothSocket = null;


    private InputStream inputStream = null;
    private boolean isListening = false;
    private int receivedInt = -1;
    private byte receivedByte = KPhoProp.nullByte;
    private int delimiterByteCounter = 0;
    private int packetBufferByteCounter = 0;
    private byte[] packetBuffer = new byte[KPhoProp.kPhoPacketLengthInBytes];
    private long numberOfRejectedIncomingBytes = 0;
    private long numberOfAcceptedPackets = 0;



    public BluetoothCommunicator(Context context, PhoneViewModel phoneViewModel) {
        this.context = context;
        this.phoneViewModel = phoneViewModel;
        this.clearPacketBufferAndFriends();

        this.bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (this.bluetoothManager == null) {
            this.phoneViewModel.set_kPhoStatus("Failed to initialize BluetoothManager.");
            Log.e("KPho", "Failed to initialize BluetoothManager");
            return;
        }

        //Note: followed Android dev guide:
        // https://developer.android.com/develop/connectivity/bluetooth/setup?_gl=1*mdrluq*_up*MQ..*_ga*MTYxMDE1NjA5My4xNzM5MzM1ODA3*_ga_6HH9YJMN9M*MTczOTMzNTgwNi4xLjAuMTczOTMzNTgwNi4wLjAuNjA0ODI4ODY3

        this.bluetoothAdapter = this.bluetoothManager.getAdapter();
        if (this.bluetoothAdapter == null) {
            this.phoneViewModel.set_kPhoStatus("Failed to initialize BluetoothAdapter.");
            Log.e("KPho", "Failed to initialize BluetoothAdapter");
        }
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

        if (!this.bluetoothAdapter.isEnabled()) {
            this.phoneViewModel.set_kPhoStatus("Bluetooth not enabled, aborting.");
            Log.e("KPho", "Bluetooth not enabled, aborting.");
            return;
        }

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
                Log.e("KPho", "bluetoothServerSocket.accept() failed.");
            }

            if (this.bluetoothSocket == null) {
                Log.e("KPho", "Could not open bluetoothSocket. Aborting...");
                if (this.bluetoothServerSocket != null) {
                    try {
                        this.bluetoothServerSocket.close();
                    } catch (IOException e) {
                        Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                    }
                }
                return;
            } else {
                this.phoneViewModel.set_kPhoStatus("bluetoothSocket open (connection accepted).");
                Log.i("KPho", "BluetoothSocket open (connection accepted).");
                if (this.bluetoothServerSocket != null) {
                    try {
                        this.bluetoothServerSocket.close();
                    } catch (IOException e) {
                        Log.e("KPho", "Could not close the bluetoothServerSocket", e);
                    }
                }
                if (callback != null) callback.onConnected();
            }
        }).start();
    }

    /** Returns true if BluetoothCommunicator.bluetoothSocket is not null (the bluetooth connection is active) */
    public boolean isBluetoothSocketActive() {
        if (this.bluetoothSocket != null && this.bluetoothSocket.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /** Once called, begins to ingests data from the bluetooth input stream in an endless thread.
     * When a delimiter is detected, begins recording data in a buffer. When a packet worth of data
     * has been captured in the buffer, passes a copy of the buffer to packetUnpacker, to load into
     * the model. */
    public void startProcessingInputStream(PacketUnpacker packetUnpacker) {

        if (!this.isBluetoothSocketActive()) {
            Log.e("KPho", "Cannot start processing input stream: Bluetooth Socket is null or not connected.");
            return;
        }

        new Thread(() -> {
            try {
                Log.i("KPho", "Data Stream Thread started.");
                this.inputStream = this.bluetoothSocket.getInputStream();
                byte[] buffer = new byte[KPhoProp.kPhoPacketLengthInBytes];
                int bytes;
                isListening = true;

                while (isListening) {

                    this.receivedInt = this.inputStream.read(); // Note: read() blocks until bytes actually arrive

                    if (this.receivedInt == -1) {
                        Log.d("KPho", "End of stream reached.");
                    } else {
                        this.receivedByte = (byte) this.receivedInt;
                    }

                    if (0 <= this.delimiterByteCounter && this.delimiterByteCounter < KPhoProp.numberOfDelimiterBytes) { //Packet has not started yet
                        if (this.receivedByte == KPhoProp.delimiterByte) {
                            this.delimiterByteCounter++;
                        } else {
                            this.delimiterByteCounter = 0;
                            //Log.d("KPho", "Number of rejected incoming bytes: " + ++this.numberOfRejectedIncomingBytes);
                        }
                    } else if (this.delimiterByteCounter == KPhoProp.numberOfDelimiterBytes) { //Packet read in progress (header or payload)
                        this.packetBufferByteCounter++;
                        this.packetBuffer[this.packetBufferByteCounter - 1] = this.receivedByte;

                        if (this.packetBufferByteCounter == KPhoProp.kPhoPacketLengthInBytes) { //A full packet is in the packetBuffer

                            /*TODO: Add packet validation
                            if (<packet is valid>) {
                                Log.d("KPho", "Number of accepted packets: " + ++this.numberOfAcceptedPackets);
                                <unpack packet>
                            } else {
                                Log.d("KPho", "Number of rejected packets: " + ++this.numberOfRejectedPackets);
                                this.clearPacketBufferAndFriends();
                            }
                             */
                            //Log.d("KPho", "Number of accepted packets: " + ++this.numberOfAcceptedPackets);
                            //Log.d("KPho", "packet: " + Arrays.toString(this.packetBuffer));
                            packetUnpacker.unpackPacketBufferIntoModel(this.packetBuffer);
                            this.clearPacketBufferAndFriends();
                        }
                    }
                }

            } catch (IOException e) {
                Log.e("KPho", "Connection lost during stream read", e);
                isListening = false;
                phoneViewModel.set_kPhoStatus("Connection lost during stream read");
            }
        }).start();
    }

    public void stop() {
        // Set the flag to false so the while loop in startProcessingInputStream() terminates
        isListening = false;

        try {
            // Close the ServerSocket to abort an ongoing .accept() call
            if (this.bluetoothServerSocket != null) {
                this.bluetoothServerSocket.close();
                this.bluetoothServerSocket = null;
            }

            // Close the InputStream first to unblock any .read() operations
            if (this.inputStream != null) {
                this.inputStream.close();
                this.inputStream = null;
            }

            // Close the Socket to kill the physical connection
            if (this.bluetoothSocket != null) {
                this.bluetoothSocket.close();
                this.bluetoothSocket = null;
            }
        } catch (IOException e) {
            Log.e("KPho", "Error during BluetoothCommunicator.stop()", e);
        }
    }

    private void clearPacketBufferAndFriends() {
        this.receivedInt = -1;
        this.receivedByte = KPhoProp.nullByte;
        this.delimiterByteCounter = 0;
        this.packetBufferByteCounter = 0;
        Arrays.fill(this.packetBuffer, KPhoProp.nullByte);
        this.numberOfRejectedIncomingBytes = 0;
        this.numberOfAcceptedPackets = 0;
    }

    /** Callback function so KPhoService knows once a bluetooth connection has been established */
    public interface BluetoothConnectionCallback {
        void onConnected();
    }
}
