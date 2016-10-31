package com.ss.android.theme.loader.entity;

import android.view.View;

import com.ss.android.theme.loader.helper.JLog;
import com.ss.android.theme.loader.helper.JavaHelper;

import java.util.List;

/**
 * Created by chenlifeng on 16/10/25.
 */
public class ThemeViewEntity {
    public View view;
    public List<BaseAttr> attrs;

    public void apply() {
        JLog.i("clf", "onThemeChanged attrs=" + attrs);
        if (JavaHelper.isListEmpty(attrs)) {
            return;
        }

        for (BaseAttr attr : attrs) {
            attr.apply(view);
        }
    }

    public void destroy() {
        if (JavaHelper.isListEmpty(attrs)) {
            return;
        }

        attrs.clear();
    }
}
