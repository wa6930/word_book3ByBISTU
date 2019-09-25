package com.example.usedsharedpreferences.by.word_book3bybistu.dbchange;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class wordBookDB extends SQLiteOpenHelper {
    private final String DB_NAME = "Wordbook";


    public wordBookDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createDB = "create table" + DB_NAME + "(word Text primary key,wordTranslate Text)";//记录单词名和翻译两基础信息
        sqLiteDatabase.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableSQL="DROP TABLE IF EXISTS "+DB_NAME +" ";
        sqLiteDatabase.execSQL(dropTableSQL);
        dropTableSQL="DROP TABLE IF EXISTS "+DB_NAME+" ";
        sqLiteDatabase.execSQL(dropTableSQL);
        onCreate(sqLiteDatabase);

    }

}
