package krunal.com.example.simplenotificationapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText mSetTime ,mSetTime2;
    private Button mSendNotification,mSendNotification2;

    private int mHour,mMinte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSetTime = findViewById(R.id.editTexttime);
        final Calendar calendar = Calendar.getInstance();
        mSetTime2 = findViewById(R.id.editTexttime2);

        mSendNotification2 = findViewById(R.id.buttonsend2);

        mSendNotification = findViewById(R.id.Sentnptification);

        mSendNotification.setOnClickListener(v -> {
            String getsetTime = mSetTime.getText().toString();
            // Checking if editText is Empty.
            if(getsetTime.matches("")) {
                Toast.makeText(this,"You did not set time!", Toast.LENGTH_LONG).show();
            }else {
                int time = Integer.parseInt(mSetTime.getText().toString());
                Intent intent = new Intent(MainActivity.this, Notification.class);
                PendingIntent p1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                AlarmManager a = (AlarmManager) getSystemService(ALARM_SERVICE);
                a.set(AlarmManager.RTC, System.currentTimeMillis() + time * 1000, p1);
            }

        });

        mSetTime2.setOnClickListener(v -> {
            final Calendar calendar1 = Calendar.getInstance();

            mHour = calendar1.get(Calendar.HOUR_OF_DAY);
            mMinte = calendar1.get(Calendar.MINUTE);

            @SuppressLint("SetTextI18n") TimePickerDialog timePicker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                updateTime(hourOfDay,minute);

            },mHour,mMinte,false);
            timePicker.show();
        });


        mSendNotification2.setOnClickListener(v -> {
            String getsetTime2 = mSetTime2.getText().toString();
            // Checking if editText is Empty.
            if (getsetTime2.matches("")){
                Toast.makeText(this,"Select Time!",Toast.LENGTH_LONG).show();
            }else {
                Log.d("time in Millis",String.valueOf(calendar.getTimeInMillis()));
                Intent intent = new Intent(MainActivity.this, Notification2.class);
                PendingIntent p1=PendingIntent.getBroadcast(getApplicationContext(),0, intent,0);
                AlarmManager a=(AlarmManager)getSystemService(ALARM_SERVICE);
                a.set(AlarmManager.RTC,calendar.getTimeInMillis(),p1);
            }



        });


    }


    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        mSetTime2.setText(aTime);
    }
}
