package com.example.advertisingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyPostHolder> {

    private CommunityActivity fActivity;
    private List<PostModel> postModelList;
    private FirebaseFirestore db;

    public PostAdapter(CommunityActivity cActivity, List<PostModel> postModelList){
        this.fActivity = cActivity;
        this.postModelList = postModelList;
    }

    public void updateData(int position){

        PostModel item = postModelList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId",item.getId());
        bundle.putString("uTitle", item.getTitle());
        bundle.putString("uDesc",item.getDesc());
        Intent intent = new Intent(fActivity,CommunityActivity.class);
        intent.putExtras(bundle);
        fActivity.startActivity(intent);

    }

    public void deleteData(int position){
        PostModel item = postModelList.get(position);
        db.collection("Documents").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        notifyItemRemoved(position);
                    }
                });
    }

    @NonNull
    @Override
    public MyPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(fActivity).inflate(R.layout.postitem , parent, false);
       return new MyPostHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostHolder holder, int position) {

        holder.title.setText(postModelList.get(position).getTitle());
        holder.desc.setText(postModelList.get(position).getDesc());




    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public static class MyPostHolder extends RecyclerView.ViewHolder{

        TextView title, desc;


        public MyPostHolder(@NonNull View itemView) {
            super(itemView);



            title = itemView.findViewById(R.id.title_text);
            desc = itemView.findViewById(R.id.description_text);
        }
    }
}
