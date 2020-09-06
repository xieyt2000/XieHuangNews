package com.java.xieyuntong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.java.xieyuntong.R;
import com.mob.MobSDK;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsItemActivity extends AppCompatActivity {
    private String title;
    private String content;
    private String type;
    private String source;
    private String time;
    private TextView textView_title;
    private TextView textView_content;
    private TextView textView_source;
    private TextView textView_time;
    private Toolbar mToolbar;
    private ImageButton mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item_acticity);
        init();

    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        content = bundle.getString("content");
        type = bundle.getString("type");
        source = bundle.getString("source");
        time = bundle.getString("time");
        textView_title = findViewById(R.id.news_title);
        textView_content = findViewById(R.id.news_content);
        textView_source = findViewById(R.id.news_source);
        textView_time = findViewById(R.id.news_time);
        textView_title.setText(title);
        textView_content.setText(content);
        textView_source.setText("来源：" + source);
        textView_time.setText("发布时间：" + time);
        mToolbar = findViewById(R.id.news_toolbar);
        mToolbar.setTitle("新闻");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mShareButton = findViewById(R.id.share_button);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyShare oks = new OnekeyShare();
                oks.setTitle(title);
// titleUrl QQ和QQ空间跳转链接
//                oks.setTitleUrl("https://github.com/xieyt2000/XieHuangNews");
                oks.setText(content);
// setImageUrl是网络图片的url
                oks.setImageUrl("https://hmls.hfbank.com.cn/hfapp-api/9.png");
// url在微信、Facebook等平台中使用
//                oks.setUrl("https://github.com/xieyt2000/XieHuangNews");
// 启动分享GUI
                oks.show(MobSDK.getContext());
            }
        });

    }
}