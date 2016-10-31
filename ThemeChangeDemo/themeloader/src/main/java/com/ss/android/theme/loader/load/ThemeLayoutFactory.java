package com.ss.android.theme.loader.load;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ss.android.theme.loader.entity.AttrFactory;
import com.ss.android.theme.loader.entity.BaseAttr;
import com.ss.android.theme.loader.entity.ThemeViewEntity;
import com.ss.android.theme.loader.helper.JLog;
import com.ss.android.theme.loader.helper.JavaHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenlifeng on 16/10/24.
 */
public class ThemeLayoutFactory implements LayoutInflater.Factory {
    public static final String TAG = ThemeLayoutFactory.class.getName();

    private List<ThemeViewEntity> mViewEntities = new ArrayList<>();

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {

        JLog.i(TAG, "onCreateView name=" + name);
        View view = null;

        try {
            view = createView(name, context, attributeSet);
        } catch (Exception e) {
            JLog.i(TAG, "onCreateView e=" + e.getMessage());
        }

        JLog.i(TAG, "onCreateView view=" + view);
        parseAttrs(context, view, attributeSet);

        return view;
    }

    private View createView(String name, Context context, AttributeSet attributeSet)
            throws ClassNotFoundException {
        View view = null;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (-1 == name.indexOf(".")) {
            if(name.equals("Surface") || name.equals("SurfaceHolder") || name.equals("SurfaceView")
                    || name.equals("TextureView") || name.equals("View")){
                view = layoutInflater.createView(name, "android.view.", attributeSet);
            } else if (name.equals("WebView")) {
                view = layoutInflater.createView(name, "android.webkit.", attributeSet);
            } else {
                view = layoutInflater.createView(name, "android.widget.", attributeSet);
            }
        } else {
            view = layoutInflater.createView(name, null, attributeSet);
        }

        return view;
    }

    public void apply() {
        JLog.i("clf", "onThemeChanged mViewEntities=" + mViewEntities);
        if (JavaHelper.isListEmpty(mViewEntities)) {
            return;
        }

        for (ThemeViewEntity entity : mViewEntities) {
            entity.apply();
        }
    }

    public void destroy() {
        if (JavaHelper.isListEmpty(mViewEntities)) {
            return;
        }

        for (ThemeViewEntity entity : mViewEntities) {
            entity.destroy();
        }

        mViewEntities.clear();
    }

    public void parseAttrs(Context context, View view, AttributeSet attributeSet) {
        List<BaseAttr> attrs = new ArrayList<>();
        JLog.i(TAG, "onCreateView attributeSet.getAttributeCount()=" + attributeSet.getAttributeCount());
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            String attrName = attributeSet.getAttributeName(i);
            String attrValue = attributeSet.getAttributeValue(i);

            JLog.i(TAG, "parseAttrs attrName=" + attrName + ",view=" + view);
            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue;
            }

            JLog.i(TAG, "parseAttrs attrValue=" + attrValue);
            if (attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    BaseAttr attr = AttrFactory.create(attrName, id, entryName, typeName);
                    JLog.i(TAG, "parseAttrs attr=" + attr);
                    if (attr != null) {
                        attrs.add(attr);
                    }
                } catch (Exception e) {
                    JLog.i(TAG, "parseAttrs e=" + e.getMessage());
                }
            }

            JLog.i(TAG, "parseAttrs attrs.size=" + attrs.size());
            if (!JavaHelper.isListEmpty(attrs)) {
                ThemeViewEntity entity = new ThemeViewEntity();
                entity.view = view;
                entity.attrs = attrs;

                mViewEntities.add(entity);
            }
        }
    }

}
