package com.example.abziz.simon;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);
        UpdateListeners();
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

    private void StartGame(){
        startActivity(new Intent(this, GameActivity.class));
    }

}
