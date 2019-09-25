package com.example.usedsharedpreferences.by.word_book3bybistu.Item;

import androidx.annotation.Nullable;

public class Word {
    //private String word="例句：";
    private String word;
    private String translate;


    public Word(String word, String translate) {
        this.word = word;
        if(translate.equals("")){
            this.translate="无内容";
        }
        else this.translate = translate;

    }



    @Override
    public boolean equals(@Nullable Object word) {
        if (this.word.equals(((Word)word).getWord())&&this.translate.equals(((Word)word).getTranslate())){
            return  true;
        }
        else{
            return  false;
        }
    }


    public Word() {
    }

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }


    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

}
