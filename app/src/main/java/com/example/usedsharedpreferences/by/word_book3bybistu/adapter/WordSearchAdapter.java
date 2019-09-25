package com.example.usedsharedpreferences.by.word_book3bybistu.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedsharedpreferences.by.word_book3bybistu.R;
import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;

import java.util.List;



public class WordSearchAdapter extends RecyclerView.Adapter<WordSearchAdapter.SearchViewHolder> {

    private Context mContext;
    private List<Word> mSearchWordList;//显示的查找出的内容
    private FragmentTransaction transaction;//TODO
    private SQLiteDatabase beSearchDB;//导入要检索的数据库
//    private String wordString;//存储更改EditText之后对应要搜索的值
    private  FragmentManager manager;

    public FragmentManager getManager() {
        return manager;
    }

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public WordSearchAdapter(List<Word> wordList, Context context) {
        this.mSearchWordList=wordList;
        this.mContext=context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        final View view= LayoutInflater.from(mContext).inflate(R.layout.search_words_item,parent,false);//加载自定义的卡片构图文件
        SearchViewHolder searchViewHolder=new SearchViewHolder(view);
        searchViewHolder.setFragmentManager(manager);

        final CardView cardView=(CardView)view;
        final TextView wordName=(TextView)view.findViewById(R.id.search_word_name);
        final TextView wordTranslate=(TextView)view.findViewById(R.id.search_word_translate);
        //final String translateString=sentence_tran.getText().toString()
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
//        cardView.setOnLongClickListener(new View.OnLongClickListener() {
////            @Override
////            public boolean onLongClick(View view) {
////                //暂时不重建长点事件，可能优化此处v1,添加单词本，朗读//TODO
////                return false;
////            }
////        });

        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Word word=mSearchWordList.get(position);
        holder.wordName.setText(word.getWord());
        holder.wordTranslate.setText(word.getTranslate());


    }


    @Override
    public int getItemCount() {
        return mSearchWordList.size();//显示的链表长度
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        FragmentManager fragmentManager;
        CardView cardView;
        TextView wordName;
        TextView wordTranslate;


        public void setFragmentManager(FragmentManager fragmentManager){
            this.fragmentManager=fragmentManager;
        }
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=(CardView)itemView;
            wordName=(TextView) itemView.findViewById(R.id.search_word_name);
            wordTranslate=(TextView)itemView.findViewById(R.id.search_word_translate);


        }
    }

}
