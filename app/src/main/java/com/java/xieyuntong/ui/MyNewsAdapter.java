package com.java.xieyuntong.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.NewsPiece;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MyNewsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<NewsPiece>  newsPieceArrayList;
    public MyNewsAdapter(Context c, ArrayList<NewsPiece> newsPieceArrayList){
        super();
        mContext = c;
        this.newsPieceArrayList = newsPieceArrayList;
    }
    @Override
    public int getCount() {
        return newsPieceArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder = null;
        if(view == null){
            view = inflater.inflate(R.layout.news_list_layout,null);
            holder = new ViewHolder();
            holder.textView1 = view.findViewById(R.id.text1);
            holder.textView2 = view.findViewById(R.id.text2);
            holder.linearLayout = view.findViewById(R.id.news_list_linearlayout);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        holder.textView1.setText(newsPieceArrayList.get(i).getTitle());
        holder.textView2.setText(newsPieceArrayList.get(i).getTime());
        if(newsPieceArrayList.get(i).getHaveRead()) {
            holder.linearLayout.setBackgroundColor(R.color.ReadNews);
        }
//        }else{
//            holder.linearLayout.setBackgroundColor(R.color.ReadNews);
//        }

        return view;
    }
    class ViewHolder{
        TextView textView1;
        TextView textView2;
        LinearLayout linearLayout;
    }
}
