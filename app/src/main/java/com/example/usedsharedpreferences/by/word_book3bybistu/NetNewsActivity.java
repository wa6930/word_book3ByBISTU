package com.example.usedsharedpreferences.by.word_book3bybistu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.NewsItemModel;
import com.example.usedsharedpreferences.by.word_book3bybistu.Net.CommonTool;
import com.example.usedsharedpreferences.by.word_book3bybistu.Net.Function;
import com.example.usedsharedpreferences.by.word_book3bybistu.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class NetNewsActivity extends AppCompatActivity {
    private ListView mNewsListView;
    private List<NewsItemModel> list;
    private NewsAdapter adapter;
    //获取数据成功
    private final static int GET_DATA_SUCCEED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news_list_layout);
        //初始化视图
        initView();
        //初始化数据
        initData();


    }

    public void initView() {
        list = new ArrayList<NewsItemModel>();
        mNewsListView = (ListView) findViewById(R.id.news_list_view);
    }


    public void initData() {
        //开启一个线程执行耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取网络数据
                String result = CommonTool.getRequest("http://www.globaltimes.cn/world/Top-News.html", "gbk");
                //Log.i("结果------------->", result);
                //解析新闻数据
                List<NewsItemModel> list = Function.parseHtmlData(result);

                for (int i = 0; i < list.size(); i++) {
                    NewsItemModel model = list.get(i);
                    //获取新闻图片
                    Bitmap bitmap = BitmapFactory.decodeStream(CommonTool.getImgInputStream(list.get(i).getUrlImgAddress()));

                    model.setNewsBitmap(bitmap);
                }
                mHandler.sendMessage(mHandler.obtainMessage(GET_DATA_SUCCEED, list));
            }
        }).start();
    }


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCCEED:
                    List<NewsItemModel> list = (List<NewsItemModel>) msg.obj;
                    //新闻列表适配器
                    adapter = new NewsAdapter(NetNewsActivity.this, list, R.layout.adapter_news_item);
                    mNewsListView.setAdapter(adapter);
                    //设置点击事件
                    mNewsListView.setOnItemClickListener(new ItemClickListener());
                    //mNewsListView.setOnLongClickListener(new ItemLongClickListener());
                    break;
                default:
            }
            //Toast.makeText(getApplicationContext(), String.valueOf(list.size()), Toast.LENGTH_LONG).show();

        }

    };

    /**
     * 新闻列表点击事件
     */
    public class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            NewsItemModel temp = (NewsItemModel) adapter.getItem(i);
            Toast.makeText(getApplicationContext(), temp.getNewsDetailUrl(), Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 新闻列表长按事件
     */


}