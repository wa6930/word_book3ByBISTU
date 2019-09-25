package tools;

import android.util.Log;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Sentence;

import java.util.ArrayList;

public class StringToCut {
    private static String TAG = "ErJike's StringToCut";

    public static ArrayList<Sentence> CutStringToSentenceList(String str) {
        //Log.i(TAG, "CutStringToSentenceList str:"+str);
        ArrayList<Sentence> sentencesList = new ArrayList<Sentence>();
        String[] strArr = str.split("\\n");
        //System.out.println(strArr.length); //这里输出4
        //Log.i(TAG, "CutStringToSentenceList: "+strArr.length);
        int tag = 0;
        String sentence = "";
        String sentence_tran = "";
        for (int i = 0; i < strArr.length; i++) {
            Log.i(TAG, "CutStringToSentenceList show StrArr[" + i + "]:" + strArr[i]);
            if (tag == 0) {
                sentence = sentence + strArr[i];

            } else if (tag == 1) {
                sentence_tran = sentence_tran + strArr[i];
            } else if (tag == 2) {
                sentencesList.add(new Sentence(sentence, sentence_tran));
                //Log.i(TAG, "CutStringToSentenceList sentecename:"+sentence+",sentenceTran:"+sentence_tran);
                sentence = "";
                sentence_tran = "";
                tag = -1;
            } else {
                Log.e(TAG, "CutStringToSentenceList: 循环致命错误");
            }
            tag++;
            //System.out.println(strArr[i]);//这里输出a b c 空字符串
        }


        return sentencesList;
    }

}
