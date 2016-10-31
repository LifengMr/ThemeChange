package com.ss.android.theme.loader.entity;

/**
 * Created by chenlifeng on 16/10/24.
 */
public class AttrFactory {

    public static final String ATTR_NAME_BACKGROUND = "background";
    public static final String ATTR_NAME_TEXT_COLOR = "textColor";
    public static final String ATTR_NAME_SRC = "src";

    public static BaseAttr create(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {
        BaseAttr attr = null;

        if (ATTR_NAME_BACKGROUND.equals(attrName)) {
            attr = new BackgroundAtrr();
        } else if (ATTR_NAME_TEXT_COLOR.equals(attrName)) {
            attr = new TextColorAttr();
        } else if (ATTR_NAME_SRC.equals(attrName)) {
            attr = new SrcAttr();
        }

        attr.attrName = attrName;
        attr.attrValueRefId = attrValueRefId;
        attr.attrValueRefName = attrValueRefName;
        attr.attrValueTypeName = typeName;
        return attr;
    }

    public static boolean isSupportedAttr(String attrName) {
        if (ATTR_NAME_BACKGROUND.equals(attrName) || ATTR_NAME_TEXT_COLOR.equals(attrName)
                || ATTR_NAME_SRC.equals(attrName)) {
            return true;
        }

        return false;
    }
}
