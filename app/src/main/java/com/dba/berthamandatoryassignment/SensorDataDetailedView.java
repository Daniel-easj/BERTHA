package com.dba.berthamandatoryassignment;

import android.content.Intent;
import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public class SensorDataDetailedView extends AppCompatActivity {

    private TextView detailTitle;

    private  TextView deviceId;
    private  TextView pm25;
    private  TextView pm10;
    private  TextView co2;
    private  TextView o3;
    private  TextView pressure;
    private  TextView temperature;
    private  TextView humidity;
    private  TextView utc;
    private  TextView latitude;
    private  TextView longitude;
    private  TextView noise;
    private  TextView userId;

    private SensorUserData selectedUserDataObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data_detailed_view);

        Intent intent = getIntent();
        selectedUserDataObject = (SensorUserData) intent.getSerializableExtra("object");


        // Date SimpleDateFormat

        // Page Title
        detailTitle = findViewById(R.id.detailTitle);

        // Detailed TextViews
        deviceId = findViewById(R.id.deviceId);
        pm25 = findViewById(R.id.pm25);
        pm10 = findViewById(R.id.pm10);
        co2 = findViewById(R.id.co2);
        o3 = findViewById(R.id.o3);
        pressure = findViewById(R.id.pressure);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        utc = findViewById(R.id.utc);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        noise = findViewById(R.id.noise);
        userId = findViewById(R.id.userId);

       insertDataToTextViews(selectedUserDataObject);
    }

    public void insertDataToTextViews(SensorUserData userDataObject){

        detailTitle.setText("Detailed data from " + userDataObject.getDateFormatted() + ":");

        deviceId.setText(userDataObject.getDeviceId() + "");
        pm25.setText(userDataObject.getPm25() + "");
        pm10.setText(userDataObject.getPm10() +" ");
        co2.setText(userDataObject.getCo2() +" ");
        o3.setText(userDataObject.getO3() +" ");
        pressure.setText(userDataObject.getPressure() +" ");
        temperature.setText(userDataObject.getTemperature() +" ");
        humidity.setText(userDataObject.getHumidity() +" ");
        utc.setText(userDataObject.getUtc() +" ");
        latitude.setText(userDataObject.getLatitude() +" ");
        longitude.setText(userDataObject.getLongitude() +" ");
        noise.setText(userDataObject.getNoise() +" ");
        userId.setText(userDataObject.getUserId() +" ");
    }
}
