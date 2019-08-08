package com.safar724test.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;


public class CustomDialog extends Dialog {
    public CustomDialogButtonClick customDialogButtonClick;

    public interface CustomDialogButtonClick {
        void onCustomDialogButtonClicked();
    }

    public CustomDialog(Context context, CustomDialogButtonClick customDialogButtonClick) {
        super(context);
        this.customDialogButtonClick = customDialogButtonClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.custom_dialog);
        getWindow().setGravity(Gravity.CENTER);
        TextView exit = findViewById(R.id.exit_text_view);
        exit.setOnClickListener(view -> customDialogButtonClick.onCustomDialogButtonClicked());
    }
}
