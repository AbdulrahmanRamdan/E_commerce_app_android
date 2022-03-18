package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.example.ecommerce.DataBase.EcommerceDataBase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //for Bie Chart
        PieChart pieChart = findViewById(R.id.piechart_graph);

        ArrayList<PieEntry> categories = new ArrayList<>();

        EcommerceDataBase dbobj = new EcommerceDataBase(getApplicationContext());
        Cursor c = dbobj.PieChartFunction();
        Hashtable<String , Integer> proid_quantity = new Hashtable<>();
        while(!c.isAfterLast()){
            int proid = Integer.parseInt(c.getString(0));
            String categoryname = dbobj.RetriveCategoryNameByProductID(proid);
            int quantity =  Integer.parseInt(c.getString(1));
            if(proid_quantity.containsKey(categoryname) == true){
                proid_quantity.put(categoryname , proid_quantity.get(categoryname) + 1);
            }
            else{
                proid_quantity.put(categoryname , quantity);
            }
            c.moveToNext();
        }

        Set<String> keys = proid_quantity.keySet();
        for (String category_name: keys) {
            categories.add(new PieEntry(proid_quantity.get(category_name) , category_name));
        }



/*
        visitors.add(new PieEntry(508,"2016"));
        visitors.add(new PieEntry(600,"2016"));
        visitors.add(new PieEntry(750,"2016"));
        visitors.add(new PieEntry(600,"2016"));
        visitors.add(new PieEntry(670,"2016"));
*/

        PieDataSet pieDataSet = new PieDataSet(categories , "Categories");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Categories");
        pieChart.animate();


    }
}