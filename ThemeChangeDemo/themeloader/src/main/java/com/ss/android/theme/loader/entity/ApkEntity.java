package com.ss.android.theme.loader.entity;

/**
 * Created by chenlifeng on 16/6/30.
 */
public class ApkEntity extends BaseBean {
    /** for download **/
    /** doanload taskID, equal packageName **/
    public String taskID;
    /** 下载地址 **/
    public String url;
    /** 文件名 **/
    public String name;
    /** 标题 **/
    public String title;
    /** 描述 **/
    public String desc;
    /** apk包名 **/
    public String packageName;
    /** 文件下载路径 **/
    public String filePath;
    /** 文件总大小 **/
    public long fileSize;
    /** 已下载文件大小 **/
    public long downloadSize;
}
