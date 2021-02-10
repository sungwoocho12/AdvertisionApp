package com.example.advertisingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText userMail, userPassword;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent goShowActivity;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = (EditText)findViewById(R.id.loginUserMail);
        userPassword = (EditText)findViewById(R.id.loginUserPassword);
        btnLogin = (Button)findViewById(R.id.loginBtn);
        loginProgress = (ProgressBar)findViewById(R.id.loginProgressBar);
        mAuth = FirebaseAuth.getInstance();
        goShowActivity = new Intent(this,ShowActivity.class);

        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginProgress.setVisibility(View.INVISIBLE);
                btnLogin.setVisibility(View.VISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"정보를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    signIn(mail, password);
                }
            }
        });
    }

    private void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){

                    loginProgress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);

                    Toast.makeText(LoginActivity.this,"로그인 성공" ,Toast.LENGTH_SHORT).show();

                    updateUI();

                }
                else {
                    Toast.makeText(LoginActivity.this,"로그인에 실패하였습니다." + task.getException(),Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void updateUI() {
        startActivity(goShowActivity);
        finish();
    }
    @Override
    protected void onStart(){

        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            // 로그인이 된 상태의 유저라면 바로 showActivity 로 이동
            updateUI();
        }
    }

}
