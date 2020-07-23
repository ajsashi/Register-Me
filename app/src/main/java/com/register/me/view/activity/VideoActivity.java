package com.register.me.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.register.me.R;

public class VideoActivity extends YouTubeBaseActivity {
    private final String API_KEY = "AIzaSyBfriUmBC6XSI7V9FtFHyaYyG-1caS1eF4";
    private String VIDEO_CODE ="";
    YouTubePlayerView player;
    YouTubePlayer youTube_Player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        player = (YouTubePlayerView) findViewById(R.id.player);
        if(getIntent()!=null){
            VIDEO_CODE = getIntent().getStringExtra("ID");
        }
        player.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTube_Player = youTubePlayer;
                    youTube_Player.loadVideo(VIDEO_CODE);
                    youTube_Player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
//                    Toast.makeText(VideoActivity.this, youTubePlayer.getDurationMillis() + "", Toast.LENGTH_SHORT).show();
                    youTube_Player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                        @Override
                        public void onLoading() {
//                            Toast.makeText(VideoActivity.this, "onLoading", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onLoaded(String s) {
//                            Toast.makeText(VideoActivity.this, "onLoaded", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onAdStarted() {

                        }

                        @Override
                        public void onVideoStarted() {
//                            Toast.makeText(VideoActivity.this, "onVideoStarted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVideoEnded() {
//                            Toast.makeText(VideoActivity.this, "onVideoEnded", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.putExtra("STATUS","Completed");
                            setResult(1,intent);
                            finish();

                        }

                        @Override
                        public void onError(YouTubePlayer.ErrorReason errorReason) {

                        }
                    });
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

    }

}
