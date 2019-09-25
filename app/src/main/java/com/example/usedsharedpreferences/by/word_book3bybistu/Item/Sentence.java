package com.example.usedsharedpreferences.by.word_book3bybistu.Item;

import androidx.annotation.Nullable;

public class Sentence {
    private String sentence_no="例句：";
    private String sentence_name;
    private String sentece_tran;

    public void setSentence_no(String sentence_no) {
        this.sentence_no = sentence_no;
    }

    public String getSentence_no() {
        return sentence_no;
    }

    public Sentence(String word, String translate,String no) {
        this.sentence_name = word;
        this.sentece_tran = translate;
        this.sentence_no = no;

    }
    public Sentence(String word, String translate) {
        this.sentence_name = word;
        this.sentece_tran = translate;
        this.sentence_no = "例句：";

    }


    @Override
    public boolean equals(@Nullable Object word) {
        if (this.sentence_name.equals(((Sentence)word).getSentence_name())&&this.sentece_tran.equals(((Sentence)word).getTranslate())){
            return  true;
        }
        else{
            return  false;
        }
    }

    public Sentence(String word, String translate, String amev1, String engv2) {
        this.sentence_name = word;
        this.sentece_tran = translate;

    }

    public Sentence() {
    }

    public String getSentence_name() {
        return sentence_name;
    }

    public String getTranslate() {
        return sentece_tran;
    }


    public void setSentence_name(String sentence_name) {
        this.sentence_name = sentence_name;
    }

    public void setTranslate(String translate) {
        this.sentece_tran = translate;
    }


}
