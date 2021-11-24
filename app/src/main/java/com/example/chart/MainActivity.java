package com.example.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    line_chart line_chart;
    bar_chart bar_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        line_chart=(line_chart)findViewById(R.id.line_chart);
        line_chart.setLabelX(60,6);
        line_chart.setLabelY(50,10,10);
        line_chart.setLabelText1("心律");
        line_chart.setLabelText2("血氧");
        //line_chart.setCircle(false);
        line_chart.setGetRandomData(true);

        //---------------------------------------------------
        bar_chart=(bar_chart)findViewById(R.id.bar_chart);
        bar_chart.setLabelX(100,10);
        bar_chart.setLabelY(100,40,5);
        bar_chart.setLabelText1("卡路里");
        //line_chart.setCircle(false);
        bar_chart.setGetRandomData(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater(); //实例化一个MenuInflater对象
        inflater.inflate(R.menu.menu,menu);   //解析菜单文件
        return super.onCreateOptionsMenu(menu);
    }
    boolean _Switch=true;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.Switch:

                if (_Switch==true){
                    _Switch=false;
                    line_chart.setVisibility(View.VISIBLE);
                    bar_chart.setVisibility(View.INVISIBLE);


                }else {
                    _Switch=true;
                    bar_chart.setVisibility(View.VISIBLE);
                    line_chart.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}