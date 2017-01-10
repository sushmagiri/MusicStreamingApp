package com.example.user.music;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterModel extends RecyclerView.Adapter<CustomAdapterModel.CustomViewHolder> {
    ArrayList<Item> entityArray;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item,int position);
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_model, null);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;

    }
    public CustomAdapterModel(ArrayList<Item> entityArray, OnItemClickListener listener) {
        this.entityArray = entityArray;
        this.listener=listener;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.bind(entityArray.get(position), listener);


    }

    @Override
    public int getItemCount() {
        return entityArray.size();

    }



    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView filename,title;

        public CustomViewHolder(final View itemView) {

            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card);
            filename=(TextView)itemView.findViewById(R.id.item_filename);

            title=(TextView) itemView.findViewById(R.id.item_title);

        }


        public void bind(final Item item, final OnItemClickListener listener) {
            filename.setText(item.getFilename());
            title.setText(item.getLength());



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item,getPosition());
                }
            });
        }
    }

}
