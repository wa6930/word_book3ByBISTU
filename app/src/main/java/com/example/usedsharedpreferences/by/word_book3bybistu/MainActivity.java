package com.example.usedsharedpreferences.by.word_book3bybistu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;
import com.example.usedsharedpreferences.by.word_book3bybistu.adapter.WordAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WordAdapter.deleteWhatyouWant {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            final int delete = -1;
            super.handleMessage(msg);
            switch (msg.what) {
                case delete:
                    Bundle bundle = msg.getData();
                    String word = bundle.getString("word");
                    String tran = bundle.getString("tran");
                    Log.i(TAG, "handleMessage word: " + word);
                    Log.i(TAG, "handleMessage: translate" + tran);
                    Word Dword = new Word(word, tran);//要删除内容的单词翻译
                    Iterator itr = wordList.iterator();
                    Word cWord;
                    int tag = 0;
                    while (itr.hasNext()) {
                        cWord = (Word) itr.next();
                        if (cWord.equals(Dword)) {
                            itr.remove();
                            tag = 1;
                        }
                    }

                    if (tag == 0) {
                        Log.i(TAG, "handleMessage: 删除失败,word:" + word + ",tran:" + tran);
                    } else {
                        adapter = new WordAdapter(wordList, handler, MainActivity.this);//通过重写的holder完成界面构建
                        adapter.setManager(getSupportFragmentManager());


                    }
                    break;
                default:
            }
        }
    };//暂时用不到
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private static String TAG = "ErJike";
    private Word[] words = new Word[]{
            new Word("a", "一个"),
            new Word("b", "bbbbbbbbbbbbbb"),
            new Word("erjike", "耳机壳")

    };

    private List<Word> wordList = new ArrayList<>();//链表的增删改查需要用到
    private WordAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initWrods();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);//找到对应的recyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);//左右显示一个fragment
        recyclerView.setLayoutManager(layoutManager);//绑定二者
        adapter = new WordAdapter(wordList, handler, MainActivity.this);//通过重写的holder完成界面构建
        adapter.setManager(getSupportFragmentManager());
        recyclerView.setAdapter(adapter);//在recycler中导入adapter

        FloatingActionButton fab = findViewById(R.id.fab);//悬浮按钮
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                /**********生成新界面**********/
                AlertDialog.Builder chang_word = new AlertDialog.Builder(MainActivity.this);
                chang_word.setTitle("添加单词");
                LayoutInflater Changinflater = getLayoutInflater();
                final View view1 = Changinflater.inflate(R.layout.change_word_layout, null);
                chang_word.setView(view1);
                final EditText edit_word = (EditText) view1.findViewById(R.id.cw_edit_word_change);
                final EditText edit_tran = (EditText) view1.findViewById(R.id.cw_edit_tran_change);


                chang_word.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (edit_word.getText().toString().equals("") || edit_tran.getText().toString().equals("")) {
                            Snackbar.make(view, "输入有误，添加失败", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        } else {
//                            String word = edit_word.getText().toString();
//                            String tran = edit_tran.getText().toString();
//                            wordList.add(new Word(word, tran));
//                            adapter = new WordAdapter(wordList,handler,MainActivity.this);//通过重写的holder完成界面构建
//                            adapter.setManager(getSupportFragmentManager());
//                            recyclerView.setAdapter(adapter);//在recycler中导入adapter
//                            Snackbar.make(view, "添加单词："+edit_word.getText().toString()+"，成功", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            String word = edit_word.getText().toString();
                            String tran = edit_tran.getText().toString();
                            wordList.add(new Word(word, tran));
                            adapter.notifyDataSetChanged();//更新链表更改导致的视图变化
                            Snackbar.make(view, "添加单词："+edit_word.getText().toString()+"，成功", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                        }
                    }
                });
                chang_word.show();
                /********************************************************/

            }
        });


    }

    public void initWrods() {//装载数组到列表
        wordList.clear();
        for (int i = 0; i < words.length; i++) {
//            Random random = new Random();
//            int index = random.nextInt(words.length);
//            wordList.add(words[index]);
//            Log.i(TAG, "initWrods,List(+):index: " + index + "|words[index]:" + words[index].getWord() + words[index].getTranslate());
            /*******随机重复生成，此处由于单词名决定之后内容，故不能单词名重复*******/
            wordList.add(words[i]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "TODO1", Toast.LENGTH_LONG).show();
                break;
            case R.id.search_word://点击搜索按钮
                //Toast.makeText(this, "TODO2", Toast.LENGTH_LONG).show();
                //Bundle bundle=new Bundle();
                //bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) wordList);
                Intent intent = new Intent(MainActivity.this, SearchWordActivity.class);
                //intent.putExtra(bundle);
                startActivityForResult(intent, 1);//返回值来添加list

                break;
            case R.id.refresh_word:
                Toast.makeText(this, "TODO3", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }


    @Override
    public void deleteWord(String word, String tran) {
        Log.i(TAG, "handleMessage word: " + word);
        Log.i(TAG, "handleMessage: translate" + tran);
        Word Dword = new Word(word, tran);//要删除内容的单词翻译
        Iterator itr = wordList.iterator();
        Word cWord;
        int tag = 0;
        while (itr.hasNext()) {
            cWord = (Word) itr.next();
            if (cWord.equals(Dword)) {
                itr.remove();
                tag = 1;
            }
        }

        if (tag == 0) {
            Log.i(TAG, "handleMessage: 删除失败,word:" + word + ",tran:" + tran);
        } else {
            adapter.notifyDataSetChanged();
//            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 1);
//            recyclerView.setLayoutManager(layoutManager);
//            adapter = new WordAdapter(wordList,handler,MainActivity.this);//通过重写的holder完成界面构建
//            adapter.setManager(getSupportFragmentManager());
//            recyclerView.setAdapter(adapter);//在recycler中导入adapter

        }


    }
}
