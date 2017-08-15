package com.am.siri_ios_8_waveform;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.am.siriview.DrawView;
import com.am.siriview.UpdaterThread;

import java.io.IOException;

public class MediaRecordActivity extends AppCompatActivity {
    long REFRESH_INTERVAL_MS = 30;
    private boolean keepGoing = true;
    LinearLayout layout;
    MediaRecorder mRecorder;
    float tr = 400.0f;
    UpdaterThread up;
    DrawView view;

    public void startVoiceRecorder() {
        this.mRecorder = new MediaRecorder();
        this.mRecorder.setAudioSource(1);
        this.mRecorder.setOutputFormat(0);
        this.mRecorder.setAudioEncoder(0);
        this.mRecorder.setOutputFile("/dev/null");
        try {
            this.mRecorder.prepare();
        } catch (IOException e) {
            Log.e("sada", "prepare() failed");
        }
        this.mRecorder.start();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_recorder_layout);
        this.view = (DrawView) findViewById(R.id.root);
        startVoiceRecorder();
        new Thread(new Runnable() {
            public void run() {
                while (MediaRecordActivity.this.keepGoing) {
                    try {
                        Thread.sleep(Math.max(0, MediaRecordActivity.this.REFRESH_INTERVAL_MS - MediaRecordActivity.this.redraw()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private long redraw() {
        long t = System.currentTimeMillis();
        display_game();
        return System.currentTimeMillis() - t;
    }

    private void display_game() {
        runOnUiThread(new Runnable() {
            public void run() {
                MediaRecordActivity.this.view.setMaxAmplitude(((float) MediaRecordActivity.this.mRecorder.getMaxAmplitude()) * 1.6f);
            }
        });
    }
}
