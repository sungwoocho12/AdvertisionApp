package com.example.advertisingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    List<MyData> myDataList;
    Context context;

    public CustomAdapter(List<MyData> myDataList, Context context){
        this.myDataList = myDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.data_in_holder, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageResource(myDataList.get(position).getImage());
        holder.textView.setText(myDataList.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imgaeView);

        }
    }
}
