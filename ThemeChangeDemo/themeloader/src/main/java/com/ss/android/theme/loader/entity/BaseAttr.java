package com.ss.android.theme.loader.entity;

import android.view.View;

/**
 * Created by chenlifeng on 16/10/24.
 */
public abstract class BaseAttr {
    public static final String ATTR_TYPE_NAME_COLOR = "color";
    public static final String ATTR_TYPE_NAME_DRAWABLE = "drawable";
    public static final String ATTR_TYPE_NAME_MIPMAP = "mipmap";

    public String attrName;
    public int attrValueRefId;
    public String attrValueRefName;
    public String attrValueTypeName;

    public abstract void apply(View view);
}
