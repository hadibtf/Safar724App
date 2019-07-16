package com.safar724test.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safar724test.app.R;
import com.safar724test.app.tools.Utils;

public class ConnectionErrorActivity extends AppCompatActivity {

    private TextView errorMessage;
    private TextView retry;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection_error_activty);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        utils = new Utils(this);
        errorMessage = findViewById(R.id.error_message);
        retry = findViewById(R.id.retry);
        utils.setTextViewFont(errorMessage,utils.REGULAR);
        utils.setTextViewFont(retry, utils.REGULAR);
    }


    public void retry(View view) {
        Toast.makeText(this, "Retry", Toast.LENGTH_SHORT).show();
    }
}
