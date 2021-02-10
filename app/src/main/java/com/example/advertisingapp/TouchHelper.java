package com.example.advertisingapp;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {


    private PostAdapter postAdapter;
    public TouchHelper( PostAdapter postAdapter){

        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.postAdapter = postAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){

            postAdapter.updateData(position);
            postAdapter.notifyDataSetChanged();
        }else {
            postAdapter.deleteData(position);
        }


    }


}
