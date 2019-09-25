package com.example.usedsharedpreferences.by.word_book3bybistu.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usedsharedpreferences.by.word_book3bybistu.Item.Sentence;
import com.example.usedsharedpreferences.by.word_book3bybistu.R;

import java.util.List;
import java.util.Locale;

public class netSentenceAdapter extends RecyclerView.Adapter<netSentenceAdapter.ViewHolder> {


    private Context mContext;

    public netSentenceAdapter(Context mContext, List<Sentence> sentenceList) {
        this.mContext = mContext;
        this.sentenceList = sentenceList;
    }

    private List<Sentence> sentenceList;
    private FragmentTransaction transaction;

    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public FragmentManager getManager() {
        return manager;
    }

    private FragmentManager manager;
    public TextToSpeech textToSpeech;

    public void setTransaction(FragmentTransaction transaction) {
        this.transaction = transaction;
    }//获取translation

    public FragmentTransaction getTransaction() {
        return transaction;
    }

    private static String TAG = "ErJike's cardTest";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(mContext).inflate(R.layout.d_sentence_item, parent, false);//导入卡片视图
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setFragmentManager(manager);
        final CardView cardView = (CardView) view;
        final TextView sentence_no = (TextView) view.findViewById(R.id.sentence_no);
        final TextView sentence_eng = (TextView) view.findViewById(R.id.sentence_english);
        final TextView sentence_tran = (TextView) view.findViewById(R.id.sentence_translate);


        //final ImageButton Engv2 = (ImageButton) view.findViewById(R.id.buttonEngV2);//在返回视图之前，建立点击事件与映射关系
        sentence_eng.setOnClickListener(new View.OnClickListener() {//点击例句事件
            @Override
            public void onClick(View view) {
//                textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
//                    @Override
//                    public void onInit(int status) {
//                        if (status == TextToSpeech.SUCCESS) {
//                            Log.i(TAG, "onInit ClickAme1: 调用到发声步骤，发声语句为：" + sentence_eng.getText().toString());
//                            int result = textToSpeech.setLanguage(Locale.ENGLISH);
//                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                                Log.i(TAG, "onInit: ClickAme1,发音失败");
//                                Toast.makeText(mContext, "抱歉！该内容不支持网络发音", Toast.LENGTH_LONG).show();
//                            } else {
//                                Log.i(TAG, "onInit:ClickAme1  开始阅读");
//                                textToSpeech.setSpeechRate(1f);
//                                textToSpeech.speak(sentence_eng.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
//                            }
//                        }
//
//                    }
//                });
                textToSpeech=new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status==TextToSpeech.SUCCESS){
                            Log.i(TAG, "onInit ClickAme1: 调用到发声步骤，发声单词为："+sentence_eng.getText().toString());
                            int result=textToSpeech.setLanguage(Locale.ENGLISH);
                            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.i(TAG, "onInit: ClickAme1,发音失败");
                                Toast.makeText(mContext,"抱歉！该内容不支持网络发音",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Log.i(TAG, "onInit:ClickAme1  开始阅读");
                                textToSpeech.setSpeechRate(1.2f);
                                textToSpeech.speak(sentence_eng.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                            }
                        }

                    }
                });

            }
        });
        sentence_eng.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO
                return false;
            }
        });
//            sentence_tran.setVisibility(View.VISIBLE);
//            Amev1.setVisibility(View.VISIBLE);
//            Engv2.setVisibility(View.VISIBLE);
//            @SuppressLint("ResourceType") AnimatorSet inAnimator=(AnimatorSet) AnimatorInflater.loadAnimator(BaseApplication.getContext(),R.anim.rotate_in_anim);
//            @SuppressLint("ResourceType") AnimatorSet inAnimator=(AnimatorSet) AnimatorInflater.loadAnimator(BaseApplication.getContext(),R.anim.rotate_in_anim);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//点击卡片事件，暂时不写

//                Log.i(TAG, "onClick Card: " + sentence_eng.getText());
//                if(sentence_tran.getVisibility()==View.VISIBLE){
//                    sentence_tran.setVisibility(View.INVISIBLE);
//                }else {
//                    sentence_tran.setVisibility(View.VISIBLE);
//                }


            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                PopupMenu menu = new PopupMenu(mContext, view);//新建一个popupMenu
                menu.getMenuInflater().inflate(R.menu.select_menu, menu.getMenu());
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.select_menu_edi_item:
                                Log.i(TAG, "onClick select_edit_menu: " + sentence_eng.getText());//
                                AlertDialog.Builder chang_word = new AlertDialog.Builder(mContext);
                                chang_word.setTitle("编辑");
                                LayoutInflater Changinflater = LayoutInflater.from(mContext);
                                View view1 = Changinflater.inflate(R.layout.change_word_layout, null);
                                chang_word.setView(view1);
                                final EditText edit_word = (EditText) view1.findViewById(R.id.cw_edit_word_change);
                                final EditText edit_tran = (EditText) view1.findViewById(R.id.cw_edit_tran_change);
                                edit_word.setText(sentence_eng.getText().toString());
                                edit_tran.setText(sentence_tran.getText().toString());//单词和翻译初始化为未修改前的值

                                chang_word.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                });
                                chang_word.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        sentence_eng.setText(edit_word.getText().toString());
                                        sentence_tran.setText(edit_tran.getText().toString());
                                    }
                                });
                                chang_word.show();
                                break;
                            case R.id.select_menu_delete_item:
                                Log.i(TAG, "onClick select_delete_menu: " + sentence_eng.getText());
//                                Iterator itr=sentenceList.iterator();
//                                Word word;
//                                Word Dword=new Word(sentence_eng.getText().toString(),sentence_tran.getText().toString());//要删除内容的单词翻译
//                                while(itr.hasNext()){
//                                    word=(Word)itr.next();
//                                    if(word.equals(Dword)){
//                                        itr.remove();
//                                    }
//                                }
//                                int delete=-1;
//                                Message message=new Message();
//                                message.what=delete;
//                                Bundle wordStr=new Bundle();
//                                wordStr.putString("word",sentence_eng.getText().toString());//存储要删除的建值
//                                wordStr.putString("tran",sentence_tran.getText().toString());
//                                message.setData(wordStr);
//                                handler.sendMessage(message);


                                break;

                            default:

                        }

                        return true;
                    }
                });

                return true;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sentence sentence = sentenceList.get(position);
        holder.sentence_no.setText(sentence.getSentence_no());//获得序号或者"例句："
        holder.sentence_eng.setText(sentence.getSentence_name());//获得holder元素并用列表元素对其赋值。
        holder.sentence_tran.setText(sentence.getTranslate());//获得翻译


    }

    @Override
    public int getItemCount() {
        return sentenceList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView sentence_no;
        TextView sentence_eng;
        TextView sentence_tran;

        FragmentManager fragmentManager;

        public void setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            sentence_no = (TextView) view.findViewById(R.id.sentence_no);
            sentence_eng = (TextView) view.findViewById(R.id.sentence_english);
            sentence_tran = (TextView) view.findViewById(R.id.sentence_translate);

        }
    }


}
