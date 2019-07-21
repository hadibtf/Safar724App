package com.safar724test.app.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.safar724test.app.R;
import com.safar724test.app.tools.Utils;

public class ConnectionErrorActivity extends AppCompatActivity {

    private boolean t = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_connection_error_activty);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        final Utils utils = new Utils(this);
        TextView errorMessage = findViewById(R.id.error_message);
        TextView retry = findViewById(R.id.retry);
        utils.setTextViewFont(errorMessage, utils.REGULAR);
        utils.setTextViewFont(retry, utils.REGULAR);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Hello world!").setTitle("Error").setPositiveButton("yes",
                (dialog, which) -> {
                    dialog.dismiss();
//                    finish();
                }).show();
    }

    public void retry(View view) {
        recreate();
        Toast.makeText(this, "Retry", Toast.LENGTH_SHORT).show();
    }

}
