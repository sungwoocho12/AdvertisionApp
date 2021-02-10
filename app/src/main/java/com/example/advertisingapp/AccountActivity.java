package com.example.advertisingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AccountActivity extends AppCompatActivity {

    ImageView userPhoto;
    static int PReqCode = 1;
    static int REQUEST_CODE = 1;
    Intent galleryIntent;

    Uri pickIconUri;

    Button createAccount;
    private EditText userEmail, userPassword, userPassword2, userName;
    private ProgressBar loading;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        //user Init
        userEmail = (EditText)findViewById(R.id.userEmail);
        userName = (EditText)findViewById(R.id.userName);
        userPassword = (EditText)findViewById(R.id.userPassword);
        userPassword2 = (EditText)findViewById(R.id.userPassword2);

        //loading
        loading = (ProgressBar)findViewById(R.id.regProgressBar);


        loading.setVisibility(View.INVISIBLE);

        //create Button
        createAccount = (Button)findViewById(R.id.createAccountBtn);


        //FirebaseAute 객체에 현재 상태를 담음

        firebaseAuth = FirebaseAuth.getInstance();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading.setVisibility(View.INVISIBLE);
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = userPassword2.getText().toString();
                final String name = userName.getText().toString();

                if(email.isEmpty() || name.isEmpty() || password.isEmpty()
                        || !password.equals(password2)){
                    Toast.makeText(AccountActivity.this,"모든 정보를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    createAccount.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.INVISIBLE);


                }else{
                    //모든 자료가 들어 왔을 때 회원가입 완료
                    CreateUserAccount(email,name,password,password2);
                }




            }
        });

        //유저 이미지 설정
        userPhoto =(ImageView)findViewById(R.id.userIcon);

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();

                }
                openGallery();

            }
        });
    }


    //유저 회원가입 메서드 설정
    private void CreateUserAccount(String email, String name, String password, String password2) {

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //유저가 성공적으로 계정을 만들었을 때

                    Toast.makeText(AccountActivity.this,"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show();

                    //만들어진 계정에 사진과 정보들을 업데이트 할 메서드
                    updateUserInfo(name,pickIconUri, firebaseAuth.getCurrentUser());
                    loading.setVisibility(View.INVISIBLE);



                }else {

                    Toast.makeText(AccountActivity.this,"회원가입에 실패하였습니다."+task.getException().toString(),Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    //사용자 사진과 이름 업데이트
    private void updateUserInfo(String name, Uri pickIconUri, FirebaseUser currentUser) {

        //파이어베이스 스토리지에 사진을 업로드 하고 url 받아오기

        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        StorageReference imageFilePath = firebaseStorage.child(pickIconUri.getLastPathSegment());
        imageFilePath.putFile(pickIconUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //이미지 업로드 성공
                //url 받아오기

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //유저 이미지를 갖고 있는 url

                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                                setDisplayName(name).setPhotoUri(uri).build();

                        currentUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    //유저 정보가 성공적으로 업데이트가 되었을 때
                                    Toast.makeText(AccountActivity.this,"유저가 등록되었습니다.",Toast.LENGTH_SHORT).show();

                                }
                                //updateUI();


                            }
                        });
                    }
                });
            }
        });
    }


    //ResultActivity 사용
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {

            //사용자 사진 픽 성공
            pickIconUri = data.getData();
            userPhoto.setImageURI(pickIconUri);

        }
    }


    //유저 사진첩 열고 저장하는 클래스
    private void openGallery() {
        //겔러리 열기 인텐트, 사용자 사진 선택
        galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_CODE);


        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT, Uri.parse("content://media/internal/images/media"));
        startActivityForResult(mIntent, REQUEST_CODE);
    }



    //유저 사진첩 권한 설정 클래스
    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(AccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(AccountActivity.this,"사진첩 권한을 설정해주세요.",Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(AccountActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }


    }

    public void mOnclick(View view){

        galleryIntent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(galleryIntent);
        finish();


    }

}
