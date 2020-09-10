package com.java.xieyuntong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.scholar.Scholar;
import com.java.xieyuntong.backend.scholar.ScholarAPI;

import java.util.ArrayList;

public class ScholarActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ListView mListView;
    private ArrayList<Scholar> scholars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar);
        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("知疫学者");
        setSupportActionBar(mToolbar);
        mListView = findViewById(R.id.scholar_listview);
        scholars = ScholarAPI.getAllScholars();
        MyScholarAdapter myScholarAdapter = new MyScholarAdapter(this,scholars);
        mListView.setAdapter(myScholarAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int prevSelection = mListView.getFirstVisiblePosition();//获取第一个可见view的位置
                View firstChild = mListView.getChildAt(0);//获取listview中第一个view
                int prevFromTop = 0;
                if (firstChild != null) {//判空很重要
                    prevFromTop = firstChild.getTop();//获取listview中顶部view距离顶部的距离
                }
                try {
                    Scholar scholar = scholars.get(i);

                    Bundle bundle = new Bundle();
                    bundle.putString("url",scholar.getImgURL().toString());
                    bundle.putString("name",scholar.getName());
                    bundle.putString("bio",scholar.getBio());
                    bundle.putString("affiliation",scholar.getAffiliation());
                    bundle.putString("education",scholar.getEducation());
                    Scholar.Indices indices = scholar.getIndices();
                    bundle.putDouble("activity",indices.activity);
                    bundle.putDouble("newStar",indices.newStar);
                    bundle.putDouble("risingStar",indices.risingStar);
                    bundle.putDouble("sociability",indices.sociability);
                    bundle.putInt("citations",indices.citations);
                    bundle.putInt("gindex",indices.gindex);
                    bundle.putInt("hindex",indices.hindex);

                    Intent intent = new Intent(ScholarActivity.this, ScholarItemActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mListView.setSelectionFromTop(prevSelection, prevFromTop);//回到原位置
            }
        });
    }
}