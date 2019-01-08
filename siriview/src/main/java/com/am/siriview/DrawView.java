package com.am.siriview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class DrawView extends LinearLayout {
    private int ViewHeight = 0;
    private float ViewMid = 0.0f;
    private int ViewWidth = 0;
    public float amplitude = 0.5f;
    public float density = 1.0f;
    private boolean drawLock = false;
    public float frequency = 1.2f;
    public float maxAmplitude = 0.0f;
    public int numberOfWaves;
    private ArrayList<Paint> paintsArray = new ArrayList<>();
    private Path path2;
    public float phase = 0.0f;
    public float phaseShift = -0.25f;
    int refreshInterval = 30;
    int waveColor;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs, defStyleAttr);
    }

    void initShit() {
        Resources res = getResources();
        for (int i = 0; i < this.numberOfWaves; i++) {
            float multiplier = Math.min(1.0f, (((1.0f - (((float) i) / ((float) this.numberOfWaves))) / 3.0f) * 2.0f) + 0.33333334f);
            Paint p;
            if (i == 0) {
                p = new Paint(1);
                p.setColor(this.waveColor);
                p.setStrokeWidth(res.getDimension(R.dimen.waver_width));
                p.setStyle(Style.STROKE);
                this.paintsArray.add(p);
            } else {
                p = new Paint(1);
                Log.v("Color", BuildConfig.FLAVOR + ((int) ((((double) (1.0f * multiplier)) * 0.7d) * 255.0d)));
                p.setColor(this.waveColor);
                p.setAlpha((int) ((((double) (1.0f * multiplier)) * 0.8d) * 255.0d));
                p.setStrokeWidth(res.getDimension(R.dimen.waver_width_min));
                p.setStyle(Style.STROKE);
                this.paintsArray.add(p);
            }
        }
        this.path2 = new Path();
    }

    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        this.ViewWidth = xNew;
        this.ViewHeight = yNew;
        this.ViewMid = ((float) this.ViewWidth) / 2.0f;
        this.maxAmplitude = (((float) this.ViewHeight) / 2.0f) - 4.0f;
        Log.v("Waver", "width=" + this.ViewWidth);
    }

    public void setMaxAmplitude(float mAmpli) {
        this.phase += this.phaseShift;
        this.amplitude = (this.amplitude + Math.max(mAmpli / 5590.5337f, 0.01f)) / 2.0f;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.drawLock) {
            this.drawLock = true;
            for (int i = 0; i < this.numberOfWaves; i++) {
                float normedAmplitude = ((1.5f * (1.0f - (((float) i) / ((float) this.numberOfWaves)))) - 0.5f) * this.amplitude;
                this.path2.reset();
                float x = 0.0f;
                while (x < ((float) this.ViewWidth) + this.density) {
                    double y = (((((double) this.maxAmplitude) * ((-Math.pow((double) ((x / this.ViewMid) - 1.0f), 2.0d)) + 1.0d)) * ((double) normedAmplitude)) * Math.sin(((6.282d * ((double) (x / ((float) this.ViewWidth)))) * ((double) this.frequency)) + ((double) this.phase))) + (((double) this.ViewHeight) / 2.0d);
                    if (x == 0.0f) {
                        this.path2.moveTo(x, (float) y);
                    } else {
                        this.path2.lineTo(x, (float) y);
                    }
                    x += this.density;
                }
                Paint p = this.paintsArray.get(i);
                canvas.drawPath(this.path2, p);
            }
            this.drawLock = false;
        }
    }

    private void initialize(Context c, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.SiriWaveView, defStyleAttr, 0);
        this.waveColor = a.getColor(R.styleable.SiriWaveView_wave_color, -1);
        this.numberOfWaves = a.getInteger(R.styleable.SiriWaveView_wave_count, 5);
        this.refreshInterval = a.getInteger(R.styleable.SiriWaveView_waveRefreshInterval, 30);
        a.recycle();
        initShit();
    }

    public void setWaveColor(int wCol) {
        this.waveColor = wCol;
    }

    public void setNumberOfWaves(int nWaves) {
        this.numberOfWaves = nWaves;
    }
}
