package com.ss.android.theme.loader.entity;

import android.view.View;
import android.widget.TextView;

import com.ss.android.theme.loader.helper.JLog;
import com.ss.android.theme.loader.load.ThemeLoader;

/**
 * Created by chenlifeng on 16/10/25.
 */
public class TextColorAttr extends BaseAttr {

    public static final String TAG = TextColorAttr.class.getName();

    //TextColorAttr
    @Override
    public void apply(View view) {
        JLog.i(TAG, "TextColorAttr view=" + view);
        if (view == null) {
            return;
        }
        if (view instanceof TextView) {
            JLog.i(TAG, "TextColorAttr attrValueTypeName=" + attrValueTypeName);
            TextView textView = (TextView)view;
            if(ATTR_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                textView.setTextColor(ThemeLoader.inst().getColorStateList(attrValueRefId));
            }
        }
    }
}
