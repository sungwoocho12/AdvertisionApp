package com.example.advertisingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentVideo extends Fragment {

    View v;
    RecyclerView recyclerView;
    List<MyData> myDataList;
    CustomAdapter customAdapter;

    public FragmentVideo(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewId);


        customAdapter = new CustomAdapter(myDataList, getContext());//MainActivity.this
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        return  v;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        myDataList = new ArrayList<>();
        myDataList.add(new MyData(R.drawable.soccer,"축구 황제의 도래! 한국 선수 최초 푸스카스상을 받다"));
        myDataList.add(new MyData(R.drawable.baseball,"류현진 이적 이후 완전히 올라온 폼!"));
        myDataList.add(new MyData(R.drawable.basketball,"서장훈 전 농구선수 이제는 방송인?"));
        myDataList.add(new MyData(R.drawable.frizon,"겨울왕국 시즌 3 김연아가 모델이다? 디지니에서 밝히는 놀라운 사실"));
        myDataList.add(new MyData(R.drawable.golf,"골프 여제 김인경 그녀의 끝은 어디까지 일까?"));

        myDataList.add(new MyData(R.drawable.soccer,"축구 황제의 도래! 한국 선수 최초 푸스카스상을 받다"));
        myDataList.add(new MyData(R.drawable.baseball,"류현진 이적 이후 완전히 올라온 폼!"));
        myDataList.add(new MyData(R.drawable.basketball,"서장훈 전 농구선수 이제는 방송인?"));
        myDataList.add(new MyData(R.drawable.frizon,"겨울왕국 시즌 3 김연아가 모델이다? 디지니에서 밝히는 놀라운 사실"));
        myDataList.add(new MyData(R.drawable.golf,"골프 여제 김인경 그녀의 끝은 어디까지 일까?"));
    }
}
