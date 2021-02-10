package com.example.advertisingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class FirestoreUpload extends AppCompatActivity {

    private EditText mTitle, mDesc;
    private Button mSave;
    private FirebaseFirestore db;
    private String uTitle, uDesc, uId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore);


        mTitle = (EditText)findViewById(R.id.fTitle);
        mDesc = (EditText)findViewById(R.id.fDes);

        mSave = (Button)findViewById(R.id.mSave);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mSave.setText("Update");
            uTitle = bundle.getString("uTitle");
            uId = bundle.getString("uId");
            uDesc = bundle.getString("uDesc");
            mTitle.setText(uTitle);
            mDesc.setText(uDesc);
        }else {
            mSave.setText("Save");
        }

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String desc = mDesc.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id = uId;
                    updateToFireStore(id, title, desc);

                }else{
                    String id = uId;
                    saveToFireStore(id, title, desc);
                }

            }
        });
    }

    private void updateToFireStore(String id, String title, String desc) {

        db.collection("Documents").document(id).update("title",title,"desc",desc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(FirestoreUpload.this,"업데이트 완료!!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(FirestoreUpload.this,"에러" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FirestoreUpload.this,"뭔ㄱ ㅏ잘못 되었다...",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveToFireStore(String id, String title, String desc){
        if(!title.isEmpty() && !desc.isEmpty()){

            HashMap<String, Object> map = new HashMap<>();
            map.put("id",id);
            map.put("title", title);
            map.put("desc", desc);

            db.collection("Documents").document(title).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(FirestoreUpload.this, "data saved! ",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FirestoreUpload.this,CommunityActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FirestoreUpload.this, "Fail data saved! ",Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(FirestoreUpload.this,"모든 값을 입력해 주세요.",Toast.LENGTH_SHORT).show();
        }

    }


}
