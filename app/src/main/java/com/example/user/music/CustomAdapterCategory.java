package com.example.user.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterCategory extends RecyclerView.Adapter<CustomAdapterCategory.CustomViewHolder> {
    ArrayList<Item> entityArray;
    private final OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(Item item, int position);
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_category, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;

    }
    public CustomAdapterCategory(ArrayList<Item> entityArray, OnItemClickListener listener) {
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
        public TextView filename;
        private  Context context;

        public CustomViewHolder(final View itemView) {

            super(itemView);
            context=itemView.getContext();
            cardView=(CardView)itemView.findViewById(R.id.card);
            filename=(TextView)itemView.findViewById(R.id.item_filename);



        }


        public void bind(final Item item, final OnItemClickListener listener) {
            filename.setText(item.getFilename());




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                     Intent intent=new Intent();
//                    listener.onItemClick(item,getPosition());
                    if (getPosition() == 0){
                        intent =  new Intent(context, Band.class);
                    } else if (getPosition() == 1){
                        intent =  new Intent(context,PlayMusic.class);
                    }
                    else {
                        intent =  new Intent(context,PlayMusic.class);

                    }

                    context.startActivity(intent);
                }

            });
        }
    }

}
