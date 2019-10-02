package com.example.usedsharedpreferences.by.word_book3bybistu.Net;

import android.util.Log;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.NewsItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * description:解析新闻数据
 * <p/>
 * author:Edward
 * <p/>
 * 2015/9/9
 */
public class Function {
    private static String  TAG="ErJike's net";
    public static List<NewsItemModel> parseHtmlData(String result) {
        List<NewsItemModel> list = new ArrayList<>();
        Log.i(TAG, "parseHtmlData: result:"+result);


        Pattern pattern = Pattern
                .compile("<img src=([^></a>]*)></a> </div>          <div class=\"span10\">" +
                        "<h4><a target=\"_blank\"  href=\"([^\"]*)\">([^</a>]*)</a></h4><p>([^<br>]*)<br><smaill>([^</smaill>]*)</smaill></p>");
        Matcher matcher = pattern.matcher(result);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            NewsItemModel model = new NewsItemModel();
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(1).trim().toString());
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(2).trim().toString());
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(3).trim().toString());
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(4).trim().toString());

            model.setNewsDetailUrl(matcher.group(1).trim());
            model.setUrlImgAddress(matcher.group(2).trim());
            model.setNewsTitle(matcher.group(3).trim());
            model.setNewsSummary(matcher.group(4).trim());

            sb.append("详情页地址：" + matcher.group(1).trim() + "\n");
            sb.append("图片地址：" + matcher.group(2).trim() + "\n");
            sb.append("标题：" + matcher.group(3).trim() + "\n");
            sb.append("概要：" + matcher.group(4).trim() + "\n\n");

            list.add(model);
        }

        Log.i(TAG, "爬取内容为"+sb.toString());

        return list;
    }

}
