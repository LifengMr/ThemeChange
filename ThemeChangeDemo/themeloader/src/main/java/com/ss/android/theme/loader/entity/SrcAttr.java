package com.ss.android.theme.loader.entity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.ss.android.theme.loader.load.ThemeLoader;

/**
 * Created by chenlifeng on 16/10/31.
 */
public class SrcAttr extends BaseAttr {

    @Override
    public void apply(View view) {
        if (view == null) {
            return;
        }

        if (view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            if (ATTR_TYPE_NAME_DRAWABLE.equals(attrValueRefName)) {
                Drawable drawable = ThemeLoader.inst().getDrawable(attrValueRefId);
                iv.setImageDrawable(drawable);
            } else if (ATTR_TYPE_NAME_MIPMAP.equals(attrValueTypeName)) {
                Drawable drawable = ThemeLoader.inst().getMipmap(attrValueRefId);
                iv.setImageDrawable(drawable);
            }
        }
    }
}
