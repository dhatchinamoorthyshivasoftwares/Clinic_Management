package com.example.clinicmanagement;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class Appointment_Activity extends AppCompatActivity {
    String DATE ="";
    android.icu.util.Calendar calendar;
    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
    TextView date_tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        date_tv = (TextView) findViewById(R.id.date_tv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = android.icu.util.Calendar.getInstance();
            //calendar.add(Calendar.DATE, -13);
            DATE = mdformat.format(calendar.getTime());
        }
        try {
            date_tv.setText(String.valueOf(DATE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
