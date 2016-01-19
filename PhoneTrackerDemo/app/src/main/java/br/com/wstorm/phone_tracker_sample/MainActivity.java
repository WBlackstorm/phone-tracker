package br.com.wstorm.phone_tracker_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import br.com.wstorm.phone_tracker.PhoneTracker;

public class MainActivity extends AppCompatActivity {

    private PhoneTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tracker = PhoneTracker.init(getApplicationContext());

        TextView textView = (TextView)findViewById(R.id.main_text);

        Log.d("MainActivity - Tracker", tracker.getSensorList().toString());

        textView.setText(String.format("Magnetic: %b\nAcelerometer: %b\nGyroscope: %b\nProximity: %b",
                tracker.hasMagneticSensor(),
                tracker.hasAcelerometerSensor(),
                tracker.hasGyroscopeSensor(),
                tracker.hasProximitySensor()));
    }
}
