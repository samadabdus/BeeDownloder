package com.shootbee.tamada.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shootbee.tamada.R;
import com.shootbee.tamada.activity.EmojiVideoActivity;
import com.shootbee.tamada.model.Option;
import com.shootbee.tamada.util.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class EmojisAdapter extends RecyclerView.Adapter <EmojisAdapter.EmojisViewHolder>  {

    Context context;
    List<Option> options;

    public EmojisAdapter(Context context,List<Option> options){

        this.context = context;
        this.options = options;
    }
    @NonNull
    @Override
    public EmojisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_emojis,null);
        EmojisViewHolder emojisViewHolder = new EmojisViewHolder(view);
        return emojisViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmojisViewHolder holder, int position) {
        RequestOptions options_ = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        ;
        Glide.with(context).load(Utils.IMAGE_URL+options.get(position).getImageUrl()).apply(options_).into(holder.imgEmoji);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EmojiVideoActivity.class);
                intent.putExtra("optionId",options.get(position).getId());
                intent.putExtra("optionName",options.get(position).getOptionName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class EmojisViewHolder extends  RecyclerView.ViewHolder{

        public  View view;
        public CardView cvEmoji;
        public ImageView imgEmoji;
        public EmojisViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            cvEmoji = view.findViewById(R.id.cv_emoji);
            imgEmoji = view.findViewById(R.id.imgEmoji);
        }
    }


}
