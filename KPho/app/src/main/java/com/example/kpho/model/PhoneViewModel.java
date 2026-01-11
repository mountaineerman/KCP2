package com.example.kpho.model;

import android.os.BatteryManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

public class PhoneViewModel extends ViewModel {

    // Private mutable data to hold the changing text strings
    private final MutableLiveData<String> kPhoStatusText = new MutableLiveData<>();
    private final MutableLiveData<String> apoapsisText = new MutableLiveData<>();
    private final MutableLiveData<String> altitudeText = new MutableLiveData<>();
    private final MutableLiveData<String> periapsisText = new MutableLiveData<>();
    private final MutableLiveData<String> timeToApoapsisOrPeriapsisHeaderText = new MutableLiveData<>();
    private final MutableLiveData<String> timeToApoapsisOrPeriapsisBodyText = new MutableLiveData<>();
    private final MutableLiveData<String> speedText = new MutableLiveData<>();
    private final MutableLiveData<String> currentDrawText = new MutableLiveData<>();
    private final MutableLiveData<String> kkimExceptionsText = new MutableLiveData<>();
    private final MutableLiveData<String> batteryPercentageText = new MutableLiveData<>();


    // Public read-only LiveData for the MainActivity to observe
    public LiveData<String> get_kPhoStatusText() {
        return this.kPhoStatusText;
    }
    public LiveData<String> get_apoapsisText() {
        return this.apoapsisText;
    }
    public LiveData<String> get_altitudeText() {
        return this.altitudeText;
    }
    public LiveData<String> get_periapsisText() {
        return this.periapsisText;
    }
    public LiveData<String> get_timeToApoapsisOrPeriapsisHeaderText() { return this.timeToApoapsisOrPeriapsisHeaderText; }
    public LiveData<String> get_timeToApoapsisOrPeriapsisBodyText() { return this.timeToApoapsisOrPeriapsisBodyText; }
    public LiveData<String> get_speedText() {
        return this.speedText;
    }
    public LiveData<String> get_currentDrawText() {
        return this.currentDrawText;
    }
    public LiveData<String> get_kkimExceptionsText() {
        return this.kkimExceptionsText;
    }
    public LiveData<String> get_batteryPercentageText() {
        return this.batteryPercentageText;
    }



    // Setters... They update the visible textboxes on the phone.
    // Note: postValue() is used so it is safe to call from background threads
    public void set_kPhoStatus(String kPhoStatus) {
        this.kPhoStatusText.postValue(kPhoStatus);
    }

    /** Takes a float and:
     * 1) Rounds it to the nearest whole number,
     * 2) Adds commas to separate thousands,
     * 3) Returns it as a String. */
    private String convertFloatToRoundedStringWithCommas(float theFloat) {
        /* Notes:
            - , tells Java to use the grouping separator (commas).
            - .0 specifies zero digits after the decimal point (rounding to a whole number).
            - f indicates a floating-point type */
        return String.format(Locale.CANADA, "%,.0f", theFloat);
    }
    /** Takes an integer and:
     * 1) Adds commas to separate thousands,
     * 2) Returns it as a String. */
    private String convertIntToStringWithCommas(int theInt) {
        /* Notes:
            - , tells Java to use the grouping separator (commas).
            - d indicates a integer type */
        return String.format(Locale.CANADA, "%,d", theInt);
    }
    /** Takes an integer (representing current in milliamps) and:
     * 1) Adds commas to separate thousands (not really needed),
     * 2) Converts the milliamps to amps.
     * 3) Rounds the amps to show 1 decimal place.
     * 2) Returns the amps as a String. */
    private String convertIntMilliampsToStringAmps(int theInt) {
        /* Notes:
            - , tells Java to use the grouping separator (commas).
            - .1 specifies 1 digit after the decimal point.
            - f indicates a floating-point type */
        float milliamps = (float) theInt;
        float amps = (float) (milliamps / 1000.0);
        return String.format(Locale.CANADA, "%,.1f", amps);
    }

    /** Takes a float of seconds and:
     * 1) Rounds it to the nearest whole number,
     * 2) Formats it as: <days> <hours>:<minutes>:<seconds>
     * 3) Returns it as a String. */
    public static String convertFloatSecondsToPrettyTimeString(float totalSeconds) {

        long total = (long) totalSeconds;
        long seconds = total % 60;
        long minutes = (total / 60) % 60;
        long hours = (total / 3600) % 24;
        long days = total / 86400;

        if (days > 0) {
            return String.format(Locale.CANADA, "%d %02d:%02d:%02d", days, hours, minutes, seconds);
        } else {
            return String.format(Locale.CANADA, "%02d:%02d:%02d", hours, minutes, seconds);
        }
    }
    public void setApoapsis(float apoapsisNewValue) {
        this.apoapsisText.postValue(this.convertFloatToRoundedStringWithCommas(apoapsisNewValue));
    }
    public void setAltitude(float altitudeNewValue) {
        this.altitudeText.postValue(this.convertFloatToRoundedStringWithCommas(altitudeNewValue));
    }
    public void setPeriapsis(float periapsisNewValue) {
        this.periapsisText.postValue(this.convertFloatToRoundedStringWithCommas(periapsisNewValue));
    }
    public void setTimeToApoapsisOrPeriapsisText(float timeNewValue) {

        if (timeNewValue < 0) {//Time to Periapsis
            this.timeToApoapsisOrPeriapsisHeaderText.postValue("ttP:");
            timeNewValue = timeNewValue * -1;
        } else {//Time to Apoapsis
            this.timeToApoapsisOrPeriapsisHeaderText.postValue("ttA:");
        }
        nap15ms();
        this.timeToApoapsisOrPeriapsisBodyText.postValue(convertFloatSecondsToPrettyTimeString(timeNewValue));
    }
    public void setSpeedText(float speedNewValue) {
        this.speedText.postValue(this.convertFloatToRoundedStringWithCommas(speedNewValue));
    }
    public void setCurrentDrawText(int currentDrawNewValue) {
        String amps = convertIntMilliampsToStringAmps(currentDrawNewValue);
        this.currentDrawText.postValue(amps);
    }
    public void setKKIMExceptionsText(int exceptionsNewValue) {
        this.kkimExceptionsText.postValue(convertIntToStringWithCommas(exceptionsNewValue));
    }
    public void setBatteryPercentageText(int batteryPercentageNewValue) {
        this.batteryPercentageText.postValue(convertIntToStringWithCommas(batteryPercentageNewValue));
    }



    public void reset() {
        this.apoapsisText.postValue("no data");
        nap15ms(); this.altitudeText.postValue("no data");
        nap15ms(); this.periapsisText.postValue("no data");
        nap15ms(); this.timeToApoapsisOrPeriapsisHeaderText.postValue("ttA/P:");
        nap15ms(); this.timeToApoapsisOrPeriapsisBodyText.postValue("no data");
        nap15ms(); this.speedText.postValue("no data");
        nap15ms(); this.currentDrawText.postValue("no data");
        nap15ms(); this.kkimExceptionsText.postValue("no data");
        nap15ms(); this.batteryPercentageText.postValue("no data");
    }

    private void nap15ms() {
        try {//Short sleep (15ms) to allow the Main Thread to process
            Thread.sleep(15);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
