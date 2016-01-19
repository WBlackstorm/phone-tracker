package br.com.wstorm.phone_tracker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

/**
 * Created by wstorm on 1/19/16.
 */
public class PhoneTracker implements SensorEventListener{

    private static PhoneTracker instance;

    private static SensorManager sensorManager;

    public static SensorManager getSensorManager() {
        return sensorManager;
    }

    public static PhoneTracker init(Context context) {


        if (instance == null) {

            instance = new PhoneTracker();
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            instance.registerListeners();

        }

        return instance;

    }

    public void stop(Context context) {


        if (instance != null && sensorManager != null) {

            sensorManager.unregisterListener(this);

        }

    }

    private void registerListeners() {

        if (sensorManager == null) {
            return;
        }

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    public List getSensorList() {

        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        return deviceSensors;

    }

    // Magnetic Section =========================================================

    public boolean hasMagneticSensor() {

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            return true;
        }

        return false;

    }




    // ==========================================================================

    // Acelerometer Section =========================================================

    public boolean hasAcelerometerSensor() {

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            return true;
        }

        return false;

    }




    // ==========================================================================

    // Gyroscope Section ========================================================

    public boolean hasGyroscopeSensor() {

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            return true;
        }

        return false;

    }




    // ==========================================================================

    // Proximity Section ========================================================

    public boolean hasProximitySensor() {

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            return true;
        }

        return false;

    }

    // ==========================================================================

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("PhoneTracker", String.format("Sensor: %s - Value: %s", event.sensor.getName(), event.values));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
