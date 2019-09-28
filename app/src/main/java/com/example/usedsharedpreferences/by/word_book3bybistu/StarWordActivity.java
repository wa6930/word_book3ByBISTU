package com.example.usedsharedpreferences.by.word_book3bybistu;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;
import com.example.usedsharedpreferences.by.word_book3bybistu.adapter.WordAdapter;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBManager;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBwordStorage;
import com.example.usedsharedpreferences.by.word_book3bybistu.fragement.RightFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StarWordActivity extends AppCompatActivity implements WordAdapter.deleteWhatyouWant {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            final int delete = -1;
            super.handleMessage(msg);
            switch (msg.what) {
                case delete:
                    Bundle bundle = msg.getData();
                    String word = bundle.getString("word");
                    String tran = bundle.getString("tran");
                    Log.i(TAG, "handleMessage word: " + word);
                    Log.i(TAG, "handleMessage: translate" + tran);
                    Word Dword = new Word(word, tran);//要删除内容的单词翻译
                    Iterator itr = wordList.iterator();
                    Word cWord;
                    int tag = 0;
                    while (itr.hasNext()) {
                        cWord = (Word) itr.next();
                        if (cWord.equals(Dword)) {
                            itr.remove();
                            tag = 1;
                        }
                    }

                    if (tag == 0) {
                        Log.i(TAG, "handleMessage: 删除失败,word:" + word + ",tran:" + tran);
                    } else {
                        adapter = new WordAdapter(wordList, handler, StarWordActivity.this);//通过重写的holder完成界面构建
                        adapter.setManager(getSupportFragmentManager());


                    }
                    break;
                default:
            }
        }
    };//暂时用不到
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private static String TAG = "ErJike";
    private Word[] words = new Word[]{
//            new Word("a", "一个"),
//            new Word("b", "bbbbbbbbbbbbbb"),
//            new Word("erjike", "耳机壳")
    };

    private List<Word> wordList = new ArrayList<>();//链表的增删改查需要用到
    private WordAdapter adapter;
    private DBwordStorage dbHelper;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /****
         *
         * 当为竖屏的时候调用的布局
         *
         * ***/
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
            Intent intent=getIntent();
            dbHelper = new DBwordStorage(StarWordActivity.this, "StarWordStore.db", null, 1);
            //initWrods();//将word数组转化为链表
            SQLiteDatabase db = dbHelper.getWritableDatabase();//获取数据库后调用查找方法
            wordList.addAll(DBManager.WordStorQuery(db, StarWordActivity.this));//读取数据库
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//找到对应的recyclerView
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1);//左右显示一个fragment
            recyclerView.setLayoutManager(layoutManager);//绑定二者
            adapter = new WordAdapter(wordList, handler, StarWordActivity.this);//通过重写的holder完成界面构建
            adapter.setManager(getSupportFragmentManager());
            recyclerView.setAdapter(adapter);//在recycler中导入adapter


        }
        /*********
         *
         *横屏布局与逻辑
         *
         * *****/
        else {
            setContentView(R.layout.activity_main_land);
            Intent intent=getIntent();
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            dbHelper = new DBwordStorage(StarWordActivity.this, "wordStore.db", null, 1);
            //initWrods();//将word数组转化为链表
            SQLiteDatabase db = dbHelper.getWritableDatabase();//获取数据库后调用查找方法
            wordList.addAll(DBManager.WordStorQuery(db, StarWordActivity.this));//读取数据库
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//找到对应的recyclerView
            GridLayoutManager layoutManager = new GridLayoutManager(this, 1);//卡片显示一行
            recyclerView.setLayoutManager(layoutManager);//绑定二者
            adapter = new WordAdapter(wordList, handler, StarWordActivity.this);//通过重写的holder完成界面构建
            RightFragment rightFragment = new RightFragment();
            adapter.setManager(getSupportFragmentManager());
            adapter.setRightFragment(rightFragment);
            recyclerView.setAdapter(adapter);//在recycler中导入adapter

        }


    }

    public void initWrods() {//装载数组到列表
        wordList.clear();
        for (int i = 0; i < words.length; i++) {
//            Random random = new Random();
//            int index = random.nextInt(words.length);
//            wordList.add(words[index]);
//            Log.i(TAG, "initWrods,List(+):index: " + index + "|words[index]:" + words[index].getWord() + words[index].getTranslate());
            /*******随机重复生成，此处由于单词名决定之后内容，故不能单词名重复*******/
            wordList.add(words[i]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void deleteWord(String word, String tran) {
        Log.i(TAG, "handleMessage word: " + word);
        Log.i(TAG, "handleMessage: translate" + tran);
        Word Dword = new Word(word, tran);//要删除内容的单词翻译
        dbHelper = new DBwordStorage(StarWordActivity.this, "StarWordStore.db", null, 1);
        //initWrods();//将word数组转化为链表
        SQLiteDatabase db = dbHelper.getWritableDatabase();//获取数据库后调用查找方法
        db.delete(DBwordStorage.TABLE_NAME,DBwordStorage.TABLE_LIST_1+"=?",new String[]{word});
        wordList.clear();
        wordList.addAll(DBManager.WordStorQuery(db,StarWordActivity.this));
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {//界面重新回到前台时

        dbHelper = new DBwordStorage(StarWordActivity.this, "StarWordStore.db", null, 1);
        //initWrods();//将word数组转化为链表
        SQLiteDatabase db = dbHelper.getWritableDatabase();//获取数据库后调用查找方法
        wordList.clear();
        wordList.addAll(DBManager.WordStorQuery(db, StarWordActivity.this));//读取数据库
        adapter.notifyDataSetChanged();//更新显示

        super.onStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
        else{

        }
    }
    @Override
    protected void onDestroy() {//关闭程序时保存单词表数据库


        super.onDestroy();
    }
}