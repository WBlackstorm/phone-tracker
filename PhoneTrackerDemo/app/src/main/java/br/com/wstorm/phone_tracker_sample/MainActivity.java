package br.com.wstorm.phone_tracker_sample;

import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;

import br.com.wstorm.phone_tracker.PhoneTrackerListener;
import br.com.wstorm.phone_tracker.PhoneTracker;
import br.com.wstorm.phone_tracker.PhoneTrackerGeoLocation;

public class MainActivity extends AppCompatActivity implements PhoneTrackerListener, PhoneTrackerGeoLocation.PhoneTrackerLocationListener {

    private PhoneTracker tracker;
    private PhoneTrackerGeoLocation phoneTrackerGeoLocation;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView;
    private TextView txtLatitude;
    private TextView txtLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tracker = PhoneTracker.init(getApplicationContext(), this);
        phoneTrackerGeoLocation = PhoneTrackerGeoLocation.getInstance(
                MainActivity.this,
                5000,
                60000,
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                this);


        textView1 = (TextView)findViewById(R.id.main_text1);
        textView2 = (TextView)findViewById(R.id.main_text2);
        textView3 = (TextView)findViewById(R.id.main_text3);
        textView4 = (TextView)findViewById(R.id.main_text4);
        textView5 = (TextView)findViewById(R.id.main_text5);
        textView = (TextView)findViewById(R.id.main_text);

        txtLatitude = (TextView)findViewById(R.id.txt_latitude);
        txtLongitude = (TextView)findViewById(R.id.txt_longitude);

        Log.d("MainActivity - Tracker", tracker.getSensorList().toString());

        textView.setText(String.format("Magnetic: %b\nAcelerometer: %b\nGravity: %b",
                tracker.hasMagneticSensor(),
                tracker.hasAcelerometerSensor(),
                tracker.hasGravitySensor()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        phoneTrackerGeoLocation.init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tracker.stop();
        phoneTrackerGeoLocation.stop();
    }

    @Override
    public void accelerometerValueChanged(SensorEvent event) {
        textView1.setText(String.format("Accelerometer: \nX: %f, Y: %f, Z: %f", event.values[0], event.values[1], event.values[2]));
    }

    @Override
    public void magnetometerValueChanged(SensorEvent event) {
        textView2.setText(String.format("Magnetometer: \nX: %f, Y: %f, Z: %f", event.values[0], event.values[1], event.values[2]));
    }

    @Override
    public void gravityValueChanged(SensorEvent event) {
        textView5.setText(String.format("Gravity: \nX: %f, Y: %f, Z: %f", event.values[0], event.values[1], event.values[2]));

    }

    @Override
    public void azimuthCalculated(int value) {
        textView4.setText(String.format("Azimuth: %d", value));
    }

    @Override
    public void OnLocationChanged(double latitude, double longitude) {

        txtLatitude.setText(String.format("Latitude: %f",latitude));
        txtLongitude.setText(String.format("Longitude: %f",longitude));

    }
}
