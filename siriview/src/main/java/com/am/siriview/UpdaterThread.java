package com.am.siriview;

import android.app.Activity;

public class UpdaterThread extends Thread {
    int REFRESH_INTERVAL_MS;
    Activity c;
    float tr = 0.0f;
    DrawView v;

    public UpdaterThread(DrawView v, Activity c) {
        this.v = v;
        this.c = c;
        this.REFRESH_INTERVAL_MS = 30;
    }

    public UpdaterThread(int refreshTime, DrawView v, Activity c) {
        this.v = v;
        this.c = c;
        this.REFRESH_INTERVAL_MS = refreshTime;
    }

    public void setPRog(float prog) {
        this.tr = prog;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(Math.max(0, ((long) this.REFRESH_INTERVAL_MS) - redraw()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long redraw() {
        long t = System.currentTimeMillis();
        display_game();
        return System.currentTimeMillis() - t;
    }

    private void display_game() {
        this.c.runOnUiThread(new Runnable() {
            public void run() {
                UpdaterThread.this.v.setMaxAmplitude(UpdaterThread.this.tr);
            }
        });
    }
}
