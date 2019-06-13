package com.safar724test.app.activities;

import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.safar724test.app.R;

public class ConnectionErrorActivity extends AppCompatActivity {
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection_error_activty);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        errorTextView = findViewById(R.id.err_text_view);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(errorTextView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
    }

    public void goBack(View view) {
        finish();
    }
}
