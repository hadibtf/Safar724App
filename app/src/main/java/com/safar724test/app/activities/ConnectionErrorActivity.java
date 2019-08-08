package com.safar724test.app.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safar724test.app.R;

public class ConnectionErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection_error_activty);
        super.onCreate(savedInstanceState);
    }

    public void retry(View view) {
        Toast.makeText(this, "Retry", Toast.LENGTH_SHORT).show();
    }
}
