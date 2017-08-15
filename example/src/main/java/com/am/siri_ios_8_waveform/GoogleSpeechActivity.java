package com.am.siri_ios_8_waveform;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.am.siriview.DrawView;
import com.am.siriview.UpdaterThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;



public class GoogleSpeechActivity extends AppCompatActivity implements RecognitionListener {
    private static final int REFRESH_INTERVAL_MS = 30;
    private static final int REQUEST_PERMISSION_RECORD_AUDIO = 1;
    ImageView imageView;
    private boolean keepGoing = true;
    DrawView layout;
    String resultSpeech;
    TextView resultText;
    SpeechRecognizer speechRecognizer;
    float tr = 0.0f;
    UpdaterThread up;
    private DrawView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_speech_layout);
        this.layout = (DrawView) findViewById(R.id.root_google);
        this.up = new UpdaterThread(REFRESH_INTERVAL_MS, this.layout, this);
        this.resultText = (TextView) findViewById(R.id.tView);
        this.up.start();
        this.imageView = (ImageView) findViewById(R.id.centerImage1);
        this.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GoogleSpeechActivity.this.checkPermissionsAndStart();
            }
        });
    }

    public void onReadyForSpeech(Bundle bundle) {
        this.imageView.setVisibility(View.INVISIBLE);
        this.layout.setVisibility(View.VISIBLE);
    }

    public void onBeginningOfSpeech() {
    }

    public void onRmsChanged(float v) {
        v = Math.abs(v);
        double a = Math.pow(20.0d, ((double) v) / 14.14d);
        this.tr = ((float) a) * 100.0f;
        Log.d("RMS", v + " = " + a);
        this.up.setPRog(this.tr * 1.8f);
    }

    public void onBufferReceived(byte[] bytes) {
    }

    public void onEndOfSpeech() {
        this.tr = 0.0f;
    }

    public void onPartialResults(Bundle bundle) {
        for (String key : bundle.keySet()) {
            Log.d("myApplication partial", key + " : " + bundle.get(key));
        }
    }

    public void onEvent(int i, Bundle bundle) {
    }

    public void onError(int i) {
        this.imageView.setVisibility(View.VISIBLE);
        this.layout.setVisibility(View.INVISIBLE);
        this.tr = 0.0f;
        Log.d("Error Recording", BuildConfig.FLAVOR + i);
    }

    public void onResults(Bundle bundle) {
        this.speechRecognizer.stopListening();
        this.imageView.setVisibility(View.VISIBLE);
        this.layout.setVisibility(View.INVISIBLE);
        this.tr = 0.0f;
        ArrayList<String> arraylist = bundle.getStringArrayList("results_recognition");
        Iterator it = arraylist.iterator();
        while (it.hasNext()) {
            Log.d("myApplication", (String) it.next());
        }
        this.resultSpeech = ((String) arraylist.get(0)).toLowerCase();
        this.resultText.setText(this.resultSpeech);
    }

    public void startVoiceRecorder() {
        try {
            Log.d("VoiceREcorder", "started");
            try {
                this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
                this.speechRecognizer.setRecognitionListener(this);
                Intent speechIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                speechIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                speechIntent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
                speechIntent.putExtra("calling_package", getPackageName());
                this.speechRecognizer.startListening(speechIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("VoiceRecorder", "startedListening");
        } catch (Exception e2) {
            Toast.makeText(this, "You don't have any voice recognizer app installed, Download from Google Play store", Toast.LENGTH_SHORT).show();
            e2.printStackTrace();
        }
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        if (this.speechRecognizer != null) {
            this.speechRecognizer.stopListening();
        }
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.speechRecognizer != null) {
            this.speechRecognizer.stopListening();
            this.speechRecognizer.destroy();
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.speechRecognizer != null) {
            this.speechRecognizer.stopListening();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_RECORD_AUDIO /*1*/:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    finish();
                    return;
                } else {
                    checkPermissionsAndStart();
                    return;
                }
            default:
                return;
        }
    }

    private void checkPermissionsAndStart() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE"}, REQUEST_PERMISSION_RECORD_AUDIO);
            return;
        }
        startVoiceRecorder();
    }
}
