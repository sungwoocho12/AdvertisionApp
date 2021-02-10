package com.example.advertisingapp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TopActivity extends AppCompatActivity {


    ViewPager2 viewPager2;
    ArrayList<VideoModel> videos;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager2 = (ViewPager2)findViewById(R.id.viewpager);
        videos = new ArrayList<>();

/*        FirebaseRecyclerOptions<VideoModel> options =
                new FirebaseRecyclerOptions.Builder<VideoModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("videos"), VideoModel.class)
                .build();*/

        VideoModel ob1 = new VideoModel("https://learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4","그대와 함께","2020/08/31");
        videos.add(ob1);

        VideoModel ob2 = new VideoModel("https://docjamal.xyz/wp-content/uploads/2020/08/video2.mp4","하루의 끝","2020/09/05");
        videos.add(ob2);

        VideoModel ob3 = new VideoModel("https://docjamal.xyz/wp-content/uploads/2020/08/video4.mp4","오늘도 고마워","2020/09/21");
        videos.add(ob3);

        VideoModel ob4 = new VideoModel("https://docjamal.xyz/wp-content/uploads/2020/08/video5.mp4","잊혀진건 잊혀진 대로","2020/10/01");
        videos.add(ob4);

        VideoModel ob5 = new VideoModel("https://docjamal.xyz/wp-content/uploads/2020/08/video6.mp4","아름다운 저녁","2020/12/25");
        videos.add(ob5);

        VideoModel ob6 = new VideoModel("https://docjamal.xyz/wp-content/uploads/2020/08/video10.mp4","괜찮아","2021/02/02");
        videos.add(ob6);

        viewPager2.setAdapter(new VideoAdapter(videos));

    }

}
