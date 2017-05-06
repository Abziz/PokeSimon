package com.example.abziz.simon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartMenuActivity extends AppCompatActivity {
    MediaPlayer mp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);
        playMusic(R.raw.themesong,0.2f);
        UpdateListeners();
    }


    private void playMusic(int song,float vol) {
        mp = MediaPlayer.create(this, song);
        mp.setLooping(true);
        mp.setVolume(vol,vol);
        mp.start();
    }

    private void UpdateListeners(){
        ImageButton btn = (ImageButton) findViewById(R.id.start_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartGame();
            }
        });
    }

    @Override
    protected void onStop()
    {
        if (mp != null)
            mp.stop();
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mp.stop();
        mp.release();
        playMusic(R.raw.themesong,0.2f);
    }

    private void StartGame(){
        startActivity(new Intent(this, GameActivity.class));
    }

}
