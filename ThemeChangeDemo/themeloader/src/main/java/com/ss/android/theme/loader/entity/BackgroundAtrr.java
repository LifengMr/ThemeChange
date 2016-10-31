package com.ss.android.theme.loader.entity;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.ss.android.theme.loader.helper.JLog;
import com.ss.android.theme.loader.load.ThemeLoader;

/**
 * Created by chenlifeng on 16/10/24.
 */
public class BackgroundAtrr extends BaseAttr {

    @Override
    public void apply(View view) {
        if (view == null) {
            return;
        }
        if (ATTR_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            view.setBackgroundColor(ThemeLoader.inst().getColor(attrValueRefId));
        } else if (ATTR_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
            Drawable drawable = ThemeLoader.inst().getDrawable(attrValueRefId);
            view.setBackground(drawable);
        } else if (ATTR_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
            Drawable drawable = ThemeLoader.inst().getMipmap(attrValueRefId);
            view.setBackground(drawable);
        }
    }
}
