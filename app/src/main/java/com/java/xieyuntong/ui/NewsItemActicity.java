package com.java.xieyuntong.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.java.xieyuntong.R;

public class NewsItemActicity extends AppCompatActivity {
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
        textView_source.setText("来源："+source);
        textView_time.setText("发布时间："+time);
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
                Toast.makeText(NewsItemActicity.this, "fuck", Toast.LENGTH_SHORT).show();
            }
        });

    }

}