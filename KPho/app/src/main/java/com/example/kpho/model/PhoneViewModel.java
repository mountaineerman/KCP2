package com.example.kpho.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhoneViewModel extends ViewModel {
    // Private mutable data to hold the changing text strings
    private final MutableLiveData<String> kPhoStatusText = new MutableLiveData<>();
    private final MutableLiveData<String> apoapsisText = new MutableLiveData<>();
    /* TODO:2
        APO: (Apoapsis)
        ALT: (Altitude)
        PER: (Periapsis)
        ttA/P (time to Apoapsis/Periapsis)
        Speed (m/s) + (speed type)
        Current draw (mA) */



    // Public read-only LiveData for the MainActivity to observe
    public LiveData<String> get_kPhoStatusText() {
        return this.kPhoStatusText;
    }
    public LiveData<String> get_apoapsisText() {
        return this.apoapsisText;
    }



    // Setters...
    // Note: postValue() is used so it is safe to call from background threads
    public void set_kPhoStatus(String kPhoStatus) {
        this.kPhoStatusText.postValue(kPhoStatus);
    }

    /** Converts the float to a displayable, rounded apoapsis */
    public void setApoapsis(float apoapsisNewValue) {

        String TBD = "TODO";

        // Use postValue() so it is safe to call from background threads
        apoapsisText.postValue(TBD);
    }
}
