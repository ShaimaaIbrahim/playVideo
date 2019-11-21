package com.google.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.audiorecord.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String WRITE_external_storage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String RADIO_RECORDER = Manifest.permission.RECORD_AUDIO;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String path="";
    private static final int RECORDER_REQUEST_CODE=100;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding= DataBindingUtil.setContentView(this , R.layout.activity_main);

        if (!checkPermissionForAndroid()){
            requestPermissions();
        }


            binding.playRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkPermissionForAndroid()) {
                        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.3gp";

                        setUpMediaRecorder();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        binding.playFile.setEnabled(false);
                        binding.stopFile.setEnabled(false);

                        Toast.makeText(MainActivity.this, "Recording ", Toast.LENGTH_LONG).show();
                    } else {
                        requestPermissions();
                    }
                }
            });

            binding.stopRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaRecorder.stop();

                    binding.stopRecord.setEnabled(false);
                    binding.playRecord.setEnabled(true);
                    binding.playFile.setEnabled(true);
                    binding.stopFile.setEnabled(false);
                }
            });

            binding.playFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.stopFile.setEnabled(true);
                    binding.playRecord.setEnabled(false);
                    binding.stopRecord.setEnabled(false);

                    mediaPlayer=new MediaPlayer();

                    try {

                        mediaPlayer.setDataSource(path);
                        mediaPlayer.prepare();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this , "playing-- " , Toast.LENGTH_LONG).show();
                }
            });

            binding.stopFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.stopRecord.setEnabled(false);
                    binding.playRecord.setEnabled(true);
                    binding.playFile.setEnabled(false);

                    if (mediaPlayer!=null){
       mediaPlayer.stop();
       mediaPlayer.release();
       setUpMediaRecorder();
                    }
                }
            });

    }

    private void setUpMediaRecorder() {

        mediaRecorder=new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(path);
    }

    private void requestPermissions() {

        ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.RECORD_AUDIO}, RECORDER_REQUEST_CODE);
    }

    private boolean checkPermissionForAndroid() {
 int WRITE_EXTERNAL_STORAGE_RESULT= ContextCompat.checkSelfPermission(this , WRITE_external_storage);
 int AUDIO_RECORDER_RESULT= ContextCompat.checkSelfPermission(this , RADIO_RECORDER);

 return WRITE_EXTERNAL_STORAGE_RESULT== PackageManager.PERMISSION_GRANTED && AUDIO_RECORDER_RESULT==PackageManager.PERMISSION_GRANTED;

    }


    public void stopRecord(View view) {
    }

    public void playFile(View view) {
    }

    public void stopFile(View view) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case RECORDER_REQUEST_CODE :
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this , "Permissin granted", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this , "Permissin denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void startRecord(View view) {
    }
}
