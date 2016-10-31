package com.flywu.mediaplayer;


import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoPlayer extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;
    private Button play;
    private Button pause;
    private Button replay;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_player);
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        replay = (Button) findViewById(R.id.replay);
        videoView = (VideoView) findViewById(R.id.video_view);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        mediaController = new MediaController(this);
        initVideoPath();
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(),
                "movie.mp4");
        videoView.setVideoPath(file.getAbsolutePath()); // 指定视频文件的路径
        System.out.println(file.getPath());

        //VideoView与MediaController进行关联
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.requestFocus();//让VideoView获取焦点
        // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
        mediaController.setPrevNextListeners(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(VideoPlayer.this, "下一个", Toast.LENGTH_LONG).show();
            }
        }, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(VideoPlayer.this, "上一个", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!videoView.isPlaying()) {
                    videoView.start(); // 开始播放
                }
                break;
            case R.id.pause:
                if (videoView.isPlaying()) {
                    videoView.pause(); // 暂时播放
                }
                break;
            case R.id.replay:
                if (videoView.isPlaying()) {
                    videoView.resume(); // 重新播放
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();//将 VideoView 所占用的资源释放掉。
        }
    }
}
