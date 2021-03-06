package com.ss.android.theme.loader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ss.android.theme.loader.entity.ApkEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenlifeng on 16/7/1.
 */
public class ThemeDAO extends BaseDAO {

    public static final String TABLE_NAME = "theme_info";
    public static final String KEY_NAME = "name";
    public static final String KEY_PACKAGENAME = "packageName";
    public static final String KEY_SIGNATURE = "signature";
    public static final String KEY_VERSION = "version";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESC = "desc";
    private SQLiteHelper mSQLiteHelper;

    public ThemeDAO(Context context) {
        this.mSQLiteHelper = new SQLiteHelper(context);
    }

    @Override
    public String buildCreateTableSql() {
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (");
        builder.append("id").append(" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, ");
        builder.append(KEY_NAME).append(" VARCHAR, ");
        builder.append(KEY_PACKAGENAME).append(" VARCHAR, ");
        builder.append(KEY_SIGNATURE).append(" VARCHAR, ");
        builder.append(KEY_VERSION).append(" INTEGER, ");
        builder.append(KEY_TITLE).append(" VARCHAR, ");
        builder.append(KEY_DESC).append(" VARCHAR ");
        builder.append(")");
        return builder.toString();
    }

    public void savePlugindInfo(ApkEntity entity) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, entity.name);
        cv.put(KEY_PACKAGENAME, entity.packageName);
        Cursor cursor = null;
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        try {
            cursor = db.rawQuery("SELECT * from " + TABLE_NAME + " WHERE " + KEY_PACKAGENAME + " = ? ",
                    new String[] {entity.packageName});
            if (cursor.getCount() > 0) {
                db.update(TABLE_NAME, cv, KEY_PACKAGENAME + " = ? ", new String[] {entity.packageName});
            } else {
                db.insert(TABLE_NAME, null, cv);
            }
        } catch (Exception e) {
            // NOOP
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public ApkEntity getPluginInfo(String packageName) {
        ApkEntity entity = null;
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME + " WHERE " + KEY_PACKAGENAME + " = ? ",
                new String[] {packageName});

        if (cursor.moveToNext()) {
            entity = new ApkEntity();
            entity.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            entity.packageName = cursor.getString(cursor.getColumnIndex(KEY_PACKAGENAME));
        }
        cursor.close();
        db.close();
        return entity;
    }

    public ArrayList<ApkEntity> getPlugins() {
        ArrayList<ApkEntity> list = new ArrayList<>();
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            ApkEntity entity = new ApkEntity();
            entity.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            entity.packageName = cursor.getString(cursor.getColumnIndex(KEY_PACKAGENAME));
            list.add(entity);
        }
        cursor.close();
        db.close();
        return list;
    }

    public Map<String, ApkEntity> getPluginsMap() {
        Map<String, ApkEntity> map = new HashMap<>();
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            ApkEntity entity = new ApkEntity();
            entity.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            entity.packageName = cursor.getString(cursor.getColumnIndex(KEY_PACKAGENAME));
            map.put(entity.packageName, entity);
        }
        cursor.close();
        db.close();
        return map;
    }

    public void deletePlugin(String packageName) {
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_PACKAGENAME + " = ? ", new String[] {packageName});
        db.close();
    }

    public void deletePlugins() {
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
