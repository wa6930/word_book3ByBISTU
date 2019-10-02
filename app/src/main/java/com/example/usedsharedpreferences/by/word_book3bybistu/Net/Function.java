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
 * author:ErJike
 * <p/>
 * 2019/10/9
 */
public class Function {
    private static String  TAG="ErJike's net";
    public static List<NewsItemModel> parseHtmlData(String result) {
        List<NewsItemModel> list = new ArrayList<>();
        Log.i(TAG, "parseHtmlData: result:"+result);
        String toSelect="<img src=(.*)></a> </div>(\\s*)<div class=\"span10\">\n" +
                "(\\s*)<h4><a target=\"_blank\"  href=\"(.*)\">(.*)</a></h4>\n" +
                "(\\s*)<p>(.*)<br>\n" +
                "(\\s*)<smaill>(.*)</smaill>";//用于爬取
//        String testToSelect="<img src=(http://www.globaltimes.cn/Portals/0//attachment/2019/2019-10-02/41395a05-6ab9-4f97-b254-5e9ff4c1d153_s.jpeg)></a> </div>          <div class=\"span10\">\n" +
//                "                <h4><a target=\"_blank\"  href=\"(http://www.globaltimes.cn/content/1165966.shtml)\">(President Xi reviews armed forces on National Day for first time)</a></h4>\n" +
//                "                <p>(Chinese President Xi Jinping reviewed the armed forces at the heart of Beijing Tuesday morning, his first on National Day, to mark the 70th founding anniversary of the People's Republic of China (PRC).)<br>\n" +
//                "                  <smaill> (Source: Xinhua | 2019/10/2 9:06:45)</smaill>";
//        Pattern pattern = Pattern
//                .compile("<img src=([^></a>]*)></a> </div>          <div class=\"span10\">" +
//                        "<h4><a target=\"_blank\"  href=\"([^\"]*)\">([^</a>]*)</a></h4><p>([^<br>]*)<br><smaill>([^</smaill>]*)</smaill></p>");


        Pattern pattern = Pattern
                .compile(toSelect);

        Matcher matcher = pattern.matcher(result);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            NewsItemModel model = new NewsItemModel();
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(1).trim().toString());
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(2).trim().toString());
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(3).trim().toString());
            Log.i(TAG, "parseHtmlData: matcher.group():"+matcher.group(4).trim().toString());

            model.setNewsDetailUrl(matcher.group(4).trim());
            model.setUrlImgAddress(matcher.group(1).trim());
            model.setNewsTitle(matcher.group(5).trim());
            model.setNewsSummary(matcher.group(7).trim());
            model.setNewsOrigin(matcher.group(9).trim());

            sb.append("详情页地址：" + matcher.group(4).trim() + "\n");
            sb.append("图片地址：" + matcher.group(1).trim() + "\n");
            sb.append("标题：" + matcher.group(5).trim() + "\n");
            sb.append("概要：" + matcher.group(7).trim() + "\n");
            sb.append("来源："+matcher.group(9).trim()+"\n\n");

            list.add(model);
        }

        Log.i(TAG, "爬取内容为"+sb.toString());

        return list;
    }

}
