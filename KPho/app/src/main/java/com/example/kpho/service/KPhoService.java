package com.example.kpho.service;

import android.app.ActionBar;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.kpho.integration.BluetoothCommunicator;
import com.example.kpho.integration.PacketUnpacker;
import com.example.kpho.model.PhoneViewModel;

public class KPhoService {

    //private final PhoneViewModel phoneViewModel;
    private final BluetoothCommunicator bluetoothCommunicator;
    private final PacketUnpacker packetUnpacker;

    public KPhoService(Context context, PhoneViewModel phoneViewModel) {
        //this.phoneViewModel = phoneViewModel;
        this.bluetoothCommunicator = new BluetoothCommunicator(context, phoneViewModel);
        this.packetUnpacker = new PacketUnpacker(phoneViewModel);
    }

    public void run() {

        //
        this.bluetoothCommunicator.startBluetoothServer();

        //Loop...
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
}
