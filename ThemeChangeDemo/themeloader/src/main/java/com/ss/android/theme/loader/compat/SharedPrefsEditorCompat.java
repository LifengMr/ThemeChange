package com.ss.android.theme.loader.compat;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Compatibility utility for SharedPreferences.Editor. 
 *
 */
public class SharedPrefsEditorCompat {

    interface EditorImpl {
        public void apply(SharedPreferences.Editor editor);
    }

    static class BaseEditorImpl implements EditorImpl {

        @Override
        public void apply(SharedPreferences.Editor editor) {
            editor.commit();
        }
    }

    static class GingerbreadEditorImpl implements EditorImpl {

        @TargetApi(9)
        @Override
        public void apply(SharedPreferences.Editor editor) {
            editor.apply();
        }
    }

    static final EditorImpl IMPL;
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            IMPL = new GingerbreadEditorImpl();
        } else {
            IMPL = new BaseEditorImpl();
        }
    }

    /** try to commit changes asynchronously */
    public static void apply(SharedPreferences.Editor editor) {
        if (editor == null)
            return;
        IMPL.apply(editor);
    }

}
