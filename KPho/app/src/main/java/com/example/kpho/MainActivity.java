package com.example.kpho;

import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.kpho.model.AppState;
import com.example.kpho.model.PhoneViewModel;
import com.example.kpho.service.KPhoService;

//Note: As of 2025-02-17, my phone is on Android 10 (API level 29)

public class MainActivity extends AppCompatActivity {

    private KPhoService kPhoService = null;
    private AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        hideStatusAndActionBars();

        //Initialization ==============================================================================
        this.appState = AppState.IDLE;
        PhoneViewModel phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        Button button_Exit = findViewById(R.id.exitButton);
        Button button_LaunchAndReset = findViewById(R.id.LaunchAndResetButton);
        ColorStateList button_green_ColorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.button_green));
        ColorStateList button_grey_ColorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.button_grey));
        ColorStateList button_blue_ColorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.button_blue));
        final View buttonHint_TL = findViewById(R.id.TL_Button_Hint);
        TextView textbox_KPhoStatus = findViewById(R.id.KPhoStatus);
        TextView textbox_Apoapsis = findViewById(R.id.centerBody1_APO);
        TextView textbox_Altitude = findViewById(R.id.centerBody2_ALT);
        TextView textbox_Periapsis = findViewById(R.id.centerBody3_PER);
        TextView textbox_ttAP_Header = findViewById(R.id.centerHeader4);
        TextView textbox_ttAP_Body = findViewById(R.id.centerBody4_ttAP);
        TextView textbox_Speed = findViewById(R.id.centerBody5_Speed);
        TextView textbox_Current = findViewById(R.id.centerBody6_Current);
        TextView textbox_Exceptions = findViewById(R.id.centerBody7_Exceptions);
        TextView textbox_BatteryPercentage = findViewById(R.id.centerBody8_BATTERY);

        //Observe PhoneViewModel. Whenever it changes, update the TextViews
        phoneViewModel.get_kPhoStatusText().observe(this, newText -> {textbox_KPhoStatus.setText(newText);});
        phoneViewModel.get_apoapsisText().observe(this, newText -> {textbox_Apoapsis.setText(newText);});
        phoneViewModel.get_altitudeText().observe(this, newText -> {textbox_Altitude.setText(newText);});
        phoneViewModel.get_periapsisText().observe(this, newText -> {textbox_Periapsis.setText(newText);});
        phoneViewModel.get_timeToApoapsisOrPeriapsisHeaderText().observe(this, newText -> {textbox_ttAP_Header.setText(newText);});
        phoneViewModel.get_timeToApoapsisOrPeriapsisBodyText().observe(this, newText -> {textbox_ttAP_Body.setText(newText);});
        phoneViewModel.get_speedText().observe(this, newText -> {textbox_Speed.setText(newText);});
        phoneViewModel.get_currentDrawText().observe(this, newText -> {textbox_Current.setText(newText);});
        phoneViewModel.get_kkimExceptionsText().observe(this, newText -> {textbox_Exceptions.setText(newText);});
        phoneViewModel.get_batteryPercentageText().observe(this, newText -> {textbox_BatteryPercentage.setText(newText);});

        kPhoService = new KPhoService(getApplicationContext(), phoneViewModel);

        // When the button is pressed
        button_LaunchAndReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (appState == AppState.TRANSITIONING) {
                    return;
                } else if (appState == AppState.IDLE) {

                    Log.i("KPho", "User tapped the Launch button...");
                    button_LaunchAndReset.setBackgroundTintList(button_grey_ColorStateList);
                    appState = AppState.TRANSITIONING;

                    //Delay other actions by 1 second:
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //buttonHint_TL.setVisibility(View.VISIBLE);//TODO1 hide glass cockpit descriptions and unhide them here
                            kPhoService.begin();
                            button_LaunchAndReset.setText("Reset");
                            button_LaunchAndReset.setBackgroundTintList(button_blue_ColorStateList);
                            appState = AppState.RUNNING;
                        }
                    }, 1000);

                } else if (appState == AppState.RUNNING) {

                    Log.i("KPho", "User tapped the Reset button...");
                    button_LaunchAndReset.setBackgroundTintList(button_grey_ColorStateList);
                    appState = AppState.TRANSITIONING;

                    //Delay other actions by 1 second:
                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            kPhoService.reset();
                            button_LaunchAndReset.setText("Launch");
                            button_LaunchAndReset.setBackgroundTintList(button_green_ColorStateList);
                            appState = AppState.IDLE;
                        }
                    }, 1000);
                }
            }
        });

        button_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kPhoService != null) {
                    kPhoService.reset();
                }

                // Close the activity and remove it from 'Recents'
                finishAndRemoveTask();
            }
        });
    }

    private void hideStatusAndActionBars() {
        
        // Hide the status+navigation bars
        int uiOptions =
                  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        // Hide the action bar
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            getActionBar().hide();
        }

        /* No longer necessary?
        //Remove top padding that prevents displaying at the top of the screen after the status bar is hidden:
        View mainLayout = findViewById(R.id.main);
        mainLayout.setPadding(mainLayout.getPaddingLeft(), 0, 0, mainLayout.getPaddingBottom());
         */
    }
}