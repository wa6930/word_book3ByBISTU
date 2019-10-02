package com.example.usedsharedpreferences.by.word_book3bybistu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.NewsItemModel;
import com.example.usedsharedpreferences.by.word_book3bybistu.R;

import java.util.List;



/**
 * description:
 * <p/>
 * author:Edward
 * <p/>
 * 2015/9/9
 */
public class NewsAdapter extends BaseAdapter {
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


        return view;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView txtTitle, txtSummary,textOrigin;
    }

}
