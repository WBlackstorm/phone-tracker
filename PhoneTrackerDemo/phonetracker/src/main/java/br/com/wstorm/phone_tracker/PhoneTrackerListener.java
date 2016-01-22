package br.com.wstorm.phone_tracker;

import android.hardware.SensorEvent;

/**
 * Created by wstorm on 1/19/16.
 */
public interface PhoneTrackerListener {

    void accelerometerValueChanged(SensorEvent event);

    void magnetometerValueChanged(SensorEvent event);

    void gravityValueChanged(SensorEvent event);

    void azimuthCalculated(int value);

}
