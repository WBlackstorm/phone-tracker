package br.com.wstorm.phone_tracker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by wstorm on 1/19/16.
 */
public class PhoneTracker implements SensorEventListener{

    private static PhoneTracker instance;

    private PhoneTrackerListener listener;

    private int mAzimuth = 0;
    private float[] gData = new float[3]; // accelerometer
    private float[] mData = new float[3]; // magnetometer
    private float[] rMat = new float[9];
    private float[] iMat = new float[9];
    private float[] orientation = new float[3];

    private static SensorManager sensorManager;

    public static SensorManager getSensorManager() {
        return sensorManager;
    }

    public static PhoneTracker init(Context context, PhoneTrackerListener listener) {


        if (instance == null) {

            instance = new PhoneTracker();
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            instance.registerListeners();
            instance.listener = listener;

        }

        return instance;

    }

    public void stop() {


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

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
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

    // Gravity Section ========================================================

    public boolean hasGravitySensor() {

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            return true;
        }

        return false;

    }

    // ==========================================================================

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                listener.accelerometerValueChanged(event);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                mData = event.values.clone();
                listener.magnetometerValueChanged(event);
                break;

            case Sensor.TYPE_GRAVITY:
                gData = event.values.clone();
                listener.gravityValueChanged(event);
                break;

            default: return;

        }

        if ( SensorManager.getRotationMatrix( rMat, iMat, gData, mData ) ) {
            mAzimuth= (int) ( Math.toDegrees( SensorManager.getOrientation( rMat, orientation )[0] ) + 360 ) % 360;
            listener.azimuthCalculated(mAzimuth);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
