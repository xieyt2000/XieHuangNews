package com.java.xieyuntong.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.BackEnd;
import com.java.xieyuntong.backend.NewsAPI;
import com.java.xieyuntong.backend.NewsPiece;
import com.java.xieyuntong.backend.StatAPI;
import com.java.xieyuntong.data.EpidemicDataActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mob.MobSDK;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //    private boolean[] visible = {true, true, true, true, true, true, true};  //表示要显示的项目
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ImageButton searchButton;
    private int[] colId = {R.id.col_news, R.id.col_paper, R.id.col_data, R.id.col_graph, R.id.col_cluster, R.id.col_scholar
            , R.id.col_history};
    private int curState;//当前处于哪个状态 0news 1paper 7history
    private ArrayList<NewsPiece> newsList; //新闻列表
    //private SimpleAdapter simpleAdapter;
    private MyNewsAdapter myNewsAdapter;
    private PullToRefreshLayout mPullToRefreshLayout;
    private ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
        setState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobSDK.submitPolicyGrantResult(true, null);
        BackEnd.initialize(this);
        StatAPI.refreshStat();
        setState();
        SharedPreferences sharedPreferences = getSharedPreferences("Category", 0);
        if(sharedPreferences.getBoolean("news", false)){
            curState = 0;
        }else{
            curState = 7;
        }

        newsList = new ArrayList<>();
        initColumns();
        initViews();
        refreshMain();
    }

    private void refreshMain() {
        Log.i("refreshmain", String.valueOf(curState));
        if (!NetworkAvail.check(this) && curState != 6) {
            Log.i("network", "no");
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        if (curState == 0) {//news
            NewsAPI.setType(NewsPiece.NewsType.NEWS);
            NewsAPI.reset();
            newsList = NewsAPI.getNextPage();
        } else if (curState == 1) {//paper
            NewsAPI.setType(NewsPiece.NewsType.PAPER);
            NewsAPI.reset();
            newsList = NewsAPI.getNextPage();
        } else {//history
            newsList = NewsAPI.getHistory();

        }
        showNewsList();
    }


    private void showNewsList() {
        listView = findViewById(R.id.listview);

        if (newsList.size() == 0) {
            Toast.makeText(this, "无新闻", Toast.LENGTH_SHORT).show();
        }
//        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
//        for(NewsPiece newsPiece : newsList){
//            HashMap<String,Object> map = new HashMap<String,Object>();
//            map.put("title",newsPiece.getTitle());
//            map.put("time",newsPiece.getTime());
//            list.add(map);
//        }
//        simpleAdapter = new SimpleAdapter(this,list,android.R.layout.simple_list_item_2,
//                new String[] { "title","time"  }, new int[] {
//                android.R.id.text1, android.R.id.text2 });
        myNewsAdapter = new MyNewsAdapter(getApplicationContext(), newsList);
        listView.setAdapter(myNewsAdapter);
        listView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        int prevSelection = listView.getFirstVisiblePosition();//获取第一个可见view的位置
        View firstChild = listView.getChildAt(0);//获取listview中第一个view
        int prevFromTop = 0;
        if (firstChild != null) {//判空很重要
            prevFromTop = firstChild.getTop();//获取listview中顶部view距离顶部的距离
        }
        try {
            NewsPiece newsPiece = newsList.get(position);
            NewsAPI.read(newsPiece);
            Bundle bundle = new Bundle();
            bundle.putString("type", newsPiece.getType().toString());
            bundle.putString("time", newsPiece.getTime());
            bundle.putString("source", newsPiece.getSource());
            bundle.putString("content", newsPiece.getContent());
            bundle.putString("title", newsPiece.getTitle());
            Intent intent = new Intent(MainActivity.this, NewsItemActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (curState == 6) {
            refreshMain();
        }
        showNewsList();

        listView.setSelectionFromTop(prevSelection, prevFromTop);//回到原位置

    }

    private void initColumns() {
        int id;
        TextView textView;
        for (int value : colId) {
            id = value;
            textView = findViewById(id);
            if (id == R.id.col_news || id == R.id.col_paper || id == R.id.col_history) {//新闻界面&历史记录
                final String str = textView.getText().toString();
                Log.i("click", str);
                final int finalId = id;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (finalId == R.id.col_news) {
                            curState = 0;
                        } else if (finalId == R.id.col_paper) {
                            curState = 1;
                        } else {
                            curState = 6;
                        }
                        refreshMain();
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                });
            } else if (id == R.id.col_data) {//疫情数据
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, EpidemicDataActivity.class);
                        startActivity(intent);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                });

            } else if (id == R.id.col_graph) {//疫情图谱

            } else if (id == R.id.col_cluster) {//新闻聚类

            } else if (id == R.id.col_scholar) {//知疫学者

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
                        if (curState == 6) {//历史记录
                            showNewsList();
                            Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                            mPullToRefreshLayout.finishRefresh();
                            return;
                        }
                        if (!NetworkAvail.check(MainActivity.this)) {
                            Log.i("network", "no");
                            Toast.makeText(MainActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                            showNewsList();
                            mPullToRefreshLayout.finishRefresh();
                            return;
                        } else {
                            Log.i("network", "yes ");
                        }

                        newsList = NewsAPI.refresh();
                        NewsPiece.NewsType newsType = NewsAPI.getType();
                        if (newsType == NewsPiece.NewsType.NEWS) {
                            curState = 0;
                        } else {
                            curState = 1;
                        }
                        showNewsList();
                        Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                        mPullToRefreshLayout.finishRefresh();
                    }
                }, 500);

            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (curState == 6) {
                            Toast.makeText(MainActivity.this, "无更多历史新闻", Toast.LENGTH_SHORT).show();
                            mPullToRefreshLayout.finishLoadMore();
                            return;
                        }
                        if (curState == 7) {
                            Toast.makeText(MainActivity.this, "无更多搜索记录", Toast.LENGTH_SHORT).show();
                            mPullToRefreshLayout.finishLoadMore();
                            return;
                        }
                        if (!NetworkAvail.check(MainActivity.this)) {
                            Log.i("network", "no");
                            Toast.makeText(MainActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                            showNewsList();
                            mPullToRefreshLayout.finishLoadMore();
                            return;
                        } else {
                            Log.i("network", "yes ");
                        }
                        int oldSize = newsList.size();
                        ArrayList<NewsPiece> newNewsList = getMoreNews();
                        newsList.addAll(newNewsList);
                        showNewsList();

                        Toast.makeText(MainActivity.this, "成功获取更多新闻", Toast.LENGTH_SHORT).show();
                        listView.setSelection(oldSize - 4);
                        mPullToRefreshLayout.finishLoadMore();
                    }
                }, 500);

            }
        });
    }

    public ArrayList<NewsPiece> getMoreNews() {
        ArrayList<NewsPiece> newNewsList = NewsAPI.getNextPage();
        return newNewsList;
    }


    private void setOneCol(SharedPreferences pref, String name, int id) {
        if (pref.getBoolean(name, false))
            findViewById(id).setVisibility(View.VISIBLE);
        else
            findViewById(id).setVisibility(ViewStub.GONE);
    }

    private void setState() {
        SharedPreferences categoryPref = getSharedPreferences("Category", 0);
        Log.i("State", " ");
        setOneCol(categoryPref, "news", R.id.col_news);
        setOneCol(categoryPref, "paper", R.id.col_paper);
        setOneCol(categoryPref, "statistics", R.id.col_data);
        setOneCol(categoryPref, "graph", R.id.col_graph);
        setOneCol(categoryPref, "cluster", R.id.col_cluster);
        setOneCol(categoryPref, "scholar", R.id.col_scholar);
        setOneCol(categoryPref, "history", R.id.col_history);
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
        if (id == R.id.category_chosen) {//管理订阅项目
            Intent outIntent = new Intent(this, CategorySettingActivity.class);
            startActivity(outIntent);
            setState();
        } else if (id == R.id.search) {//搜索
            searchContent();

        } else if (id == R.id.delete) {//删除
            Toast.makeText(this, "成功清空", Toast.LENGTH_SHORT).show();
            //refreshMain();//刷新
            NewsAPI.clearHistory();
            for (NewsPiece newsPiece : newsList) {
                newsPiece.resetRead();
            }
            int prevSelection = listView.getFirstVisiblePosition();//获取第一个可见view的位置
            View firstChild = listView.getChildAt(0);//获取listview中第一个view
            int prevFromTop = 0;
            if (firstChild != null) {//判空很重要
                prevFromTop = firstChild.getTop();//获取listview中顶部view距离顶部的距离
            }
            showNewsList();
            listView.setSelectionFromTop(prevSelection, prevFromTop);//回到原位置
        } else {

        }
        return super.onOptionsItemSelected(item);
    }

    private void searchContent() {
        final EditText editText = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("搜索新闻：").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = editText.getText().toString();
                        newsList = NewsAPI.search(s);
                        showNewsList();
                        Toast.makeText(MainActivity.this, "已为您搜索关键字\"" + s + "\"\n上拉即可恢复", Toast.LENGTH_SHORT).show();
//                        feed.setSearch("");
                    }
                }).show();
        curState = 7;//搜索
    }
}
