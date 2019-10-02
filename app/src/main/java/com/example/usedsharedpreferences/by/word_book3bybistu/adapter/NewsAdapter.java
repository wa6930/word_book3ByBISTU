package com.example.usedsharedpreferences.by.word_book3bybistu.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Selection;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.NewsItemModel;
import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Word;
import com.example.usedsharedpreferences.by.word_book3bybistu.R;
import com.example.usedsharedpreferences.by.word_book3bybistu.ShowWordActivity;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBManager;
import com.example.usedsharedpreferences.by.word_book3bybistu.dbchange.DBwordStorage;

import java.util.List;




/**
 * description:
 * <p/>
 * author:Edward
 * <p/>
 * 2015/9/9
 */
public class NewsAdapter extends BaseAdapter {
    private String TAG="ErJike's NewsAdapter";
    private Context mContext;
    private List<NewsItemModel> list;
    private int layoutId;
    private ViewHolder viewHolder = null;

    public NewsAdapter(Context mContext, List<NewsItemModel> list, int layoutId) {
        this.mContext = mContext;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(layoutId, null);

            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.txt_title);
            viewHolder.txtSummary = (TextView) view.findViewById(R.id.txt_summary);
            viewHolder.textOrigin=(TextView)view.findViewById(R.id.txt_Origin);


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (list.get(position).getNewsBitmap() != null) {
            viewHolder.imageView.setImageBitmap(list.get(position).getNewsBitmap());
        } else {
            //如果没有图片，则将imageview控件隐藏
            viewHolder.imageView.setVisibility(View.GONE);
        }
        viewHolder.txtTitle.setText(list.get(position).getNewsTitle());
        viewHolder.txtSummary.setText(list.get(position).getNewsSummary());
        viewHolder.textOrigin.setText(list.get(position).getNewsOrigin());
        final TextView txtSummary=view.findViewById(R.id.txt_summary);
        //final TextView txtTitle=view.findViewById(R.id.txt_title);

        /*
        * 显示添加单词本的相关功能
        * */
            /*
            重写选择文本所显示的内容

             */
        final View finalView = view;
        ActionMode.Callback callback = new ActionMode.Callback() {
            private Menu mMenu;

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = actionMode.getMenuInflater();
                menu.clear();
                menuInflater.inflate(R.menu.select_word_menu, menu);
                return true;
            }

            /*
            返回获取的内容，从而进行后续操作
             */
            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                this.mMenu = menu;
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                final int selStart = Selection.getSelectionStart(txtSummary.getText());
                final int selEnd = Selection.getSelectionEnd(txtSummary.getText());

                int min = Math.max(0, Math.min(selStart, selEnd));
                int max = Math.max(0, Math.max(selStart, selEnd));

                Log.i(TAG, "onActionItemClicked: min:"+min);
                Log.i(TAG, "onActionItemClicked: max:"+max);

                final String selectedWord = txtSummary.getText().toString().substring(min,max).toLowerCase();
                switch (menuItem.getItemId()) {
                    case R.id.select_word_add:

                        Word word = new Word(selectedWord, "暂无中译");
                        DBwordStorage dBwordStorage = new DBwordStorage(mContext, "wordStore.db", null, 1);
                        SQLiteDatabase database = dBwordStorage.getWritableDatabase();
                        DBManager.addWordToSqlite(database, word, mContext);
                        break;
                    case R.id.select_word_trans:
                        Intent intent = new Intent(mContext, ShowWordActivity.class);
                        intent.putExtra("word", selectedWord);
                        mContext.startActivity(intent);
                        break;
                    default:

                }
                return true;
            }


            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }

        };
        /*
        显示详情添加单词本的相关功能
         */
        txtSummary.setCustomSelectionActionModeCallback(callback);

        return view;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView txtTitle, txtSummary,textOrigin;
    }

}
