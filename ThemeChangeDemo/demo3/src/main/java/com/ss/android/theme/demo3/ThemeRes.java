package com.ss.android.theme.demo3;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * Created by chenlifeng on 16/10/13.
 */
public class ThemeRes {

    // night res count 8, use load factor 0.75
    private final static int INIT_CAPACITY = 11;

    private static final HashMap<Integer, Integer> sMap = new HashMap<>(INIT_CAPACITY);

    static {
        sMap.put(R.color.s0, R.color.s0_night);
        sMap.put(R.color.s1, R.color.s1_night);
        sMap.put(R.color.s2, R.color.s2_night);
        sMap.put(R.color.s3, R.color.s3_night);
        sMap.put(R.color.s4, R.color.s4_night);

        sMap.put(R.string.btn_text, R.string.btn_text_night);
        sMap.put(R.mipmap.image, R.mipmap.image_night);
        sMap.put(R.drawable.bg_btn, R.drawable.bg_btn_night);
    }

    public static HashMap<Integer, Integer> getResMap() {
        return sMap;
    }

    public static int getId(int resId, boolean night) {
        if (!night) {
            return resId;
        }
        if (sMap.isEmpty()) {
            return resId;
        }

        Integer value = sMap.get(resId);
        if (value != null) {
            resId = value.intValue();
        }
        return resId;
    }

    public static String getString(Context context, int id, boolean night) {
        return context.getString(getId(id, night));
    }

    public static int getColor(Context context, int id, boolean night) {
        return context.getResources().getColor(getId(id, night));
    }

    public static ColorStateList getColorStateList(Context context, int id, boolean night) {
        return context.getResources().getColorStateList(getId(id, night));
    }

    public static Drawable getDrawable(Context context, int id, boolean night) {
        return context.getResources().getDrawable(getId(id, night));
    }
}
