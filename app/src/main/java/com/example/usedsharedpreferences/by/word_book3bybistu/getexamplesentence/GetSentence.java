package com.example.usedsharedpreferences.by.word_book3bybistu.getexamplesentence;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Sentence;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tools.StringToCut;
import util.HttpUtil;

import static android.content.Context.MODE_PRIVATE;

public class GetSentence {
    private static String TAG="ErJike's GetSentence";
    public static ArrayList<Sentence> getSentenceList(String word, final Context context){
        /*
        * 使用金山词霸开放平台，本人申请的身份key为：54CAAFE35CFBF760B8B073A22B708B92
        * */
        final String urlxml="http://dict-co.iciba.com/api/dictionary.php?w=" + word + "&key=54CAAFE35CFBF760B8B073A22B708B92";
        ArrayList<Sentence> sentencesList;
        HttpUtil.sendOkHttpRequest(urlxml, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(context,"获取例句失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String result=response.body().string();
                Log.d(TAG, "onResponse: result:"+result);
                JinshanParseUtil.parseJinshanEnglishToChineseXMLWithPull(result,context);
                SharedPreferences pref=context.getSharedPreferences("JinshanEnglishToChinese", MODE_PRIVATE);
                String queryText = pref.getString("queryText", "空");
                String voiceEnText = pref.getString("voiceEnText", "空");
                String voiceEnUrlText = pref.getString("voiceEnUrlText", "空");
                String voiceAmText = pref.getString("voiceAmText", "空");
                String voiceAmUrlText = pref.getString("voiceAmUrlText", "空");
                String meanText = pref.getString("meanText", "空");
                String exampleText = pref.getString("exampleText", "空");
                //Log.i(TAG, "onResponse: exampleText:"+exampleText);

                //StringToCut.CutStringToSentenceList(exampleText);
            }
        });
        SharedPreferences pref = context.getSharedPreferences("JinshanEnglishToChinese", MODE_PRIVATE);
        String exampleText = pref.getString("exampleText", "空");
        //Log.i(TAG, "onResponse: exampleText:"+exampleText);

        ArrayList<Sentence> sentencesArrayList=new ArrayList<Sentence>();

        sentencesArrayList.addAll(StringToCut.CutStringToSentenceList(exampleText));





    return sentencesArrayList;
    }

}
