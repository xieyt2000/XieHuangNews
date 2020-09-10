package com.java.xieyuntong.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.xieyuntong.R;

public class ScholarItemActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImageView;
    private TextView textViewAffiliation;
    private TextView textViewBio;
    private TextView textViewEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_item);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mToolbar = findViewById(R.id.scholar_toolbar);
        mToolbar.setTitle(bundle.getString("name"));
        textViewAffiliation = findViewById(R.id.scholar_affiliation);
        textViewAffiliation.setText(bundle.getString("affiliation"));
        textViewBio = findViewById(R.id.scholar_bio);
        textViewBio.setText(bundle.getString("bio"));
        textViewEducation = findViewById(R.id.scholar_education);
        textViewEducation.setText(bundle.getString("education"));
    }
}