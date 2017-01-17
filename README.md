##Android客户端主题切换
&emsp;&emsp;Android客户端主题切换主要通过更改图片、颜色值等来改变UI效果，典型场景就是夜间模式的使用。本文也将以夜间模式为切入点，分析目前主要的几种实现方式。Support Lib 23.2.0提供了Theme.AppCompat.DayNight主题，但只适配API14及以上系统，这里不做过多介绍。

  | 实现方式  | 应用app | Demo
----|------------- | -------------|-----
1 | 设置主题style      | 内涵段子 |  demo1
2 | 设置res-night资源  | XX      | demo2
3 | themeloader库(LayoutInflater.Factory)  | 暂无      | demo4
4 | java代码手动切换资源  | 今日头条      | demo3

#####一、设置主题style
1、创建Day和Night两种主题style，分别对应日间和夜间样式
```
<style name="DayNightTheme" parent="AppTheme" >
    <item name="s0">@color/s0</item>
    <item name="s1">@color/s1</item>
    <item name="s2">@color/s2</item>
    <item name="s3">@color/s3</item>
    <item name="s4">@color/s4</item>

    <item name="mainBackground">@color/s0</item>
    <item name="btnBackground">@drawable/bg_btn</item>
    <item name="image">@mipmap/image</item>
    <item name="btnStr">@string/btn_text</item>
</style>

<style name="DayNightTheme.Night">
    <item name="s1">@color/s1_night</item>
    <item name="s2">@color/s2_night</item>
    <item name="s3">@color/s3_night</item>
    <item name="s4">@color/s4_night</item>

    <item name="mainBackground">@color/s0_night</item>
    <item name="btnBackground">@drawable/bg_btn_night</item>
    <item name="image">@mipmap/image_night</item>
    <item name="btnStr">@string/btn_text_night</item>
</style>
```
2、java代码设置theme切换夜间模式
```
public void apply(Context context) {
    if (mIsNight) {
        context.setTheme(R.style.DayNightTheme_Night);
    } else{
        context.setTheme(R.style.DayNightTheme);
    }
}
```
#####二、设置res-night资源
1、在res资源中配置night资源
mipmap-night
values-night
etc.
2、java代码清除缓存资源并切夜间模式
```
public void toggle() {
    if (sUiNightMode == Configuration.UI_MODE_NIGHT_YES) {
        notNight();
    } else {
        night();
    }
}
public void night() {
    updateConfig(Configuration.UI_MODE_NIGHT_YES);
    System.gc();
    System.runFinalization(); 
    System.gc();
    mActivity.get().recreate();
}
```
#####三、设置主题style
1、配置主题资源
```
static {
    sMap.put(R.color.s0, R.color.s0_night);
    sMap.put(R.color.s1, R.color.s1_night);
    sMap.put(R.color.s2, R.color.s2_night);
    sMap.put(R.color.s3, R.color.s3_night);
    sMap.put(R.color.s4, R.color.s4_night);

    sMap.put(R.string.btn_text, R.string.btn_text_night);
    sMap.put(R.mipmap.image, R.mipmap.image_night);
    sMap.put(R.drawable.bg_btn, R.drawable.bg_btn_night);
}
```
2、根据主题获取资源
```
public static int getId(int resId, boolean night) {
    if (!night) {
        return resId;
    }
    if (sMap.isEmpty()) {
        return resId;
    }

    Integer value = sMap.get(resId);
    if (value != null) {
        resId = value.intValue();
    }
    return resId;
}
public static int getColor(Context context, int id, boolean night) {
    return context.getResources().getColor(getId(id, night));
}
```
3、手动切换资源
```
@Override
public void onThemeChanged() {
    super.onThemeChanged();

    drawViews();
}
private void drawViews() {
    boolean isNight = ThemeManger.ins().isNight();
    mLayout.setBackgroundColor(ThemeRes.getColor(this, R.color.s0, isNight));
    mBtn.setTextColor(ThemeRes.getColor(this, R.color.s4, isNight));
    mBtn.setText(ThemeRes.getString(this, R.string.btn_text, isNight));
    mBtn.setBackground(ThemeRes.getDrawable(this, R.drawable.bg_btn, isNight));
    mImg.setImageResource(ThemeRes.getId(R.mipmap.image, isNight));
}
```
#####四、设置主题style
1、自定义LayoutInflater.Factory，实现onCreateView方法
```
@Override
public View onCreateView(String name, Context context, AttributeSet attributeSet) {
    View view = null;
    try {
        view = createView(name, context, attributeSet);
    } catch (Exception e) {
    }

    //解析view的所有attr属性，并将theme loader库支持的主题替换属性保存到List
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
```
2、为Activity设置以上自定义LayoutInflater.Factory
```
public ThemeLayoutFactory buildThemeFactory(Activity activity) {
    LayoutInflater inflater = activity.getLayoutInflater();
    ThemeLayoutInflaterCompat.setFactorySet(inflater, false);
    ThemeLayoutFactory factory = new ThemeLayoutFactory();
    inflater.setFactory(factory);
    return factory;
}
```
3、创建主题Resources
```
AssetManager assetManager = AssetManagerCompat.newInstance();
int cookie = AssetManagerCompat.addAssetPath(assetManager, themePath);
if (cookie == 0) {
    if (mOnThemeLoadListener != null) {
        mOnThemeLoadListener.onFailed();
    }
    return;
}
Resources resources = context.getResources();
mThemeResources = new Resources(assetManager, resources.getDisplayMetrics(),
        resources.getConfiguration());
```
4、从主题包中获取资源
```
public int getColor(int resId) {
    Resources hostResources = mContext.getResources();
    int hostColor = hostResources.getColor(resId);
    if (mThemeResources == null) {
        return hostColor;
    }

    String resName = hostResources.getResourceEntryName(resId);
    int themeResId = mThemeResources.getIdentifier(resName, "color", mThemePackageName);
    int themeColor = 0;
    try {
        themeColor = mThemeResources.getColor(themeResId);
    } catch (Exception e) {
        themeColor = hostColor;
    }
    return themeColor;
}
```
5、通知activity做主题更改
```
@Override
public void onThemeChanged() {
    if (mThemeLayoutFactory != null) {
        mThemeLayoutFactory.apply();
    }
}
//TextColorAttr
@Override
public void apply(View view) {
    if (view == null) {
        return;
    }
    if (view instanceof TextView) {
        TextView textView = (TextView)view;
        if(ATTR_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
            textView.setTextColor(ThemeLoader.inst().getColorStateList(attrValueRefId));
        }
    }
}
```
备注：themeloader库目前暂不支持style、shape等属性，而且主题版本控制和签名校验也未添加，后续将持续维护并做支持。
#####四种实现方式优缺点对比：
 实现方式 | 即时生效  | 支持两种以上主题 | 支持在线拉取
----|:-------------: | :-------------:|:-----:
设置主题style | x      | x |  x
设置res-night资源 | x  | x | x
java代码手动切换资源 | √  | x | x
themeloader库(LayoutInflater.Factory) | √  | √ | √
