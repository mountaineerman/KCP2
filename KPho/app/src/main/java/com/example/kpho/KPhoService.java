package com.example.kpho;

import android.app.ActionBar;
import android.view.View;

public class KPhoService {

    public KPhoService() {
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
