package com.example.clinicmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageView  power_img;
    TextView date_tv;
    String DATE ="";

    android.icu.util.Calendar calendar;
    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
    FloatingActionButton fab_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date_tv = (TextView) findViewById(R.id.date_tv);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);

        power_img = (ImageView) findViewById(R.id.power_img);
        power_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
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

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(view);
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Appointment_Activity.class);
                MainActivity.this.finish();
                startActivity(i);
            }
        });
    }

    public void goback() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to logout ?")
                .setTitle("Clinic")
                .setIcon(R.drawable.ic_launcher_foreground)
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent logout = new Intent(MainActivity.this, Login_Activity.class);
                        MainActivity.this.finish();
                        startActivity(logout);
                        //.setIcon(R.mipmap.admin)
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void Exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to exit ?")
                .setTitle("Clinic")
                .setIcon(R.drawable.ic_launcher_foreground)
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                        //.setIcon(R.mipmap.admin)
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void openDatePickerDialog(final View v) {
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal1.add(Calendar.DAY_OF_YEAR, -10);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    //String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    String selectedDate =String.format("%02d-%02d-%d", dayOfMonth,(monthOfYear + 1), year);
                    date_tv.setText(String.valueOf(selectedDate));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        // datePickerDialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
        // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Exit();
    }
}