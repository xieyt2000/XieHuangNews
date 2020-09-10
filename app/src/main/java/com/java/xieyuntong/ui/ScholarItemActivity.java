package com.java.xieyuntong.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.RequestHandler;

public class ScholarItemActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImageView;
    private TextView textViewAffiliation;
    private TextView textViewBio;
    private TextView textViewEducation;
    private TextView textViewIndices;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_item);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mToolbar = findViewById(R.id.scholar_toolbar);
        assert bundle != null;
        mToolbar.setTitle(bundle.getString("name"));
        setSupportActionBar(mToolbar);
        textViewAffiliation = findViewById(R.id.scholar_affiliation);
        textViewAffiliation.setText("\t\t\t\t" + bundle.getString("affiliation"));
        textViewBio = findViewById(R.id.scholar_bio);
        String bio = "\t\t\t\t" + bundle.getString("bio");
        bio = bio.replace("<br><br>","\n\t\t\t\t");
        bio = bio.replace("<br>","\n\t\t\t\t");
        textViewBio.setText(bio);
        textViewEducation = findViewById(R.id.scholar_education);
        String edu = "\t\t\t\t" + bundle.getString("education");
        edu = edu.replace("\n", "\n\t\t\t\t");
        edu = edu.replace("<br><br>","\n\t\t\t\t");
        edu = edu.replace("<br>","\n\t\t\t\t");
        textViewEducation.setText(edu);
        textViewIndices = findViewById(R.id.scholar_indice);
        String s = "";
        s += "\t\t\t\t学术活跃度:";
        s += bundle.getDouble("activity");
        s += "\n";
        s += "\t\t\t\t引用数:";
        s += bundle.getInt("citation");
        s += "\n";
        s += "\t\t\t\tG-index:";
        s += bundle.getInt("gindex");
        s += "\n";
        s += "\t\t\t\tH-index:";
        s += bundle.getInt("hindex");
        s += "\n";
        s += "\t\t\t\t新兴指数:";
        s += bundle.getDouble("newStar");
        s += "\n";
        s += "\t\t\t\t上升指数:";
        s += bundle.getDouble("risingStar");
        s += "\n";
        s += "\t\t\t\t学术合作:";
        s += bundle.getDouble("sociability");
        textViewIndices.setText(s);
        mImageView = findViewById(R.id.scholar_image);
        String url = bundle.getString("url");
        Bitmap bitmap = RequestHandler.getImage(url);
        if(bitmap != null){
            mImageView.setImageBitmap(bitmap);
        }

    }
}