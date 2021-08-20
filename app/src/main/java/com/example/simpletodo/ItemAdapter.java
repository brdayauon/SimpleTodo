package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener onClickListener;


    public interface OnLongClickListener {

        void onItemLongClick(int position);
    }

    public interface OnClickListener {
        void onItemOnClick(int position);
    }


    public ItemAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

         TextView tvItem;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);
             tvItem = itemView.findViewById(android.R.id.text1);
        }

         public void bind(String item) {
             tvItem.setText(item);

             tvItem.setOnClickListener(l -> {
                 onClickListener.onItemOnClick(getAdapterPosition());

            });
             tvItem.setOnLongClickListener(l -> {

                longClickListener.onItemLongClick(getAdapterPosition());
                 return true;
            });
        }

    }
}