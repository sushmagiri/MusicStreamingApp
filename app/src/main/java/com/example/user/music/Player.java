package com.example.user.music;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.IOException;

public class Player extends AppCompatActivity implements View.OnClickListener {



    private SeekBar seekbar;
    private ImageButton buttonPlay;
    TextView songTitle,tv_time;
    private ImageButton buttonStopPlay;
    private MediaPlayer player;
    Context context;
    String site_url;
    Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("data");
        site_url = item.getUrl();
        Log.d("value", site_url);
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
        songTitle=(TextView)findViewById(R.id.songName);
        tv_time=(TextView)findViewById(R.id.time);
        songTitle.setText(item.getFilename());
        seekbar=(SeekBar)findViewById(R.id.seekbar);
        buttonStopPlay = (ImageButton) findViewById(R.id.buttonStopPlay);
        buttonStopPlay.setEnabled(false);
        buttonStopPlay.setOnClickListener(this);
        initializeMediaPlayer();

    }




    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(site_url);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == buttonPlay) {
            startPlaying();
        } else if (view == buttonStopPlay) {
            stopPlaying();
        }
    }
    private void startPlaying() {
        buttonStopPlay.setEnabled(true);
        buttonPlay.setEnabled(false);
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
                int mFileDuration=player.getDuration()/1000;
                Log.d("duration","value:"+mFileDuration);

                seekbar.setMax(mFileDuration);


            }
        });


        final Handler mHandler=new Handler();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(player!= null){
                    int mCurrentPosition = player.getCurrentPosition() / 1000;
                    seekbar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }


        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(player != null && fromUser){
                    player.seekTo(progress * 1000);
                }
            }
        });




    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }

        buttonPlay.setEnabled(true);
        buttonStopPlay.setEnabled(false);

    }


    @Override
    protected void onPause() {

        super.onPause();
        if (player.isPlaying()) {

            player.stop();
        }
    }


}
