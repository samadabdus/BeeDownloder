package com.shootbee.tamada.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shootbee.tamada.R;
import com.shootbee.tamada.activity.PreviewActivity;
import com.shootbee.tamada.model.Video;
import com.shootbee.tamada.util.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class EmojisVideodsAdapter extends RecyclerView.Adapter <EmojisVideodsAdapter.EmojisVideosViewHolder> {

    Context context;
    List<Video> lstVideos;

    public EmojisVideodsAdapter(Context context, List<Video> videos){

        this.context = context;
        this.lstVideos = videos;

    }

    @NonNull
    @Override
    public EmojisVideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_emoji_videos,null);
        EmojisVideosViewHolder emojisVideosViewHolder = new EmojisVideosViewHolder(view);
        return emojisVideosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmojisVideosViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.isMemoryCacheable();
        String videoUrl = Utils.IMAGE_URL+lstVideos.get(position).getVideoUrl();
        Glide.with(context)
                .load(videoUrl)
                .apply(requestOptions)
                .thumbnail(Glide.with(context).load(videoUrl))
                .into(holder.emojiVideos);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PreviewActivity.class);
                intent.putExtra("video_url",Utils.IMAGE_URL+lstVideos.get(position).getVideoUrl());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return lstVideos.size();
    }

    public class EmojisVideosViewHolder extends RecyclerView.ViewHolder{

        public  View view;
        public ImageView emojiVideos;

        public EmojisVideosViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            emojiVideos = view.findViewById(R.id.imgView_row_layout_emoji_videos);
        }
    }
}
