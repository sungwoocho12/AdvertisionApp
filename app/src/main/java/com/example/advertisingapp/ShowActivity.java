package com.example.advertisingapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShowActivity extends AppCompatActivity {


    BottomNavigationView btnViewId;
    FrameLayout frameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        btnViewId = findViewById(R.id.bottomNavViewId);
        frameLayout= findViewById(R.id.frameLayoutInXml);

        btnViewId.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.mypageId){

                    setFragment(new FragmentMyPage());
                    return true;

                }else if(item.getItemId() == R.id.videoId){

                    setFragment(new FragmentVideo());
                    return true;
                }else if(item.getItemId() == R.id.informationId){

                    setFragment(new FragmentLocation());
                    return true;
                }
                return false;
            }
        });


    }

    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutInXml, fragment);

        fragmentTransaction.commit();

    }

}
