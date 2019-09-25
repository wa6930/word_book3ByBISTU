package com.example.usedsharedpreferences.by.word_book3bybistu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.WordDetail;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBManager;

import java.util.Iterator;
import java.util.List;

public class ShowWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_detail_layout);
        Intent intent=getIntent();
        String word=intent.getStringExtra("word");//获得点击时的单词
        final DBManager dbManager=new DBManager(ShowWordActivity.this);//引入数据库
        dbManager.openDatabase();//初始化
        List<WordDetail> wordDetailList=dbManager.tureQuery(word);//查询单词
        Iterator itr=wordDetailList.iterator();
        WordDetail wordDetail = null;
        while(itr.hasNext()){
            wordDetail=(WordDetail) itr.next();
        }
        TextView wordName=(TextView) findViewById(R.id.d_word_name);
        TextView wordPhonetic=(TextView)findViewById(R.id.d_word_phonetic);
        TextView wordTranslate=(TextView)findViewById(R.id.d_word_translation);
        TextView wordDiscation=(TextView)findViewById(R.id.d_word_discation);
        ImageView readWord=(ImageView)findViewById(R.id.d_word_read);
        RecyclerView wordSentence=(RecyclerView)findViewById(R.id.d_sentence_list);
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

    }


}
