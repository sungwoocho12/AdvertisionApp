package com.example.advertisingapp;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.myViewHolder>{

    ArrayList<VideoModel> videos;

    public VideoAdapter(ArrayList<VideoModel> videos){
        this.videos = videos;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.setData(videos.get(position));

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        VideoView videoView;
        TextView title, desc;
        ProgressBar progressBar;

        public myViewHolder(@NonNull View itemView) {

            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            title = itemView.findViewById(R.id.textVideoTitle);
            desc = itemView.findViewById(R.id.textVideoDescription);
            progressBar = itemView.findViewById(R.id.videoProgress);
        }
        void setData(VideoModel obj){

            videoView.setVideoPath(obj.getVideoUrl());
            title.setText(obj.getTitle());
            desc.setText(obj.getDesc());

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                    mp.start();
                }
            });


            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });
        }

    }
}
