package com.ss.android.theme.loader.compat;

import android.view.LayoutInflater;

import com.ss.android.theme.loader.helper.ReflectHelper;

import java.lang.reflect.Field;

/**
 * Created by chenlifeng on 16/10/24.
 */
public class ThemeLayoutInflaterCompat {

    public static void setFactorySet(LayoutInflater layoutInflater, boolean factorySet) {
        Field field = ReflectHelper.getField(LayoutInflater.class, "mFactorySet", false);
        ReflectHelper.writeField(field, layoutInflater, factorySet);
    }
}
