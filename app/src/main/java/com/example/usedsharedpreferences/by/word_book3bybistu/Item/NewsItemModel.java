package com.example.usedsharedpreferences.by.word_book3bybistu.Item;


import android.graphics.Bitmap;

/**
 * description:新闻Model
 * <p/>
 * author:Edward
 * <p/>
 * 2015/9/9
 */
public class NewsItemModel {
    //存储加载完成的图片
    private Bitmap newsBitmap;
    //新闻详情地址
    private String newsDetailUrl;
    //新闻图片地址
    private String urlImgAddress;
    //新闻标题
    private String newsTitle;
    //新闻概要
    private String newsSummary;
    //新闻来源
    private String newsOrigin;

    public void setNewsOrigin(String newsOrigin) {
        this.newsOrigin = newsOrigin;
    }

    public String getNewsOrigin() {
        return newsOrigin;
    }

    public Bitmap getNewsBitmap() {
        return newsBitmap;
    }

    public void setNewsBitmap(Bitmap newsBitmap) {
        this.newsBitmap = newsBitmap;
    }

    public String getUrlImgAddress() {
        return urlImgAddress;
    }

    public void setUrlImgAddress(String urlImgAddress) {
        this.urlImgAddress = urlImgAddress;
    }

    public String getNewsDetailUrl() {
        return newsDetailUrl;
    }

    public void setNewsDetailUrl(String newsDetailUrl) {
        this.newsDetailUrl = newsDetailUrl;
    }


    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSummary() {
        return newsSummary;
    }

    public void setNewsSummary(String newsSummary) {
        this.newsSummary = newsSummary;
    }
}
