package com.example.usedsharedpreferences.by.word_book3bybistu.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBManager;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBwordStorage;

public class WordContentProvider extends ContentProvider {
    private  static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);//匹配失败返回-1
    private  static  final int WORD_NAME=1;
    private static final int WORD_TRAN=2;
    private static final String AUTHORITY="com.wrodbook.erjikeWordBook";//新建一个provider
    //允许操作的数据列
    public final  static String WORD=DBwordStorage.TABLE_LIST_1;
    public final  static String TRANS=DBwordStorage.TABLE_LIST_2;
    //提供服务的URI
    public final static Uri WORD_CONTENT=Uri.parse("content://"+AUTHORITY+"/words");//数据变化后指定通知的url
    public final static Uri TRAN_CONTENT=Uri.parse("content://"+AUTHORITY+"/trans");
    static {
        //matcher.addURI();
        matcher.addURI(AUTHORITY,"words",WORD_NAME);
        matcher.addURI(AUTHORITY,"trans",WORD_TRAN);


    }
    private SQLiteDatabase sqLiteDatabase;
    DBwordStorage dBwordStorage;
    @Override
    public boolean onCreate() {
        dBwordStorage=new DBwordStorage(this.getContext(),"wordStore.db",null,1);
        return false;
    }

    @Nullable
    /**完成重建**/
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        sqLiteDatabase=dBwordStorage.getWritableDatabase();
        switch (matcher.match(uri)){
            case WORD_NAME:
                return DBManager.WordStorQueryByContentProvider(sqLiteDatabase,getContext());
            case WORD_TRAN:
                return sqLiteDatabase.query(DBwordStorage.TABLE_NAME,strings,s,strings1,null,null,s1);

        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)){
            case WORD_NAME:
                return "erjike.android.wordbook.wordname/com.wrodbook.erjikeWordBook";
            case WORD_TRAN:
                return  "erjike.android.wordbook.wordtran/com.wrodbook.erjikeWordBook";
            default:
                throw  new IllegalArgumentException("未知uri:"+uri);
        }

    }

    @Nullable
    @Override
    /*******完成重写*******/
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        sqLiteDatabase=dBwordStorage.getWritableDatabase();

        switch (matcher.match(uri)){
            case WORD_NAME:
                String word=contentValues.getAsString("word");
                String trans=contentValues.getAsString("trans");
                DBManager.addWordToSqlite(sqLiteDatabase,new Word(word,trans),getContext());
                break;
            case WORD_TRAN:
                long id= ContentUris.parseId(uri);
                //???TODO
                //String whereClause=
        }
        return null;
    }

    @Override
    /******未测试*******/
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        sqLiteDatabase=dBwordStorage.getWritableDatabase();
        int num=0;
        switch (matcher.match(uri)){
            case WORD_NAME:

                num=sqLiteDatabase.delete(DBwordStorage.TABLE_NAME,s+"=?",strings);
                break;
            case WORD_TRAN:
                long id= ContentUris.parseId(uri);
                //???TODO
                //String whereClause=
        }
        return num;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        sqLiteDatabase=dBwordStorage.getWritableDatabase();
        int num=0;
        switch (matcher.match(uri)){
            case  WORD_NAME:
                String word=contentValues.getAsString("word");
                String trans=contentValues.getAsString("trans");
                DBManager.addWordToSqlite(sqLiteDatabase,new Word(word,trans),getContext());
                num=1;
                break;
            case WORD_TRAN:
                break;
        }
        return num;
    }


}
