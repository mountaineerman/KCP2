package com.example.kpho;

import android.bluetooth.BluetoothManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Note: As of 2025-02-17, my phone is on Android 10 (API level 29)

public class MainActivity extends AppCompatActivity {

    KPhoService kPhoService = new KPhoService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Definitions ==============================================================================
        Button button_StartServer = findViewById(R.id.StartServerButton);
        final View buttonHint_TL = findViewById(R.id.TL_Button_Hint);

        //Needed by KPhoService:
        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        TextView textbox_KPhoStatus = findViewById(R.id.KPhoStatus);
        TextView textbox_Apoapsis = findViewById(R.id.centerBody1_APO);
        kPhoService.initializeOutputs(
                bluetoothManager,
                textbox_KPhoStatus,
                textbox_Apoapsis);

        // When the button is pressed
        button_StartServer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.i("KPho", "User tapped the Launch button...");

                kPhoService.hideStatusAndActionBars(getWindow().getDecorView(), getActionBar());
                View mainLayout = findViewById(R.id.main);
                //Remove top padding that prevents displaying at the top of the screen after the status bar is hidden:
                mainLayout.setPadding(mainLayout.getPaddingLeft(), 0, 0, mainLayout.getPaddingBottom());

                //Delay other actions by 1 second:
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonHint_TL.setVisibility(View.VISIBLE);
                        kPhoService.startBluetoothServer();
                    }

                }, 1000);

                /* TODO:
                APO: (Apoapsis)
                ALT: (Altitude)
                PER: (Periapsis)
                ttA/P (time to Apoapsis/Periapsis)
                Speed (m/s) + (speed type)
                Current draw (mA)
                 */
            }
        });
    }

//    //Necessary to hide the (top) status/navigation/action bar if switching back to app
//    @Override
//    public void onResume(){
//        super.onResume();
//        kPhoService.hideStatusAndActionBars(getWindow().getDecorView(), getActionBar());
//    }
}