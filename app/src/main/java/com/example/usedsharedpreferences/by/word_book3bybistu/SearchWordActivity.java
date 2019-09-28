package com.example.usedsharedpreferences.by.word_book3bybistu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;
import com.example.usedsharedpreferences.by.word_book3bybistu.adapter.WordSearchAdapter;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchWordActivity extends AppCompatActivity {
    EditText searchEditText;
    ListView serchWordList;
    //ArrayList<String> wordNameList;
    SQLiteDatabase database;//获得dbmanager中的database
    String TAG="ErJike's searchWor";
    WordSearchAdapter wordSearchAdapter;//用于保存自定义的recyclerAdapter
    List<Word> wordsList=new ArrayList<Word>();
    Comparator<Word> comparator=new Comparator<Word>() {
        @Override
        public int compare(Word word, Word t1) {
            if(word!=null&&t1!=null){
                if(word.getWord()!=null&&t1.getWord()!=null){
                    if(word.getWord().length()>t1.getWord().length()){
                        return  1;
                    }
                    else if(word.getWord().length()==t1.getWord().length()){
                        return 0;
                    }
                    else{
                        return -1;
                    }
                }
                else return 0;

            }
            else return 0;

        }
    };
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //wordsList.add(new Word("ceshi","测试"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main_activity_layout);

        Intent intent = getIntent();
        //wordNameList=intent.getStringArrayListExtra("wordList");//获得MainActivity中的word链表
        final DBManager dbManager=new DBManager(SearchWordActivity.this);
        dbManager.openDatabase();
        database=dbManager.getDatabase();//获得对应数据库
        serchWordList=(ListView)findViewById(R.id.search_word);
        searchEditText=(EditText)findViewById(R.id.search_editText);
        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.search_recycler_view);//获得对应的recyclerView
        wordSearchAdapter=new WordSearchAdapter(wordsList, SearchWordActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(SearchWordActivity.this, 1);//左右显示一个fragment
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wordSearchAdapter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!searchEditText.getText().toString().equals("")||searchEditText.getText().toString()!=null){
                    if(searchEditText.getText().toString().length()>=1){//只有第二个字母输入时才会进行搜索
                        //Log.i(TAG, "afterTextChanged:测试，改变后的状态显示");
                        wordsList.clear();
                        wordsList.addAll(dbManager.query(searchEditText.getText().toString()));//生成临时单词表
                        Collections.sort(wordsList,comparator);
                        wordSearchAdapter.notifyDataSetChanged();


                    }
                }

            }
        });
    }
}
