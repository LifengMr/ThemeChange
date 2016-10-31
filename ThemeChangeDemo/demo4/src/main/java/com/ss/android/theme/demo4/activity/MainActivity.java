package com.ss.android.theme.demo4.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.ss.android.theme.demo4.R;
import com.ss.android.theme.demo4.adapter.DemoAdapter;
import com.ss.android.theme.loader.api.ApkerFlow;
import com.ss.android.theme.loader.api.FlowCallback;
import com.ss.android.theme.loader.entity.ApkBean;
import com.ss.android.theme.loader.helper.JLog;
import com.ss.android.theme.loader.helper.JavaHelper;
import com.ss.android.theme.loader.volley.VolleyError;

public class MainActivity extends BaseActivity implements FlowCallback<ApkBean> {

    private ListView mListView;

    private DemoAdapter mAdapter;
    private ApkerFlow mFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();

        requestThemes();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.main_list);

        mAdapter = new DemoAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    private void requestThemes() {
        mFlow = new ApkerFlow(this);
        mFlow.requestPlugins(this);
    }

    @Override
    public void onFlowSuccess(ApkBean data) {
        if (data == null || JavaHelper.isListEmpty(data.apks)) {
            return;
        }

        mAdapter.setData(data.apks);
    }

    @Override
    public void onFlowError(VolleyError error) {
        JLog.i("clf", "onFlowError");
        Toast.makeText(this, R.string.request_theme_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFlow != null) {
            mFlow.onDestroy();
        }
    }
}
