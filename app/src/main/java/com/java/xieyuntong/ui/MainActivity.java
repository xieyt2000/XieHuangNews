package com.java.xieyuntong.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.java.xieyuntong.R;


public class MainActivity extends AppCompatActivity {
    private String state = "111111";// news paper data graph cluster scholar
    private boolean[] visible = {true, true, true, true, true, true};  //表示要显示的项目
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ImageButton searchButton;
    private int[] colId = {R.id.col_news, R.id.col_paper, R.id.col_data, R.id.col_graph, R.id.col_cluster, R.id.col_scholar
            , R.id.col_category, R.id.col_history,R.id.col_about};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setState("111111");
        initColumns();
        initViews();

    }

    private void initColumns() {
        int id;
        TextView textView;
        for (int i = 0; i < colId.length; i++) {
            id = colId[i];
            textView = findViewById(id);
            if(id == R.id.col_news || id == R.id.col_paper || id == R.id.col_history){//新闻界面&历史记录

            }else if(id == R.id.col_data){//疫情数据

            }else if(id == R.id.col_graph){//疫情图谱

            }else if(id == R.id.col_cluster){//新闻聚类

            }else if(id == R.id.col_scholar){//知疫学者

            }else if(id == R.id.col_category){//分类管理

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, CategoryManageActivity.class);
                        startActivity(intent);
                    }
                });

            }else{//关于

            }
        }
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("首页");
        setSupportActionBar(mToolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar.setNavigationIcon(R.drawable.nav);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }


    private void setState(String newState) {
        Log.i("State", newState);
        this.state = newState;
        TextView tv = findViewById(R.id.col_news);
        for (int i = 0; i < state.length(); i++) {
            if (i == 0) {
                tv = findViewById(R.id.col_news);
            } else if (i == 1) {
                tv = findViewById(R.id.col_paper);
            } else if (i == 2) {
                tv = findViewById(R.id.col_data);
            } else if (i == 3) {
                tv = findViewById(R.id.col_graph);
            } else if (i == 4) {
                tv = findViewById(R.id.col_cluster);
            } else if (i == 5) {
                tv = findViewById(R.id.col_scholar);
            }
            if (newState.charAt(i) == '1') {
                tv.setVisibility(View.VISIBLE);
                Log.i("state", i + "true");
                visible[i] = true;
            } else {
                tv.setVisibility(View.GONE);
                Log.i("state", i + "false");
                visible[i] = false;
            }
        }
    }

}
