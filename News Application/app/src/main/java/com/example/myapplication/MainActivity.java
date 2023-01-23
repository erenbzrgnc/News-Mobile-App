package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import java.util.List;


public class MainActivity extends AppCompatActivity {
   TabLayout tabLayout;
   ViewPager2 viewPager2;
   CategoryAdapter myCategoryAdaptor;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("CS310 News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_newspaper_24);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        myCategoryAdaptor = new CategoryAdapter(this);
        viewPager2.setAdapter(myCategoryAdaptor);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

    }






}