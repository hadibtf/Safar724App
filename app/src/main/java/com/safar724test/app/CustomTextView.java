package com.safar724test.app;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
//        Typeface ultra_light = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_ultralight.ttf");
//        Typeface light = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_light.ttf");
//        Typeface medium = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_medium.ttf");
//        Typeface regular = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile.ttf");
        Typeface bold = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_bold.ttf");
        this.setTypeface(bold);
//        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextView);
//        int font = typedArray.getInt(R.styleable.CustomTextView_custom_font,0);
//        switch (font) {
//            case 0:
//                this.setTypeface(ultra_light);
//                break;
//            case 1:
//                this.setTypeface(light);
//                break;
//            case 2:
//                this.setTypeface(medium);
//                break;
//            case 3:
//                this.setTypeface(regular);
//                break;
//            case 4:
//                this.setTypeface(bold);
//                break;
//
//        }
//        typedArray.recycle();
    }


}
