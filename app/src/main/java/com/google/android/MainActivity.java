package com.google.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker timePicker;
    private  TextView textView;
    public static MainActivity instance;
    private ToggleButton toggleButton;

    @Override
    protected void onStart() {
        super.onStart();
        instance=this;
    }

    public static MainActivity getInstance(){
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView=findViewById(R.id.txt);
        timePicker=findViewById(R.id.time_picker);
        toggleButton=findViewById(R.id.toggle_btn);

        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);


        if (AlarmReciever.getRingtone()!=null){

            AlarmReciever.getRingtone().stop();
        }

    }

    public void onToggleButtonClicked(View view) {

        if (((ToggleButton)view).isChecked()){

            Calendar calendar= Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY , timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

            Intent intent=new Intent(MainActivity.this , AlarmReciever.class);
            pendingIntent=PendingIntent.getBroadcast(MainActivity.this , 0 , intent , 0);
            alarmManager.set(AlarmManager.RTC , calendar.getTimeInMillis(), pendingIntent);

            setAlarmText("wake up");
        }else {
            alarmManager.cancel(pendingIntent);
            setAlarmText(" ");

        }
    }


    public void setAlarmText(String  alarmText){
      textView.setText(alarmText);
    }


}
