package com.example.user.music;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayMusic extends AppCompatActivity {
    private ArrayList<Item> entityArray = new ArrayList<>();
    Intent intent;
    RecyclerView rv;
    private Context mContext;

    private RecyclerView.LayoutManager mLayoutManager;
    CustomAdapterModel adapter;
    String json_url = "http://archive.org/metadata/NepaliHighQualityMp3Songs?output=json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, json_url, null,
                new Response.Listener<JSONObject>()
                {String url;
                    String dir,d2,name,length;
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Request", response.toString());
                        try {
                     dir=  response.getString("dir");
                     d2=response.getString("d1");

                            JSONArray files = response.getJSONArray("files");


                            for (int i = 0; i < files.length(); i++) {
                                JSONObject jsonObject = files.getJSONObject(i);
                                Log.d("json array length", String.valueOf(files.length()));
                                name=jsonObject.getString("name");
                                length=jsonObject.getString("format");
                                url= "http://"+d2+dir+"//"+name;
                                Item item = new Item(name,length,url);
                                entityArray.add(item);


                            }


                            adapter = new CustomAdapterModel(entityArray, new CustomAdapterModel.OnItemClickListener() {
                                @Override
                                public void onItemClick(Item item,int position) {
                                     intent = new Intent(getApplicationContext(),Player.class);
                                    intent.putExtra("data",entityArray.get(position));
                                    startActivity(intent);
                                }
                            });
                            rv.setAdapter(adapter);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error....", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

//    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

}



