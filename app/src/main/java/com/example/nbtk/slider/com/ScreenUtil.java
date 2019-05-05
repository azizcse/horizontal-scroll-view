package com.example.nbtk.slider.com;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class ScreenUtil {
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int dpToPx(Context context, int value) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Float.valueOf(value), context.getResources().getDisplayMetrics());
    }
}
