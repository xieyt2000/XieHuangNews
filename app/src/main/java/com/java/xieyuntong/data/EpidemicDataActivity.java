package com.java.xieyuntong.data;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.java.xieyuntong.R;
import com.java.xieyuntong.backend.EpidemicStat;
import com.java.xieyuntong.backend.StatAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EpidemicDataActivity extends AppCompatActivity {
    private Spinner mProvinceSpinner;
    private Spinner mCountrySpinner;
    private Spinner mTypeSpinner;
    private String country = "";
    private String province = "";
    private String type = "";
    private String[] allCountries;
    private String[] allProvinces;
    private TextView mTextView;
    private EpidemicStat epidemicStat;//数据
    //    private ArrayList<Integer> ConfirmedCount;
//    private ArrayList<Integer> SuspectedCount;
//    private ArrayList<Integer> CuredCount;
//    private ArrayList<Integer> DeadCount;
//    private ArrayList<Integer> SevereCount;
//    private ArrayList<Integer> RiskCount;
//    private ArrayList<Integer> Inc24Count;
    private ArrayList<Integer> dataList;
    private ArrayList<Date> dateArrayList;
    private LineChart lineChart;
    private TextView graphTextView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epidemic_data);
        initView();
    }


    private void initView() {
        mCountrySpinner = findViewById(R.id.spinner_country);
        mProvinceSpinner = findViewById(R.id.spinner_province);
        mTypeSpinner = findViewById(R.id.spinner_type);
        lineChart = findViewById(R.id.linechart);
        mTextView = findViewById(R.id.country_province_text);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setNavigationIcon(R.drawable.back);
        dataList = new ArrayList<Integer>();
        dateArrayList = new ArrayList<Date>();
        graphTextView = findViewById(R.id.graph_text);
        graphTextView.setVisibility(View.GONE);


        ArrayList<String> countries = StatAPI.getAllCountries();
        allCountries = (String[]) countries.toArray(new String[countries.size()]);
        Arrays.sort(allCountries);
        ArrayAdapter<String> countryApatper = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, allCountries);
        mCountrySpinner.setAdapter(countryApatper);

        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String c = adapterView.getItemAtPosition(position).toString();//获得国家
                country = c;
                ArrayList<String> provinces = StatAPI.getProvincesByCountry(c);
                provinces.sort(new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                });
                provinces.add(0, "all");
                allProvinces = (String[]) provinces.toArray(new String[provinces.size()]);

                mProvinceSpinner = findViewById(R.id.spinner_province);
                ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(EpidemicDataActivity.this,
                        android.R.layout.simple_list_item_1, allProvinces);
                mProvinceSpinner.setAdapter(provinceAdapter);

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
                if (province.equals("all")) {
                    s = country + "全国统计数据";
                } else {
                    s = country + "\n" + province +   "统计数据";
                }
                mTextView.setText(s);
                initData();
                initLineChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
                initData();
                initLineChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initLineChart() {
        if (dataList.size() == 0) {
            //Toast.makeText(this, "无数据", Toast.LENGTH_SHORT).show();
            graphTextView.setVisibility(View.VISIBLE);
            lineChart.setVisibility(View.GONE);
            return;
        } else {
            graphTextView.setVisibility(View.GONE);
            lineChart.setVisibility(View.VISIBLE);
        }
        List<Entry> entries = new ArrayList<>();

//        dataList.add(0,0);
//        dateArrayList.add(0,epidemicStat.getBeginDate());
        if (dataList.size() == 1) {
            entries.add(new Entry(0, dataList.get(0)));
            entries.add(new Entry(1, dataList.get(0)));
            entries.add(new Entry(2, dataList.get(0)));
        } else {
            for (int i = 0; i < dataList.size(); i++) {
                entries.add(new Entry(i, dataList.get(i)));
            }
        }

        //entries.add(new Entry(0,1));

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#AABCC6"));//线条颜色
//        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setDrawValues(false);                     // 设置是否显示数据点的值
        dataSet.setDrawCircleHole(false);                 // 设置数据点是空心还是实心，默认空心
        dataSet.setCircleColor(Color.parseColor("#AABCC6"));              // 设置数据点的颜色
        dataSet.setCircleSize(1);                         // 设置数据点的大小
        dataSet.setHighLightColor(Color.parseColor("#AABCC6"));            // 设置点击时高亮的点的颜色
        dataSet.setLineWidth(2f);//线条宽度

        //设置样式
        YAxis rightAxis = lineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        //设置图表左边的y轴禁用
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(Color.parseColor("#333333"));

        //设置x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大后x轴标签重绘

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int tmpValue = (int) value;
                if (tmpValue < 0) {
                    tmpValue = 0;
                }
                if (tmpValue >= dateArrayList.size()) {
                    tmpValue = dateArrayList.size() - 1;
                }
                return simpleDateFormat.format(dateArrayList.get(tmpValue));
            }
        });

        //透明化图例
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);


        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

    }


    private void initData() {
        if (province.equals("")) {//解决刚进入的问题
            province = "all";
        }
        if (province.equals("all")) {//全国数据
            epidemicStat = StatAPI.getStatByCountry(country);
        } else {//省份数据
            epidemicStat = StatAPI.getStatByProvince(country, province);
        }
        ArrayList<EpidemicStat.Figure> figures = epidemicStat.getFigures();
        dataList.clear();
        dateArrayList.clear();
        Date curDate = epidemicStat.getBeginDate();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, -1);

        for (EpidemicStat.Figure figure : figures) {
            Integer data;
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            if (type.equals("confirmed")) {
                data = figure.confirmed;
            } else if (type.equals("suspected")) {
                data = figure.suspected;
            } else if (type.equals("cured")) {
                data = figure.cured;
            } else if (type.equals("dead")) {
                data = figure.dead;
            } else if (type.equals("severe")) {
                data = figure.severe;
            } else if (type.equals("risk")) {
                data = figure.risk;
            } else {
                data = figure.inc24;
            }
            if (data != null) {
                dataList.add(data);
                dateArrayList.add(date);
            }
        }

    }
}