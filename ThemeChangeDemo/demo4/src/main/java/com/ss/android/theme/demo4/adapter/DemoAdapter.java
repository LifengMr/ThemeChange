package com.ss.android.theme.demo4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ss.android.theme.demo4.R;
import com.ss.android.theme.loader.entity.ApkEntity;
import com.ss.android.theme.loader.helper.JavaHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenlifeng on 16/10/28.
 */
public class DemoAdapter extends BaseAdapter {

    private Context mContext;
    private List<ApkEntity> mData = new ArrayList<>();

    public DemoAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ApkEntity> data) {
        if (JavaHelper.isListEmpty(data)) {
            return;
        }

        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ApkEntity getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        DemoHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_theme, null);
            holder = new DemoHolder(mContext, view);
            view.setTag(holder);
        } else {
            holder = (DemoHolder) view.getTag();
        }

        ApkEntity entity = mData.get(position);
        holder.bind(entity);

        return view;
    }
}
