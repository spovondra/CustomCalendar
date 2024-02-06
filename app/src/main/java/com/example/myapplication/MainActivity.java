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
    private CheckBox modeDarkDate;
    private CheckBox modeCustomAccentDate;
    private CheckBox dismissDate;
    private CheckBox limitSelectableDays;
    private CheckBox highlightDays;
    private DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find our View instances
        dateTextView = findViewById(R.id.date_textview);
        Button dateButton = findViewById(R.id.date_button);
        modeDarkDate = findViewById(R.id.mode_dark_date);
        modeCustomAccentDate = findViewById(R.id.mode_custom_accent_date);
        dismissDate = findViewById(R.id.dismiss_date);
        limitSelectableDays = findViewById(R.id.limit_dates);
        highlightDays = findViewById(R.id.highlight_dates);

        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();

            if (dpd == null) {
                dpd = DatePickerDialog.newInstance(
                        MainActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
            } else {
                dpd.initialize(
                        MainActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
            }
            dpd.setThemeDark(modeDarkDate.isChecked());
            dpd.dismissOnPause(dismissDate.isChecked());
            if (modeCustomAccentDate.isChecked()) {
                dpd.setAccentColor(Color.parseColor("#9C27B0"));
            }
            if (highlightDays.isChecked()) {
                Calendar date1 = Calendar.getInstance();
                Calendar date2 = Calendar.getInstance();
                date2.add(Calendar.WEEK_OF_MONTH, -1);
                Calendar date3 = Calendar.getInstance();
                date3.add(Calendar.WEEK_OF_MONTH, 1);
                Calendar[] days = {date1, date2, date3};
                dpd.setHighlightedDays(days);
            }
            if (limitSelectableDays.isChecked()) {
                Calendar[] days = new Calendar[13];
                for (int i = -6; i < 7; i++) {
                    Calendar day = Calendar.getInstance();
                    day.add(Calendar.DAY_OF_MONTH, i * 2);
                    days[i + 6] = day;
                }
                dpd.setSelectableDays(days);
            }

            dpd.setOnCancelListener(dialog -> {
                Log.d("DatePickerDialog", "Dialog was cancelled");
                dpd = null;
            });
            dpd.show(getSupportFragmentManager(), "Datepickerdialog");
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        dateTextView.setText(date);
        dpd = null;
    }
}
