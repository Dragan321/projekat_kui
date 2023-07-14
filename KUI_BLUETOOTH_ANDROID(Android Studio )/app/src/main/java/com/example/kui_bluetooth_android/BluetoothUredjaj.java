package com.example.kui_bluetooth_android;

public class BluetoothUredjaj {

    public String deviceName;
    public String deviceHardwareAddress;

    public BluetoothUredjaj(String deviceName, String deviceHardwareAddress) {
        this.deviceName = deviceName;
        this.deviceHardwareAddress = deviceHardwareAddress;
    }

    @Override
    public String toString() {
        return  deviceName;
    }
}

