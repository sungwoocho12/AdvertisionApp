package com.example.advertisingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    CardView account, community, login,  show, top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnclick(View view){

        Intent intent;


        login = (CardView)findViewById(R.id.mlogin);
        account = (CardView)findViewById(R.id.mAccount);
        community = (CardView)findViewById(R.id.mCommunity);
        login = (CardView)findViewById(R.id.mlogin);
        show = (CardView)findViewById(R.id.mShowVideo);
        top = (CardView)findViewById(R.id.mTopVideo);

        if(view.getId() == R.id.mlogin){

            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.mAccount){

            intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.mTopVideo){

            intent = new Intent(this, TopActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.mCommunity){

            intent = new Intent(this, CommunityActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.mShowVideo){

            intent = new Intent(this, ShowActivity.class);
            startActivity(intent);
        }

    }
}