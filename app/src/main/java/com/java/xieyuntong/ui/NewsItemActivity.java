package com.java.xieyuntong.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.xieyuntong.R;

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
    private FloatingActionButton mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item_acticity);
        init();

    }

    @SuppressLint("SetTextI18n")
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
        textView_content.setText("\t\t\t\t" + content);
        if (source.equals("")) {
            textView_source.setText("\t\t\t\t来源：" + "无");
        } else {
            textView_source.setText("\t\t\t\t来源：" + source);
        }
        textView_time.setText("\t\t\t\t发布时间：" + time);
        mToolbar = findViewById(R.id.news_toolbar);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.drawable.back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        mShareButton = findViewById(R.id.share_button);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "标题：" + title + '\n' + "正文：" + content);
                startActivity(Intent.createChooser(textIntent, "分享"));
            }
        });

    }
}