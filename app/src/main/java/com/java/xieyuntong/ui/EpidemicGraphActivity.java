package com.java.xieyuntong.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.kg.Entity;
import com.java.xieyuntong.backend.kg.GraphAPI;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class EpidemicGraphActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView textViewDescription;
    private TextView textViewProperties;
    private ListView listViewRelation;
    private EditText mEditText;
    private ImageView mImageView;
    private ImageButton mImageButton;
    private String keyWord;
    private ArrayList<Entity.Relation> relations;
    private String label;
    private Bitmap bitmap;
    private String description;
    private LinkedHashMap<String, String> properties;

    private Bitmap LoadImageFromWebOperations(URL url) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epidemic_graph);
        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("疫情图谱");
        mEditText = findViewById(R.id.graph_edittext);
        mImageButton = findViewById(R.id.graph_search);
        mImageView = findViewById(R.id.graph_picture);
        textViewDescription = findViewById(R.id.graph_description);
        textViewProperties = findViewById(R.id.graph_properties);
        listViewRelation = findViewById(R.id.listview_relation);
//        mToolbar.setNavigationIcon(R.drawable.back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyWord = mEditText.getText().toString();
                refresh();
            }
        });
        keyWord="病毒";
        refresh();
    }

    private void refresh() {
        Entity entity = GraphAPI.searchEntity(keyWord);
        if (entity == null) {
            Toast.makeText(this, "没有找到结果", Toast.LENGTH_SHORT).show();

        } else {
            label = entity.getLabel();
            bitmap = entity.getImg();
            description = entity.getDescription();
            properties = entity.getProperties();
            relations = entity.getRelations();
            Toast.makeText(this, "为您找到最相近词条：" + label, Toast.LENGTH_SHORT).show();
            mToolbar.setTitle(label);
            refreshText();
        }
    }

    private void refreshText() {//刷新汉字
        textViewDescription.setText(description);
        mImageView.setImageBitmap(bitmap);
        StringBuilder prop = new StringBuilder();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            prop.append(entry.getKey());
            prop.append(":");
            prop.append(entry.getValue());
            prop.append("\n");
        }
        if (prop.toString().equals("")) {
            textViewProperties.setText("无属性");
        } else {
            textViewProperties.setText(prop.toString());
        }

        listViewRelation.setAdapter(new BaseAdapter() {
            class ViewHolder {
                TextView textView1;
                TextView textView2;
            }

            @Override
            public int getCount() {
                return relations.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                LayoutInflater inflater = LayoutInflater.from(EpidemicGraphActivity.this);
                ViewHolder holder = null;
                if (view == null) {
                    view = inflater.inflate(R.layout.relation_layout, null);
                    holder = new ViewHolder();
                    holder.textView1 = view.findViewById(R.id.relation_text1);
                    holder.textView2 = view.findViewById(R.id.relation_text2);
                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }

                holder.textView1.setText(relations.get(i).getHierarchy().toString());
                holder.textView2.setText(relations.get(i).getLabel());
                return view;

            }
        });
        listViewRelation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Entity.Relation relation = relations.get(i);
                keyWord = relation.getLabel();
                mEditText.setText(keyWord);
                refresh();
            }
        });
    }

}