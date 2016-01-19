package br.com.wstorm.phone_tracker_sample;

import android.hardware.SensorEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import br.com.wstorm.phone_tracker.PhoneTrackListener;
import br.com.wstorm.phone_tracker.PhoneTracker;

public class MainActivity extends AppCompatActivity implements PhoneTrackListener {

    private PhoneTracker tracker;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tracker = PhoneTracker.init(getApplicationContext(), this);

        textView1 = (TextView)findViewById(R.id.main_text1);
        textView2 = (TextView)findViewById(R.id.main_text2);
        textView3 = (TextView)findViewById(R.id.main_text3);
        textView4 = (TextView)findViewById(R.id.main_text4);
        textView5 = (TextView)findViewById(R.id.main_text5);
        textView = (TextView)findViewById(R.id.main_text);

        Log.d("MainActivity - Tracker", tracker.getSensorList().toString());

        textView.setText(String.format("Magnetic: %b\nAcelerometer: %b\nGyroscope: %b\nProximity: %b\nGravity: %b",
                tracker.hasMagneticSensor(),
                tracker.hasAcelerometerSensor(),
                tracker.hasGyroscopeSensor(),
                tracker.hasProximitySensor(),
                tracker.hasGravitySensor()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        tracker.stop();
    }

    @Override
    public void accelerometerValueChanged(SensorEvent event) {
        textView1.setText("Accelerometer: " + String.valueOf(event.values[0]));
    }

    @Override
    public void magnetometerValueChanged(SensorEvent event) {
        textView2.setText("Magnetometer: " + String.valueOf(event.values[0]));
    }

    @Override
    public void gyroscopeValueChanged(SensorEvent event) {
        textView3.setText("Gyroscope: " + String.valueOf(event.values[0]));
    }

    @Override
    public void proximityValueChanged(SensorEvent event) {
        textView4.setText("Proximity: " + String.valueOf(event.values[0]));
    }

    @Override
    public void gravityValueChanged(SensorEvent event) {
        textView5.setText("Gravity: " + String.valueOf(event.values[0]));
    }
}
