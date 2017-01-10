package com.example.user.music;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cleveroad.audiovisualization.DbmHandler;
import com.cleveroad.audiovisualization.GLAudioVisualizationView;
import com.cleveroad.audiovisualization.VisualizerDbmHandler;

import java.util.concurrent.TimeUnit;

public class Visual extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout frameLayout;
    Context context;
    String site_url;
    Item item;
    TextView songTitle,tv_time;
    private SeekBar seekBar;
    private Button btnBack;
    private Button btnForward;
    private ImageButton btnStart;
    private ImageButton btnPause;
    private TextView txtMaxTime;
    private TextView txtCurrentTime;

    private GLAudioVisualizationView glAudioVisualizationView;

    private MediaPlayer mediaPlayer;

    private Handler seekHandler = new Handler();
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("data");
        site_url = item.getUrl();
        Log.d("value", site_url);
        frameLayout = (FrameLayout) findViewById(R.id.visualization);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        songTitle=(TextView)findViewById(R.id.song);
        songTitle.setText(item.getFilename());
        btnBack = (Button) findViewById(R.id.btn_back);
        btnForward = (Button) findViewById(R.id.btn_forward);
        btnStart = (ImageButton) findViewById(R.id.btn_start);
        btnPause = (ImageButton) findViewById(R.id.btn_pause);

        txtCurrentTime = (TextView) findViewById(R.id.txt_current_time);
        txtMaxTime = (TextView) findViewById(R.id.txt_max_time);

        glAudioVisualizationView = new GLAudioVisualizationView.Builder(this)
                //.setBubblesSize(R.dimen.bubble_size)
                //.setBubblesRandomizeSize(true)
                .setWavesHeight(R.dimen.wave_height)
                .setWavesFooterHeight(R.dimen.footer_height)
                .setWavesCount(4)
                .setLayersCount(2)
                .setBackgroundColorRes(R.color.v_color_bg)
                .setLayerColors(R.array.v_colors)
//                .setBubblesPerLayer(16)
                .build();

        mediaPlayer = MediaPlayer.create(this, Uri.parse(site_url));

        btnPause.setEnabled(false);
        seekBar.setClickable(false);

        glAudioVisualizationView.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        frameLayout.addView(glAudioVisualizationView);

        VisualizerDbmHandler visualizerHandler = DbmHandler.Factory.newVisualizerHandler(this, mediaPlayer);
        glAudioVisualizationView.linkTo(visualizerHandler);


        btnStart.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnForward.setOnClickListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(started) {
            glAudioVisualizationView.onResume();
            mediaPlayer.start();
        }
    }

    @Override
    public void onPause() {
        glAudioVisualizationView.onPause();
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        glAudioVisualizationView.release();
        if(mediaPlayer!=null)
            mediaPlayer.release();
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_back:
                rewindMusic();
                break;
            case R.id.btn_forward:
                forwardMusic();
                break;
            case R.id.btn_start:
                startPlaying();
                break;
            case R.id.btn_pause:
                pauseMusic();
                break;
        }

    }
    private String secondsToString(int seconds)  {
        long minutes = TimeUnit.SECONDS.toMinutes((long) seconds);
        seconds %= 60;
        return minutes+":"+ seconds;
    }

    public void startPlaying(){
        started = true;
        int duration = mediaPlayer.getDuration()/1000;

        int currentPosition = mediaPlayer.getCurrentPosition()/1000;
        if(currentPosition== 0)  {
            seekBar.setMax(duration);
            String maxTimeString = secondsToString(duration);
            txtMaxTime.setText(maxTimeString);
        } else if(currentPosition== duration)  {
            this.mediaPlayer.reset();
        }
        this.mediaPlayer.start();

        Runnable seekRunnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition = mediaPlayer.getCurrentPosition()/1000;
                String currentPositionStr = secondsToString(currentPosition);
                txtCurrentTime.setText(currentPositionStr);

                seekBar.setProgress(currentPosition);
                seekHandler.postDelayed(this, 1000);
            }
        };
        seekHandler.postDelayed(seekRunnable, 1000);

        this.btnPause.setEnabled(true);
        this.btnStart.setEnabled(false);
    }

    public void pauseMusic()  {
        started = false;
        this.mediaPlayer.pause();
        this.btnPause.setEnabled(false);
        this.btnStart.setEnabled(true);
    }


    public void rewindMusic()  {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int SUBTRACT_TIME = 5000;
        if(currentPosition - SUBTRACT_TIME > 0 )  {
            this.mediaPlayer.seekTo(currentPosition - SUBTRACT_TIME);
        }
    }

    public void forwardMusic()  {
        int currentPosition = this.mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
        int ADD_TIME = 5000;
        if(currentPosition + ADD_TIME < duration)  {
            this.mediaPlayer.seekTo(currentPosition + ADD_TIME);
        }
    }
}
