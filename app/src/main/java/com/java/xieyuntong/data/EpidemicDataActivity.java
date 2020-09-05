package com.java.xieyuntong.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.StatAPI;

import java.util.ArrayList;

public class EpidemicDataActivity extends AppCompatActivity {
    private Spinner mProvinceSpinner;
    private Spinner mCountrySpinner;
    private String country = "";
    private String province = "";
    private String[] allCountries;
    private String[] allProvinces;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epidemic_data);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        mCountrySpinner = findViewById(R.id.spinner_country);
        mProvinceSpinner = findViewById(R.id.spinner_province);
        mTextView = findViewById(R.id.country_province_text);
        ArrayList<String> countries = StatAPI.getAllCountries();
        allCountries = (String[]) countries.toArray(new String[countries.size()]);
        ArrayAdapter<String> countryApatper = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,allCountries);
        mCountrySpinner.setAdapter(countryApatper);

        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String c = adapterView.getItemAtPosition(position).toString();//获得国家
                country = c;
                ArrayList<String> provinces = StatAPI.getProvincesByCountry(c);
                provinces.add(0,"全国");
                allProvinces = (String[]) provinces.toArray(new String[provinces.size()]);
                mProvinceSpinner = findViewById(R.id.spinner_province);
                ArrayAdapter<String> provinceApatper = new ArrayAdapter<String>(EpidemicDataActivity.this,
                        android.R.layout.simple_list_item_1,allProvinces);
                mProvinceSpinner.setAdapter(provinceApatper);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mProvinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                province = adapterView.getItemAtPosition(i).toString();
                String s;
                if(province.equals("全国")){
                    s = country + "全国统计数据";
                }else{
                    s = country + "国"+province+"省"+"统计数据";
                }
                mTextView.setText(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}