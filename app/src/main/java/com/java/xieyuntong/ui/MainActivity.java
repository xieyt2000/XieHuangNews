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
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.NewsAPI;
import com.java.xieyuntong.backend.NewsPiece;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String state = "1111111";// news paper data graph cluster scholar
    private boolean[] visible = {true, true, true, true, true, true,true};  //表示要显示的项目
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ImageButton searchButton;
    private int[] colId = {R.id.col_news, R.id.col_paper, R.id.col_data, R.id.col_graph, R.id.col_cluster, R.id.col_scholar
            ,  R.id.col_history};
    private int curState;//当前处于哪个状态 0news 1paper 7history
    private ArrayList<NewsPiece> newsList; //新闻列表
    private SimpleAdapter simpleAdapter;
    private PullToRefreshLayout mPullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setState("1111111");
        curState = 0;
        newsList = new ArrayList<NewsPiece>();
        initColumns();
        initViews();
        refreshMain();
    }
    private void refreshMain() {
        Log.i("refreshmain",String.valueOf(curState));
        if (!NetworkAvail.check(this)) {
            Log.i("network", "no");
            return;
        }
        if(curState == 0){//news
            NewsAPI.setType(NewsPiece.NewsType.NEWS);
            newsList = NewsAPI.getNextPage();
        }else if(curState == 1){//paper
            NewsAPI.setType(NewsPiece.NewsType.PAPER);
            newsList = NewsAPI.getNextPage();
        }else{

        }
        shownewslist();
    }

    private void shownewslist() {
        ListView listView = findViewById(R.id.listview);
        if (!NetworkAvail.check(this)) {
            Log.i("network", "no");
            return;
        }else{
            Log.i("network", "yes ");
        }
        if(newsList.size() == 0){
            Toast.makeText(this, "无新闻", Toast.LENGTH_SHORT).show();
            return;
        }
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        for(NewsPiece newsPiece : newsList){
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("title",newsPiece.getTitle());
            map.put("time",newsPiece.getTime());
            list.add(map);
        }
        simpleAdapter = new SimpleAdapter(this,list,android.R.layout.simple_list_item_2,
                new String[] { "title","time"  }, new int[] {
                android.R.id.text1, android.R.id.text2 });
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
        listView.setSelection(0);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        NewsPiece newsPiece = newsList.get(position);
        NewsAPI.read(newsPiece);
        Bundle bundle = new Bundle();
        bundle.putString("type",newsPiece.getType().toString());
        bundle.putString("time",newsPiece.getTime());
        bundle.putString("source",newsPiece.getSource());
        bundle.putString("content",newsPiece.getContent());
        bundle.putString("title",newsPiece.getTitle());
        Intent intent = new Intent(MainActivity.this,NewsItemActicity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        refreshMain();

    }

    private void initColumns() {
        int id;
        TextView textView;
        for (int i = 0; i < colId.length; i++) {
            id = colId[i];
            textView = findViewById(id);
            if(id == R.id.col_news || id == R.id.col_paper || id == R.id.col_history){//新闻界面&历史记录
                final String str = textView.getText().toString();
                final int finalId = id;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("click",str);
                        if(finalId == R.id.col_news){
                            curState = 0;
                        }else if(finalId == R.id.col_paper){
                            curState = 1;
                        }else{
                            curState = 6;
                        }
                        refreshMain();
                    }
                });
            }else if(id == R.id.col_data){//疫情数据

            }else if(id == R.id.col_graph){//疫情图谱

            }else if(id == R.id.col_cluster){//新闻聚类

            }else if(id == R.id.col_scholar){//知疫学者

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
        mPullToRefreshLayout = findViewById(R.id.pulltorefreshlayout);
        mPullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {//刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshMain();
                        Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
//                        feed.refresh();
                        mPullToRefreshLayout.finishRefresh();
                    }
                }, 1000);
            }

            @Override
            public void loadMore() {

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
            } else if (i == 6) {
                tv = findViewById(R.id.col_history);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.category_chosen){//管理订阅项目

        }else if(id == R.id.search){//搜索

        }else{//关于

        }
        return super.onOptionsItemSelected(item);
    }
}
