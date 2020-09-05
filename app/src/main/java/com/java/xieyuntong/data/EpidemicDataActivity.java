package com.java.xieyuntong.data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.EpidemicStat;
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
    private EpidemicStat epidemicStat;//数据
    private ArrayList<Integer> ConfirmedCount;
    private ArrayList<Integer> SuspectedCount;
    private ArrayList<Integer> CuredCount;
    private ArrayList<Integer> DeadCount;
    private ArrayList<Integer> SevereCount;
    private ArrayList<Integer> RiskCount;
    private ArrayList<Integer> Inc24Count;
    private BarChart[] barCharts;
    private int[] BarChartId = {R.id.bar_chart_confirm,R.id.bar_chart_suspected,R.id.bar_chart_cured,R.id.bar_chart_dead
    ,R.id.bar_chart_severe,R.id.bar_chart_risk,R.id.bar_chart_inc24};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epidemic_data);
        initView();
    }



    private void initView() {
        mCountrySpinner = findViewById(R.id.spinner_country);
        mProvinceSpinner = findViewById(R.id.spinner_province);
        mTextView = findViewById(R.id.country_province_text);
        barCharts = new BarChart[7];
        for (int i = 0; i < barCharts.length; i++) {
            barCharts[i] = findViewById(BarChartId[i]);
        }
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
                initData();
                initBarChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initBarChart() {
        for(BarChart barChart:barCharts){
            barChart.getDescription().setEnabled(false); // 不显示描述
            barChart.setExtraOffsets(20, 20, 20, 20);
            setAxis(barChart);//设置坐标轴
            setLegend(barChart);//设置图例
            setData(barChart);//设置数据
        }
    }

    private void setLegend(BarChart barChart) {
    }

    private void setAxis(BarChart barChart) {

    }

    private void setData(BarChart barChart) {
    }

    private void initData() {
        if(province.equals("全国")){//全国数据
            epidemicStat = StatAPI.getStatByCountry(country);
        }else{//省份数据
            epidemicStat = StatAPI.getStatByProvince(country,province);
        }
    }
}