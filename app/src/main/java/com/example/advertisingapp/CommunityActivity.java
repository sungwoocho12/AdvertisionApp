package com.example.advertisingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {

    private RecyclerView pRecyclerView;
    private FirebaseFirestore db;
    public PostAdapter postAdapter;
    private List<PostModel> list;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);


        pRecyclerView = (RecyclerView)findViewById(R.id.postRecyclerview);
        pRecyclerView.setHasFixedSize(true);
        pRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        postAdapter = new PostAdapter(CommunityActivity.this, list);
        pRecyclerView.setAdapter(postAdapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper(postAdapter));
        touchHelper.attachToRecyclerView(pRecyclerView);

        showData();

    }

    private void showData() {

        db.collection("Documents").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot : task.getResult()){
                            PostModel model = new PostModel(snapshot.getString("id"), snapshot.getString("title"), snapshot.getString("Desc") );
                            list.add(model);

                        }

                        postAdapter.notifyDataSetChanged();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CommunityActivity.this,"이런...뭔ㄱ ㅏ잘못 되었어..",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.community_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){

            startActivity(new Intent(CommunityActivity.this, FirestoreUpload.class));

        }else if(item.getItemId() == R.id.action_settings){

        }

        return super.onOptionsItemSelected(item);
    }
}
