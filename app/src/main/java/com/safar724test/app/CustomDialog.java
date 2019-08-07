package com.safar724test.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.safar724test.app.enums.CustomFonts;
import com.safar724test.app.tools.Utils;

public class CustomDialog extends Dialog {
    private final Context context;

    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }

    public buttonClickListener listener;

    public interface buttonClickListener {
        void onButtonClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        Utils utils = new Utils(context);
        TextView msg = findViewById(R.id.msg_text_view);
        TextView exit = findViewById(R.id.exit_text_view);
        utils.setTextViewFont(msg, CustomFonts.REGULAR);
        utils.setTextViewFont(exit, CustomFonts.BOLD);
        exit.setOnClickListener(view -> listener.onButtonClicked());

    }
}
