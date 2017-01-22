package com.example.eric.myapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    boolean active = false;
    long temptime = 0;
    float alpha = new Float(0.5);
    float beta = new Float(1);
    int MY_PERMISSIONS_REQUEST_RECORD_AUDIO;
    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        final Chronometer mchronometer = (Chronometer) findViewById(R.id.timer);
        final ImageButton ibutton = (ImageButton) findViewById(R.id.record);
        final ImageButton stopbtn = (ImageButton) findViewById(R.id.stop);
        final ImageButton playbtn = (ImageButton) findViewById(R.id.play);
        final ImageButton pausebtn = (ImageButton) findViewById(R.id.pause);
        ibutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long startTime = SystemClock.elapsedRealtime();
                if (!active){
                    mchronometer.setBase(startTime - temptime);
                    mchronometer.start();
                    ibutton.setAlpha(alpha);
                    stopbtn.setVisibility(View.VISIBLE);
                    pausebtn.setVisibility(View.VISIBLE);

                }
                toggleRecording(active);
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if (active) {
                    toggleRecording(active);
                }
                mchronometer.stop();
                showResults(view);
            }
        });
        pausebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (active) {
                    mchronometer.stop();
                    ibutton.setAlpha(beta);
                    pausebtn.setVisibility(View.GONE);
                    playbtn.setVisibility(View.VISIBLE);
                    toggleRecording(active);
                }

            }
        });
        playbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (!active) {
                    mchronometer.start();
                    ibutton.setAlpha(alpha);
                    stopbtn.setVisibility(View.VISIBLE);
                    pausebtn.setVisibility(View.VISIBLE);
                }
                toggleRecording(active);
            }
        });

        final ProgressBar audio = (ProgressBar) findViewById(R.id.audiowave);
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int max = mRecorder.getMaxAmplitude();
                                    Log.d("hello", Integer.toString(max));
                                    if (max < 10000) {
                                        audio.setProgress(max);
                                    }
                                    else{
                                        audio.setProgress(10000);
                                    }
                                } catch(NullPointerException e){

                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }

    public void showResults(View view){
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    private MediaRecorder mRecorder = null;
    private static String mFileName = Environment.getExternalStorageDirectory() + File.separator
            + Environment.DIRECTORY_DCIM + File.separator + "Test_file";

    public void toggleRecording (boolean playing) {
        if (playing) {
            stopRecording();
            active = false;
        } else {
            startRecording();
            active = true;
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(e.toString(), "prepare() failed");
        }
        mRecorder.start();
    }

    private void stopRecording(){
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}
