package com.example.user.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicCategories extends AppCompatActivity {
    private ArrayList<Item> entityArray = new ArrayList<>();
    Intent intent;
    RecyclerView rv;
    private Context mContext;
    CustomAdapterCategory.OnItemClickListener listener;

    private RecyclerView.LayoutManager mLayoutManager;
    CustomAdapterCategory adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_categories);
        rv = (RecyclerView) findViewById(R.id.my_recycler_view);
        rv.setHasFixedSize(true);
        prepareNewsData();

        adapter=new CustomAdapterCategory(entityArray,listener);
//        adapter = new CustomAdapterCategory(entityArray, new CustomAdapterCategory.OnItemClickListener() {
//            @Override
//            public void onItemClick(Item item, int position) {
//                Intent intent = new Intent(getApplicationContext(), Band.class);
//                intent.putExtra("data", (Serializable) item);
//                startActivity(intent);
//            }
//
//        });
        mLayoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

    }
    private void prepareNewsData() {
        entityArray.add(new Item("  1974 A.D","",""));
        entityArray.add(new Item("  Lok Dohori","",""));
        entityArray.add(new Item("  Adrin Pradhan","",""));
        entityArray.add(new Item("  Latest Songs","",""));
        entityArray.add(new Item("  Old Songs","",""));
        entityArray.add(new Item("  Albatross","",""));
        entityArray.add(new Item("  Cobweb","",""));
        entityArray.add(new Item("  Movies Songs","",""));
        entityArray.add(new Item("  The Edge Band","",""));



    }
    }

