package com.safar724test.app.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class Utils {
    public final Context context;
    public final int ULTRA_LIGHT = 0;
    public final int LIGHT = 1;
    public final int MEDIUM = 2;
    public final int REGULAR = 3;
    public final int BOLD = 4;


    public Utils(Context context) {
        this.context = context;
    }

    public void setTextViewFont(TextView textView, int font) {
        Typeface typeface;
        switch (font) {
            case 0:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_ultralight.ttf");
                textView.setTypeface(typeface);
                break;
            case 1:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_light.ttf");
                textView.setTypeface(typeface);
                break;
            case 2:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_medium.ttf");
                textView.setTypeface(typeface);
                break;
            case 3:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile.ttf");
                textView.setTypeface(typeface);
                break;
            case 4:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_bold.ttf");
                textView.setTypeface(typeface);
                break;
        }
    }

    public void setButtonFont(Button button, int font) {
        Typeface typeface;
        switch (font) {
            case 0:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_ultralight.ttf");
                button.setTypeface(typeface);
                break;
            case 1:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_light.ttf");
                button.setTypeface(typeface);
                break;
            case 2:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_medium.ttf");
                button.setTypeface(typeface);
                break;
            case 3:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile.ttf");
                button.setTypeface(typeface);
                break;
            case 4:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_bold.ttf");
                button.setTypeface(typeface);
                break;
        }
    }

    public String faToEn(String num) {
        return num
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹");
    }

}
