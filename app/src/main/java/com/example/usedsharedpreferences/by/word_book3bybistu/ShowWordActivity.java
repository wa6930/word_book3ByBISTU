package com.example.usedsharedpreferences.by.word_book3bybistu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Sentence;
import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;
import com.example.usedsharedpreferences.by.word_book3bybistu.Item.WordDetail;
import com.example.usedsharedpreferences.by.word_book3bybistu.adapter.netSentenceAdapter;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBManager;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBwordStorage;
import com.example.usedsharedpreferences.by.word_book3bybistu.getexamplesentence.GetSentence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ShowWordActivity extends AppCompatActivity {
    private String TAG="ErJike's ShowWordActivity:";
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_detail_include_layout);

        Intent intent=getIntent();
        String word=intent.getStringExtra("word");//获得点击时的单词
        final DBManager dbManager=new DBManager(ShowWordActivity.this);//引入数据库
        dbManager.openDatabase();//初始化
        List<WordDetail> wordDetailList=dbManager.tureQuery(word);//查询单词
        List<Sentence> sentenceList = new ArrayList<Sentence>();
        Iterator itr=wordDetailList.iterator();
        WordDetail wordDetail = null;
        while(itr.hasNext()){
            wordDetail=(WordDetail) itr.next();
        }
        final TextView wordName=(TextView) findViewById(R.id.search_d_wordName);
        TextView wordPhonetic=(TextView)findViewById(R.id.search_d_word_phonetic);
        final TextView wordTranslate=(TextView)findViewById(R.id.search_d_word_translation);
        TextView wordDiscation=(TextView)findViewById(R.id.search_d_word_discation);
        ImageView readWord=(ImageView)findViewById(R.id.search_d_word_read);
        ImageView addWordBook=(ImageView)findViewById(R.id.search_d_addword);
        addWordBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBwordStorage dBwordStorage=new DBwordStorage(ShowWordActivity.this,"wordStore.db",null,1);
                SQLiteDatabase database=dBwordStorage.getWritableDatabase();
                DBManager.addWordToSqlite(database,new Word(wordName.getText().toString(),wordTranslate.getText().toString()),ShowWordActivity.this);
            }
        });
        readWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech=new TextToSpeech(ShowWordActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status==TextToSpeech.SUCCESS){
                            Log.i(TAG, "onInit ClickAme1: 调用到发声步骤，发声单词为："+wordName.getText().toString());
                            int result=textToSpeech.setLanguage(Locale.ENGLISH);
                            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.i(TAG, "onInit: ClickAme1,发音失败");
                                Toast.makeText(ShowWordActivity.this,"抱歉！该内容不支持网络发音",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Log.i(TAG, "onInit:ClickAme1  开始阅读");
                                textToSpeech.setSpeechRate(0.8f);
                                textToSpeech.speak(wordName.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                            }
                        }

                    }
                });
            }
        });
        RecyclerView wordSentence=(RecyclerView)findViewById(R.id.search_d_sentence_list);
        if(wordDetail!=null){
            if(wordDetail.getWordName().equals("")||wordDetail.getWordName()==null){
                wordName.setText("数据库内无此单词");
            }
            else{
                wordName.setText(wordDetail.getWordName());
            }
            if(wordDetail.getWordPhonetic().equals("")||wordDetail.getWordPhonetic()==null){
                wordPhonetic.setText("无音标");
            }
            else{
                wordPhonetic.setText(wordDetail.getWordPhonetic());
            }
            if(wordDetail.getWordtranslation().equals("")||wordDetail.getWordtranslation()==null){
                wordTranslate.setText("无对应中文翻译");
            }
            else{
                wordTranslate.setText(wordDetail.getWordtranslation());
            }
            if(wordDetail.getWordDefinition().equals("")||wordDetail.getWordDefinition()==null){
                wordDiscation.setText("无对应英文解释");
            }
            else{
                wordDiscation.setText(wordDetail.getWordDefinition());
            }

        }
        else{
            Toast.makeText(ShowWordActivity.this,"数据库返回类型为空啦，请检查数据库查询语句或数据库。",Toast.LENGTH_LONG).show();
            wordName.setText("数据库内无此单词");
            wordPhonetic.setText("无音标");
            wordTranslate.setText("无对应中文翻译");
            wordDiscation.setText("无对应英文解释");
        }
        sentenceList.addAll(GetSentence.getSentenceList(word,ShowWordActivity.this));
        Iterator Sitr=sentenceList.iterator();
        while(Sitr.hasNext()){
            Sentence sentence=(Sentence) Sitr.next();
            Log.i(TAG, "onCreate sentence name:"+sentence.getSentence_name()+"\n"+sentence.getTranslate());


        }
        GridLayoutManager layoutManager = new GridLayoutManager(ShowWordActivity.this, 1);//左右显示一个fragment
        wordSentence.setLayoutManager(layoutManager);
        netSentenceAdapter sentenceAdapter=new netSentenceAdapter(ShowWordActivity.this,sentenceList);
        wordSentence.setAdapter(sentenceAdapter);



    }


}
