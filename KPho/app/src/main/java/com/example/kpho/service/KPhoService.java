package com.example.kpho.service;

import android.app.ActionBar;
import android.content.Context;
import android.os.BatteryManager;
import android.util.Log;
import android.view.View;

import com.example.kpho.integration.BluetoothCommunicator;
import com.example.kpho.integration.PacketUnpacker;
import com.example.kpho.model.PhoneViewModel;

public class KPhoService {

    private final PhoneViewModel phoneViewModel;
    private final BluetoothCommunicator bluetoothCommunicator;
    private final PacketUnpacker packetUnpacker;

    public KPhoService(Context context, PhoneViewModel phoneViewModel) {
        this.phoneViewModel = phoneViewModel;
        this.bluetoothCommunicator = new BluetoothCommunicator(context, this.phoneViewModel);
        this.packetUnpacker = new PacketUnpacker(context, this.phoneViewModel);
    }

    public void begin() {

        this.bluetoothCommunicator.startBluetoothServer(new BluetoothCommunicator.BluetoothConnectionCallback() {
            @Override
            public void onConnected() {
                Log.i("KPho", "Switching to listening and displaying incoming data...");
                KPhoService.this.bluetoothCommunicator.startProcessingInputStream(KPhoService.this.packetUnpacker);
            }
        });
    }

    public void reset() {
        this.phoneViewModel.set_kPhoStatus("Resetting...");
        this.bluetoothCommunicator.stop();
        this.phoneViewModel.set_kPhoStatus("Bluetooth disconnected.");
        this.phoneViewModel.reset();
    }
}
