package com.example.kui_bluetooth_android;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kui_bluetooth_android.BluetoothUredjaj;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    List<BluetoothUredjaj> uredjaji = new ArrayList<>();
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    void ocitajUredjaje() {
        Spinner listaUredjajaGUI = findViewById(R.id.listaUredjajaGUI);
        Set<BluetoothDevice> pairedDeviceSet = bluetoothAdapter.getBondedDevices();
        if (pairedDeviceSet.size() > 0) {

            for (BluetoothDevice device : pairedDeviceSet) {
                uredjaji.add(new BluetoothUredjaj(device.getName(),device.getAddress()));

            }
        }
        ArrayAdapter<BluetoothUredjaj> adapter = new ArrayAdapter<BluetoothUredjaj>(this, android.R.layout.simple_spinner_dropdown_item, uredjaji);
        listaUredjajaGUI.setAdapter(adapter);
        uredjaji = new ArrayList<>();
    }

    ImageView slikaDCMotor=null;
    RotateAnimation rotate=null;
    RotateAnimation reverseRotation=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ocitajUredjaje();
        slikaDCMotor= findViewById(R.id.imageDCMotor);
        rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        reverseRotation = new RotateAnimation(
                360, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(500);
        rotate.setRepeatCount(Animation.INFINITE);
        reverseRotation.setDuration(500);
        reverseRotation.setRepeatCount(Animation.INFINITE);

    }



    BluetoothDevice hc05 = null;
    BluetoothSocket btSocket = null;
    public void btnPovezi(View view)
    {

        int counter = 0;
        do {
            try {
                Spinner listaUredjajaGUI = findViewById(R.id.listaUredjajaGUI);
                hc05 = bluetoothAdapter.getRemoteDevice(((BluetoothUredjaj)(listaUredjajaGUI.getSelectedItem())).deviceHardwareAddress);
                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                System.out.println(btSocket);
                btSocket.connect();
                System.out.println(btSocket.isConnected());
                Toast.makeText(getApplicationContext(),"Uspjesno ste se povezali na "+((BluetoothUredjaj)(listaUredjajaGUI.getSelectedItem())).deviceName, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
            if(counter>3)
                break;
    } while (!btSocket.isConnected());

    }



    public void btnOsvjezi(View view)
    {

        ocitajUredjaje();

    }

    public void btnPokreni(View view)
    {
        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(49);
            slikaDCMotor.startAnimation(rotate);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void btnRotirajUSuprotnomSmjeru(View view) {
        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(50);
            slikaDCMotor.startAnimation(reverseRotation);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public void btnZaustavi(View view) {
        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(51);
        } catch (IOException e) {
            e.printStackTrace();

        }
        slikaDCMotor.clearAnimation();
    }

}