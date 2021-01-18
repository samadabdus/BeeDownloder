package com.shootbee.tamada.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.shootbee.tamada.R;
import com.shootbee.tamada.util.NotifyDownload;
import com.shootbee.tamada.util.Utils;

import java.io.OutputStream;

import static com.shootbee.tamada.util.Utils.RootDirectoryRoposo;
import static com.shootbee.tamada.util.Utils.RootDirectoryVideos;
import static com.shootbee.tamada.util.Utils.startDownload;

public class PreviewActivity extends AppCompatActivity {

    ImageView btnBackArrow;
    ImageView btn_shareVideo,btn_downlodeVideo;
    VideoView videoView;

    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    String videoUrl;
    ProgressDialog pDialog;
    long downloadId;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        videoUrl = getIntent().getStringExtra("video_url");
        videoView = findViewById(R.id.video_view);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(videoView);
        videoView.setMediaController(controller);
        // Start hare Step2.....
        /*requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, R.string.msg, REQUEST_PERMISSION);*/
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadId == reference) {
                   Utils.shareVideo(PreviewActivity.this,Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+RootDirectoryVideos+"/"+fileName);
                }
            }
        };
        registerReceiver(receiver, filter);


        btn_shareVideo = findViewById(R.id.imgview_shareVideo);
        btn_downlodeVideo = findViewById(R.id.imgview_downlodeVideo);
        btnBackArrow = findViewById(R.id.imgview_backArrow);

        btn_downlodeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.startDownload(videoUrl, RootDirectoryVideos, PreviewActivity.this, fileName);
            }
        });

        btn_shareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = "video"+System.currentTimeMillis() + ".mp4";
              Utils.notifyDownload(videoUrl, RootDirectoryVideos, PreviewActivity.this, fileName, new NotifyDownload() {
                  @Override
                  public void notifyDownload(long id) {
                      downloadId = id;
                  }
              });
            }
        });

        btnBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PreviewActivity.this, EmojiVideoActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(PLAYBACK_TIME, videoView.getCurrentPosition());
    }

    private void initializePlayer() {
        // Show the "Buffering..." message while the video loads.
        pDialog.show();
        Uri videoUri = getMedia(videoUrl);
        videoView.setVideoURI(videoUri);
        videoView.setZOrderOnTop(true);
        // Listener for onPrepared() event (runs after the media is prepared).
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        // Hide buffering message.
                        if(pDialog.isShowing())
                            pDialog.dismiss();
                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            videoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            videoView.seekTo(1);
                        }

                        // Start playing!
                        videoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        videoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        // Return the video position to the start.
                        videoView.seekTo(0);
                    }
                });
    }
    private void releasePlayer() {
        videoView.stopPlayback();
    }

    // Get a Uri for the media sample regardless of whether that sample is
    // embedded in the app resources or available on the internet.
    private Uri getMedia(String mediaName) {

            // Media name is an external URL.
            return Uri.parse(mediaName);
    }

}