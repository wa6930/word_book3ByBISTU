package com.example.usedsharedpreferences.by.word_book3bybistu.dbchange;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;
import com.example.usedsharedpreferences.by.word_book3bybistu.Item.WordDetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBManager {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "WordData.db"; //保存的数据库文件名
    public static final String DB_TABLE = "table1";
    public static final String TAB_FIELD1 = "word";//单词
    public static final String TAB_FIELD2 = "phonetic";//音标
    public static final String TAB_FIELD3 = "definition";//英译
    public static final String TAB_FIELD4 = "translation";//中译
    public static final String TAB_FIELD5 = "pos";//(用不到)词语位置
    public static final String TAB_FIELD6 = "collins";//(用不到)柯林斯星级
    public static final String TAB_FIELD7 = "oxford";//是否为牛津三千词
    public static final String TAB_FIELD8 = "tag";// 字符串标签：zk/中考，gk/高考，cet4/四级 等等标签，空格分割
    public static final String TAB_FIELD9 = "bnc";//英国国家语料库词频顺序
    public static final String TAB_FIELD10 = "frg";//当代语料库词频顺序
    public static final String TAB_FIELD11 = "exchange";//(不用)时态复数等变换，使用 "/" 分割不同项目，见后面表格
    public static final String TAB_FIELD12 = "detail";//(不用)json 扩展信息，字典形式保存例句（待添加）
    public static final String TAB_FIELD13 = "audio";//(未使用，用网络发音替代)读音音频 url


    public static final String PACKAGE_NAME = "com.example.usedsharedpreferences.by.word_book3bybistu";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;  //在手机里存放数据库的位置

    private SQLiteDatabase database;
    private Context context;
    String TAG = "ErJike's DBManager";

    public DBManager(Context context) {
        this.context = context;
    }

    public void openDatabase() {
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

        private SQLiteDatabase openDatabase(String dbfile) {
        try {
            if (!(new File(dbfile).exists())) {//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库

                Log.i(TAG, "openDatabase: 导入数据库不存在，重新导入");
                InputStream is = this.context.getResources().getAssets().open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(dbfile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,
                    null);
            return db;
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    //用于返回模糊搜索生成语句
    public ArrayList<Word> query(String str) {
        ArrayList<Word> searched_words = new ArrayList<Word>();
        Cursor search_cursor = database.query(DB_TABLE, new String[]{TAB_FIELD1, TAB_FIELD4}, TAB_FIELD1 + "  LIKE ? ",
                new String[]{str + "%"}, null, null, TAB_FIELD9 + "  desc");//用词频排序从而显示前五十个常用词
        Log.i(TAG, "query: 查询完成");
        for (int i = 0; i < 50; i++) {//超过五十个，就只显示50个
            if (search_cursor.moveToNext()) {
                String word = search_cursor.getString(search_cursor.getColumnIndex(TAB_FIELD1));
                String wordTranslate = search_cursor.getString(search_cursor.getColumnIndex(TAB_FIELD4));

                if (!searched_words.contains(new Word(word, wordTranslate))) {
                    searched_words.add(new Word(word, wordTranslate));
                    Log.i(TAG, "query word name:" + word + ",translate:" + wordTranslate);
                }
            } else break;

        }

        search_cursor.close();
        return searched_words;
    }

    /*
     * 精确搜索单词对应的数据库内容
     */
    public ArrayList<WordDetail> tureQuery(String str) {//用于精确查找某个单词在数据库中
        ArrayList<WordDetail> wordDetailArrayList = new ArrayList<WordDetail>();
        //Log.i(TAG, "tureQuery: str:"+str);//可以获得
        Cursor search_cursor = database.query(DB_TABLE, new String[]{TAB_FIELD1, TAB_FIELD2, TAB_FIELD3, TAB_FIELD4}, TAB_FIELD1+"=?",
                new String[]{str}, null, null, null);
        Log.i(TAG, "query: 查询完成");

        if (search_cursor.moveToNext()) {
            String wordName = search_cursor.getString(search_cursor.getColumnIndex(TAB_FIELD1));
            String wordPhonetic=search_cursor.getString(search_cursor.getColumnIndex(TAB_FIELD2));
            String wordDefinition=search_cursor.getString(search_cursor.getColumnIndex(TAB_FIELD3));
            String wordtranslation=search_cursor.getString(search_cursor.getColumnIndex(TAB_FIELD4));

            if (!wordDetailArrayList.contains(new WordDetail(wordName,wordPhonetic,wordtranslation,wordDefinition))) {
                wordDetailArrayList.add(new WordDetail(wordName,wordPhonetic,wordtranslation,wordDefinition));
                Log.i(TAG, "query word name:" + wordName + ",wordPhonetic:" + wordPhonetic+",wordDefinition:" + wordDefinition+",wordtranslation:" + wordtranslation);
            }
        } else {
            Toast.makeText(context, "该单词在词库中不存在！", Toast.LENGTH_LONG).show();
        }

        search_cursor.close();
        return wordDetailArrayList;
    }

    @SuppressLint("LongLogTag")
    public static ArrayList<Word> WordStorQuery(SQLiteDatabase db, Context context) {//用于精确查找某个单词在数据库中
        String TAG="ErJike's wordStore log in";
        ArrayList<Word> wordDetailArrayList = new ArrayList<Word>();
        //Log.i(TAG, "tureQuery: str:"+str);//可以获得
        Cursor search_cursor = db.query(DBwordStorage.TABLE_NAME, new String[]{DBwordStorage.TABLE_LIST_1, DBwordStorage.TABLE_LIST_2}, DBwordStorage.TABLE_LIST_1+" like ? ",
                new String[]{"%%"}, null, null, null);
        Log.i(TAG, "query: 查询完成");

        while (search_cursor.moveToNext()) {
            String wordName = search_cursor.getString(search_cursor.getColumnIndex(DBwordStorage.TABLE_LIST_1));
            String wordtranslation=search_cursor.getString(search_cursor.getColumnIndex(DBwordStorage.TABLE_LIST_2));

            if (!wordDetailArrayList.contains(new Word(wordName,wordtranslation))) {
                wordDetailArrayList.add(new Word(wordName,wordtranslation));
                Log.i(TAG, "query word name:" + wordName+",wordtranslation:" + wordtranslation);
            }
        }
            //Toast.makeText(context, "本地存储内容为空，请尝试添加单词！", Toast.LENGTH_LONG).show();

        search_cursor.close();
        return wordDetailArrayList;
    }
    @SuppressLint("LongLogTag")
    public static boolean addWordToSqlite(SQLiteDatabase db, Word word,Context context) {//用于添加单词到单词本
        String TAG="ErJike's wordStore log in";
        ArrayList<Word> wordDetailArrayList = new ArrayList<Word>();
        //Log.i(TAG, "tureQuery: str:"+str);//可以获得
        Cursor search_cursor = db.query(DBwordStorage.TABLE_NAME, new String[]{DBwordStorage.TABLE_LIST_1, DBwordStorage.TABLE_LIST_2}, DBwordStorage.TABLE_LIST_1+"=?",
                new String[]{word.getWord()}, null, null, null);
        Log.i(TAG, "query: 查询完成");

        if (search_cursor.moveToNext()) {//存在即更新
            ContentValues contentValues=new ContentValues();
            contentValues.put(DBwordStorage.TABLE_LIST_1,word.getWord());
            contentValues.put(DBwordStorage.TABLE_LIST_2,word.getTranslate());
           db.update(DBwordStorage.TABLE_NAME,contentValues,DBwordStorage.TABLE_LIST_1+"=?",new String[]{word.getWord()});
           Toast.makeText(context,"对应单词内容更新成功",Toast.LENGTH_SHORT).show();
        }
        else {//不存在即添加
            ContentValues contentValues=new ContentValues();
            contentValues.put(DBwordStorage.TABLE_LIST_1,word.getWord());
            contentValues.put(DBwordStorage.TABLE_LIST_2,word.getTranslate());
            db.insert(DBwordStorage.TABLE_NAME,null,contentValues);
            Toast.makeText(context,"对应单词内容添加成功",Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(context, "本地存储内容为空，请尝试添加单词！", Toast.LENGTH_LONG).show();

        search_cursor.close();
        return true;
    }
}