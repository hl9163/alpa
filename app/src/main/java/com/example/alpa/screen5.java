package com.example.alpa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

public class screen5 extends AppCompatActivity {
    Button timeButton;
    int hour,minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);
        timeButton = (Button) findViewById(R.id.time);
    }

    public void start(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minutes));
                Toast.makeText(getApplicationContext(),"worked",Toast.LENGTH_LONG).show();
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, hour,minutes, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }
}