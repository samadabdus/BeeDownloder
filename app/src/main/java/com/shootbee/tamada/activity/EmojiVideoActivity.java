package com.shootbee.tamada.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shootbee.tamada.R;
import com.shootbee.tamada.adapter.EmojisAdapter;
import com.shootbee.tamada.adapter.EmojisVideodsAdapter;
import com.shootbee.tamada.api.CommonClassForAPI;
import com.shootbee.tamada.model.Option;
import com.shootbee.tamada.model.Video;
import com.shootbee.tamada.util.Utils;

import java.util.List;

public class EmojiVideoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EmojisVideodsAdapter emojisVideodsAdapter;
    TextView tvOption;
    int optionId;
    String optionName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji_video);
        optionId = getIntent().getIntExtra("optionId",-1);
        optionName = getIntent().getStringExtra("optionName");
        tvOption = findViewById(R.id.tv_header);
        tvOption.setText(optionName+" Videos");
        recyclerView = findViewById(R.id.rv_emojiVideos);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
        refreshList();

    }
    private void refreshList()
    {
        Utils.showProgressDialog(EmojiVideoActivity.this);
        CommonClassForAPI commonClassForAPI = CommonClassForAPI.getInstance(this);
        commonClassForAPI.getVideosList(Utils.BASE_URL+"videos/bycategory/"+optionId, new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                Utils.hideProgressDialog(EmojiVideoActivity.this);
                if(response.isSuccessful() && response.code() == 200)
                {
                    emojisVideodsAdapter = new EmojisVideodsAdapter(EmojiVideoActivity.this, response.body());
                    recyclerView.setAdapter(emojisVideodsAdapter);
                    emojisVideodsAdapter.notifyDataSetChanged();
                }
                else
                    Utils.setToast(EmojiVideoActivity.this,"Error loading Videos List");
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                Utils.hideProgressDialog(EmojiVideoActivity.this);
                Utils.setToast(EmojiVideoActivity.this,t.getMessage());
            }
        });
    }
}