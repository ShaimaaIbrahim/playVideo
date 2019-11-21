package com.google.playvideo;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.model.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.brightcove.player.view.BrightcovePlayer;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity  {

    private VideoView videoView;
    private TextView t ;
    private TextView t1;
    private ImageButton imageButton;

//https://documenter.getpostman.com/view/3773593/SW7UcWBy?version=latest#f244861f-6f25-4f41-9d96-09f9ff7b56c9
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


    }
}
