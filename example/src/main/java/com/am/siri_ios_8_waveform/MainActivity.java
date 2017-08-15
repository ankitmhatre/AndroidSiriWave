package com.am.siri_ios_8_waveform;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    Button b1;
    Button b2;
    Button b3;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.RECORD_AUDIO"}, 213);
            return;
        }
        showElements();
    }

    public void showElements() {
        setContentView(R.layout.activity_main);
        this.b1 = (Button) findViewById(R.id.b1);
        this.b2 = (Button) findViewById(R.id.b2);
        this.b3 = (Button) findViewById(R.id.b3);
        this.b1.setOnClickListener(this);
        this.b2.setOnClickListener(this);
        this.b3.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b1 /*2131427422*/:
                startActivity(new Intent(this, GoogleSpeechActivity.class));
                return;
            case R.id.b2 /*2131427423*/:
                startActivity(new Intent(this, MediaRecordActivity.class));
                return;
            case R.id.b3 /*2131427424*/:
                startActivity(new Intent(this, AdjustableActivity.class));
                return;
            default:
                return;
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 213:
                if (grantResults[0] == 0) {
                    showElements();
                    return;
                } else {
                    setContentView(R.layout.need_permission);
                    findViewById(R.id.qwerty).setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.RECORD_AUDIO"}, 213);
                        }
                    });
                }
                return;
            default:
                return;
        }
    }
}
