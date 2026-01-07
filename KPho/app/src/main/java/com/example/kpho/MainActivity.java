package com.example.kpho;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.kpho.model.PhoneViewModel;
import com.example.kpho.service.KPhoService;

//Note: As of 2025-02-17, my phone is on Android 10 (API level 29)

public class MainActivity extends AppCompatActivity {

    private KPhoService kPhoService = null;

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

        //Initialization ==============================================================================
        PhoneViewModel phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        Button button_StartServer = findViewById(R.id.StartServerButton);
        final View buttonHint_TL = findViewById(R.id.TL_Button_Hint);
        TextView textbox_KPhoStatus = findViewById(R.id.KPhoStatus);
        TextView textbox_Apoapsis = findViewById(R.id.centerBody1_APO);

        //Observe PhoneViewModel. Whenever it changes, update the TextViews
        phoneViewModel.get_kPhoStatusText().observe(this, newText -> {textbox_KPhoStatus.setText(newText);});
        phoneViewModel.get_apoapsisText().observe(this, newText -> {textbox_Apoapsis.setText(newText);});

        kPhoService = new KPhoService(getApplicationContext(), phoneViewModel);

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
                        buttonHint_TL.setVisibility(View.VISIBLE);//TODO1 hide glass cockpit descriptions and unhide them here
                        kPhoService.run();
                    }
                }, 1000);
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