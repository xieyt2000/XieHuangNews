package com.java.xieyuntong.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.NewsPiece;
import com.java.xieyuntong.backend.scholar.Scholar;

import java.util.ArrayList;

public class MyScholarAdapter extends BaseAdapter {
    private ArrayList<Scholar> scholars;
    private Context mContext;

    public MyScholarAdapter(Context c, ArrayList<Scholar> scholarArrayList) {
        super();
        mContext = c;
        this.scholars = scholarArrayList;
    }

    @Override
    public int getCount() {
        return scholars.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.scholar_list_layout, null);
            holder = new ViewHolder();
            holder.textView1 = view.findViewById(R.id.scholar_list_name);
            holder.textView2 = view.findViewById(R.id.scholar_list_belongto);
            holder.imageView = view.findViewById(R.id.scholar_list_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Log.i("news", String.valueOf(i));
        holder.textView1.setText(scholars.get(i).getName());
        holder.textView2.setText(scholars.get(i).getAffiliation());
        if (scholars.get(i).isPassedAway()) {

            holder.imageView.setImageResource(R.drawable.passaway);
        }else{
            holder.imageView.setImageResource(R.drawable.scholar);
        }

        return view;
    }

    class ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageView;
    }
}

