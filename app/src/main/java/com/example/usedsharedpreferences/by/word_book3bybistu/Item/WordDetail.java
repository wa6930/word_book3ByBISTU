package com.example.usedsharedpreferences.by.word_book3bybistu.Item;

import androidx.annotation.Nullable;

public class WordDetail {
    String wordName;
    String wordPhonetic;
    String wordDefinition;
    String wordtranslation;


    public WordDetail() {
    }

    public WordDetail(String wordName, String wordPhonetic, String wordtranslation, String wordDefinition) {

        this.wordName = wordName;
        this.wordPhonetic = wordPhonetic;
        this.wordtranslation = wordtranslation;
        this.wordDefinition = wordDefinition;
    }

    public String getWordName() {
        return wordName;
    }

    public String getWordPhonetic() {
        return wordPhonetic;
    }

    public String getWordtranslation() {
        return wordtranslation;
    }

    public String getWordDefinition() {
        return wordDefinition;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public void setWordPhonetic(String wordPhonetic) {
        this.wordPhonetic = wordPhonetic;
    }

    public void setWordtranslation(String wordtranslation) {
        this.wordtranslation = wordtranslation;
    }

    public void setWordDefinition(String wordDefinition) {
        this.wordDefinition = wordDefinition;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        WordDetail dt = (WordDetail) obj;
        if (this.wordName.equals(dt.getWordName())
                && this.wordDefinition.equals(dt.wordDefinition)
                && this.wordPhonetic.equals(dt.getWordPhonetic()) && this.wordtranslation.equals(dt.wordtranslation)) {
            return true;
        } else return false;

    }
}
