package com.example.usedsharedpreferences.by.word_book3bybistu.dbchange;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
/***
 *
 *
 *
 * 用于实现本地转存与其他程序的调用
 *
 *
 * **/
public class DBwordStorage extends SQLiteOpenHelper {
    public static final String TABLE_NAME="wordStorage";
    public static final String TABLE_LIST_1="word";
    public static final String TABLE_LIST_2="wordTranslate";

    public static final String CREATE_BOOK="create table wordStorage("
            +"word TEXT primary key ,"
            +"wordTranslate TEXT)";
    private Context mContext;
    public DBwordStorage(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    public DBwordStorage(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"创建了新的表单！",Toast.LENGTH_LONG).show();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
