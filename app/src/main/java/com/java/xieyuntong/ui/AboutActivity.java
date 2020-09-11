package com.java.xieyuntong.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.java.xieyuntong.R;

public class AboutActivity extends AppCompatActivity {
    TextView mTextView;
    Toolbar mToolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);
        mTextView = findViewById(R.id.about_text);
        mTextView.setText("本程序为2020年夏季清华小学期大作业内容，由计83班的谢云桐和计84班的黄翘楚共同开发，使用了Aminer的新冠疫情相关数据。如果遇到任何问题，可以通过以下方式联系我们：\n" +
                "\n" +
                "谢云桐：邮箱 xieyt18@mails.tsinghua.edu.cn\n" +
                "\n" +
                "黄翘楚：邮箱 huangqc18@mails.tsinghua.edu.cn");

    }
}