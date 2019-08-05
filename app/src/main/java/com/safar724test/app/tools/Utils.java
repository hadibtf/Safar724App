package com.safar724test.app.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

import com.safar724test.app.enums.CustomFonts;

public class Utils {
    private final Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public void setTextViewFont(TextView textView, CustomFonts font) {
        Typeface typeface;
        switch (font) {
            case ULTRA_LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_ultralight.ttf");
                textView.setTypeface(typeface);
                break;
            case LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_light.ttf");
                textView.setTypeface(typeface);
                break;
            case MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_medium.ttf");
                textView.setTypeface(typeface);
                break;
            case REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile.ttf");
                textView.setTypeface(typeface);
                break;
            case BOLD:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_bold.ttf");
                textView.setTypeface(typeface);
                break;
        }
    }

    public void setButtonFont(Button button, CustomFonts font) {
        Typeface typeface;
        switch (font) {
            case ULTRA_LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_ultralight.ttf");
                button.setTypeface(typeface);
                break;
            case LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_light.ttf");
                button.setTypeface(typeface);
                break;
            case MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_medium.ttf");
                button.setTypeface(typeface);
                break;
            case REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile.ttf");
                button.setTypeface(typeface);
                break;
            case BOLD:
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
