package br.com.wstorm.phone_tracker;

import android.hardware.SensorEvent;

/**
 * Created by wstorm on 1/19/16.
 */
public interface PhoneTrackListener {

    void accelerometerValueChanged(SensorEvent event);

    void magnetometerValueChanged(SensorEvent event);

    void gyroscopeValueChanged(SensorEvent event);

    void proximityValueChanged(SensorEvent event);

    void gravityValueChanged(SensorEvent event);

}
