package com.example.user.music;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;
import com.daimajia.slider.library.SliderLayout;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SliderLayout mDemoSlider;
    private GridView gridView;
    ImageView slider_img;
    private Context mContext;
    ActionBarDrawerToggle toggle;
    private ArrayList<Products> entityArrayList = new ArrayList<>();
    ImageView img_drawer;
    private RecyclerView.LayoutManager mLayoutManager;
    private CollapsingToolbarLayout collapsingToolbar;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slider_img=(ImageView)findViewById(R.id.slider);
        slider_img.setImageResource(R.drawable.calendar);
        RecyclerView rv = (RecyclerView)findViewById(R.id.my_recycler_view);
        rv.setHasFixedSize(true);
        prepareNewsData();
        adapter = new CustomAdapter(entityArrayList, new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Products item) {
                Intent intent = new Intent(getApplicationContext(), MusicCategories
                        .class);
                intent.putExtra("data", (Serializable) item);
                startActivity(intent);
            }
        });
        mLayoutManager = new GridLayoutManager(mContext,2);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }
    private void prepareNewsData() {
        entityArrayList.add(new Products("Nepali Geet",""
               , R.drawable.mn, ""
        ));
        entityArrayList.add(new Products("Nepali FM",""
                , R.drawable.radio, ""
        ));
        entityArrayList.add(new Products("fm radio",""
                , R.drawable.radio, ""
        ));
        entityArrayList.add(new Products("fm radio",""
                , R.drawable.mn, ""
        ));
        entityArrayList.add(new Products("fm radio",""
                , R.drawable.mn, ""
        ));
        entityArrayList.add(new Products("fm radio",""
                , R.drawable.mn, ""
        ));
        entityArrayList.add(new Products("fm radio",""
                , R.drawable.mn, ""
        ));
        entityArrayList.add(new Products("fm radio",""
                , R.drawable.mn, ""
        ));
    }
}
