package com.example.kpho.integration;

import android.content.Context;
import android.os.BatteryManager;

import com.example.kpho.model.PhoneViewModel;

public class PacketUnpacker {
    private final Context context;
    private final PhoneViewModel phoneViewModel;
    private BatteryManager batteryManager = null;

    public PacketUnpacker(Context context, PhoneViewModel viewModel) {
        this.context = context;
        this.phoneViewModel = viewModel;
        this.batteryManager = (BatteryManager) this.context.getSystemService(Context.BATTERY_SERVICE);
    }

    public void unpackPacketBufferIntoModel(byte[] packetBuffer) {

        this.phoneViewModel.setApoapsis(this.retrieveFloatInPacketAtByteNumbers(packetBuffer, 10, 13));
        this.phoneViewModel.setAltitude(this.retrieveFloatInPacketAtByteNumbers(packetBuffer, 14, 17));
        this.phoneViewModel.setPeriapsis(this.retrieveFloatInPacketAtByteNumbers(packetBuffer, 18, 21));
        this.phoneViewModel.setTimeToApoapsisOrPeriapsisText(this.retrieveFloatInPacketAtByteNumbers(packetBuffer, 22, 25));
        this.phoneViewModel.setSpeedText(this.retrieveFloatInPacketAtByteNumbers(packetBuffer, 26, 29));
        this.phoneViewModel.setCurrentDrawText(this.convertTwoBytesInPacketIntoInt(packetBuffer, 30, 31));
        this.phoneViewModel.setKKIMExceptionsText(this.convertTwoBytesInPacketIntoInt(packetBuffer, 32, 33));
        this.phoneViewModel.setBatteryPercentageText(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY));
    }



    /** Returns the integer stored in packet located at the specified byte numbers (see ICD).
     * byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Joplin).
     * Byte numbers can be provided in any order. */
    private int convertTwoBytesInPacketIntoInt(byte[] packet, int byteNum1, int byteNum2) {

        int largestByteNum = 0;
        int smallestByteNum = 0;

        if (byteNum1 > byteNum2) {
            largestByteNum = byteNum1 - 1;
            smallestByteNum = byteNum2 - 1;
        } else {
            largestByteNum = byteNum2 - 1;
            smallestByteNum = byteNum1 - 1;
        }

        byte[] tempTwoByteArray = new byte[2];
        tempTwoByteArray[0] = packet[largestByteNum];
        tempTwoByteArray[1] = packet[smallestByteNum];

        return ((tempTwoByteArray[0] & 0xff) << 8) | (tempTwoByteArray[1] & 0xff);
    }

    /** Returns the float stored in packet located at the specified (4) byte numbers (see ICD).
     * byteNum1 and byteNum2 are "Byte Numbers" as defined in ICD (Joplin).
     * Byte numbers can be provided in any order. */
    private float retrieveFloatInPacketAtByteNumbers(byte[] packet, int byteNum1, int byteNum2) {

        int largestByteNum = 0;
        int smallestByteNum = 0;

        if (byteNum1 > byteNum2) {
            largestByteNum = byteNum1 - 1;
            smallestByteNum = byteNum2 - 1;
        } else {
            largestByteNum = byteNum2 - 1;
            smallestByteNum = byteNum1 - 1;
        }

        int intBits = packet[smallestByteNum] << 24 |
                (packet[smallestByteNum+1] & 0xFF) << 16 |
                (packet[smallestByteNum+2] & 0xFF) << 8 |
                (packet[largestByteNum] & 0xFF);

        return Float.intBitsToFloat(intBits);
    }
}
