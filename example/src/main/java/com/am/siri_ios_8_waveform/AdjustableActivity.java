package com.am.siri_ios_8_waveform;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.am.siriview.DrawView;
import com.am.siriview.UpdaterThread;

public class AdjustableActivity extends AppCompatActivity implements OnSeekBarChangeListener {
    long REFRESH_INTERVAL_MS = 30;
    private boolean keepGoing = true;
    LinearLayout layout;
    SeekBar s1;
    SeekBar s2;
    float tr = 400.0f;
    UpdaterThread up;
    DrawView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjustable);
        this.s1 = findViewById(R.id.seekBar);
        this.s2 = findViewById(R.id.seekBar2);
        this.s1.setOnSeekBarChangeListener(this);
        this.s2.setOnSeekBarChangeListener(this);
        this.view =  findViewById(R.id.root_adjust);
        this.up = new UpdaterThread(30, this.view, this);
        this.up.start();
        new Thread(new Runnable() {
            public void run() {
                while (AdjustableActivity.this.keepGoing) {
                    try {
                        Thread.sleep(Math.max(0, AdjustableActivity.this.REFRESH_INTERVAL_MS - AdjustableActivity.this.redraw()));
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
                AdjustableActivity.this.view.setMaxAmplitude(AdjustableActivity.this.tr);
            }
        });
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekBar /*2131427426*/:
                this.tr = (float) i;
                break;
            case R.id.seekBar2 /*2131427427*/:
                this.REFRESH_INTERVAL_MS = (long) i;
                break;
            default:
                break;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
