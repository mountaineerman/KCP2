package com.example.kpho;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button startServerButton;

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

//        // Hide the status+navigation bars
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//
//        // Hide the action bar
//        ActionBar actionBar = getActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }

        startServerButton = (Button) findViewById(R.id.StartServerButton);
        // When the button is pressed
        startServerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO: prevent tap on screen from exiting fullscreen mode

                // Hide the status+navigation bars
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);

                // Hide the action bar
                ActionBar actionBar = getActionBar();
                if (actionBar != null) {
                    actionBar.hide();
                }

                Log.i("KPho", "User tapped the Start Server Button...");

            }
        });
    }

    //Necessary to hide the (top) status/navigation/action bar if switching back to app
    @Override
    public void onResume(){
        super.onResume();

        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Hide the action bar
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}