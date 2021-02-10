package com.example.advertisingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    private ImageButton postImage;
    private EditText mPostTitle, mPostDesc;
    private Button mAddPostBtn;
    private ProgressBar pProgressBar;



    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private FirebaseFirestore mFirebaseStore;
    String title,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mStorage = FirebaseStorage.getInstance().getReference();

        postImage = (ImageButton)findViewById(R.id.post_Image);


        mPostTitle = (EditText)findViewById(R.id.post_Title);
        mPostDesc = (EditText)findViewById(R.id.post_Description);
        mAddPostBtn = (Button)findViewById(R.id.post_Btn);
        pProgressBar = (ProgressBar)findViewById(R.id.postProgressbar);

        mFirebaseStore = FirebaseFirestore.getInstance();
        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        mAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = mPostTitle.getText().toString();
                description = mPostDesc.getText().toString();

                startPosting(title, description);

            }
        });



    }

    private void startPosting(String postTitle, String postDesc) {

        mProgress.setMessage("Posting to Community ..");

        if(postTitle.isEmpty() && postDesc.isEmpty()) {
            Toast.makeText(PostActivity.this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else{

            StorageReference filepath = mStorage.child("Blog_Images").child(postTitle);

            mStorage.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap<String, Object> postMap = new HashMap<>();
                                postMap.put("image", uri.toString());
                                postMap.put("title", postTitle);
                                postMap.put("description",postDesc);

                                mFirebaseStore.collection("Documents").add(postMap)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if(task.isSuccessful()){
                                            startActivity(new Intent(PostActivity.this,CommunityActivity.class));
                                            Toast.makeText(PostActivity.this,"포스트 게시 완료",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else{
                                            Toast.makeText(PostActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();
            postImage.setImageURI(mImageUri);


        }

    }
}