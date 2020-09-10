package com.java.xieyuntong.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.java.xieyuntong.R;

import org.w3c.dom.Text;

public class ClusterItemActivity extends AppCompatActivity {
    private TextView textViewTitle;
    private TextView textViewSource;
    private TextView textViewTime;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_item);
        textViewTitle = findViewById(R.id.cluster_title);
        textViewSource = findViewById(R.id.cluster_source);
        textViewTime = findViewById(R.id.cluster_time);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("疫情聚类");
        setSupportActionBar(mToolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        textViewTitle.setText("\t\t\t\t"+bundle.getString("title"));
        textViewSource.setText(bundle.getString("source"));
        textViewTime.setText(bundle.getString("time"));
    }
}