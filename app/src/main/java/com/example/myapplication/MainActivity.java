package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.customCalendar.date.DatePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateTextView;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find our View instances
        dateTextView = findViewById(R.id.date_textview);

        // Initialize the date picker dialog
        initializeDatePicker();

        showDatePicker();
    }

    // Method to initialize the date picker dialog
    private void initializeDatePicker() {
        Calendar now = Calendar.getInstance();

        dpd = DatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
    }

    // Method to show the date picker dialog
    private void showDatePicker() {
        if (dpd != null) {
            dpd.setThemeDark(false);
            dpd.setAccentColor(Color.parseColor("#9C27B0"));
            Calendar[] days = new Calendar[13];
            for (int i = -6; i < 7; i++) {
                Calendar day = Calendar.getInstance();
                day.add(Calendar.DAY_OF_MONTH, i * 2);
                days[i + 6] = day;
            }
            dpd.setSelectableDays(days);

            dpd.setOnCancelListener(dialog -> {
                Log.d("DatePickerDialog", "Dialog was cancelled");
                dpd = null;
            });

            // Add the date picker dialog to the FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.datePickerContainer, dpd)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        dateTextView.setText(date);
        dpd = null;
    }
}
