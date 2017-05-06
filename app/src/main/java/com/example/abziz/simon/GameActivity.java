package com.example.abziz.simon;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GameActivity extends AppCompatActivity {
    MediaPlayer mp;
    private final Handler hand = new Handler();
    private SoundPoolPlayer sp;
    private int[][] sounds;
    private GifImageView[] pokemons;
    private final Random rand = new Random();
    private GifDrawable[] roarAnimation ;
    private GifDrawable[] idleAnimation ;

    private boolean player_finished = false,game_over = false;
    private static final int Bulbasaur =0, Charmander =1, Squirtle=2,Pikachu=3;
    private ArrayList<Integer> sequence = new ArrayList<>();
    private int seq_index = 0;
    int score = 0;

    TextView scoreView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sp = new SoundPoolPlayer(this);
        playMusic(R.raw.battle,0.1f);
        linkViews();
        setListeners();
        NextLevel();
    }
    private void playMusic(int song,float vol) {
        if( mp != null ){
            if(mp.isPlaying()){
                return;
            }
        }
        mp = MediaPlayer.create(this, song);
        mp.setLooping(true);
        mp.setVolume(vol,vol);
        mp.start();
    }

    private void NextLevel(){
        sequence.add(rand.nextInt(4));
        PlayAll();
    }
    private void PlayAll() {
        ToggleButtons(false);
        seq_index = 0;
        hand.postDelayed(
            new Runnable() {
            @Override
            public void run() {
                int pokemon = sequence.get(seq_index);
                playRandomSound(pokemon);
                roarAnimation[pokemon].reset();
                roarAnimation[pokemon].setLoopCount(1);
                pokemons[pokemon].setPressed(true);
                pokemons[pokemon].setImageDrawable(roarAnimation[pokemon]);
                if(seq_index < sequence.size()-1) {
                    seq_index++;
                    hand.postDelayed(this, roarAnimation[pokemon].getDuration());
                }else{
                    seq_index = 0;
                    ToggleButtons(true);
                }
            }
        }
        ,1000);
    }

    private void playRandomSound(int pokemon){
        sp.playShortResource(sounds[pokemon][new Random().nextInt(3)]);
    }

    private void updateScore(){
        scoreView.setText(String.format(Locale.getDefault(),"%d",score));
    }
    private void linkViews(){
        try {
            pokemons = new GifImageView[4];
            roarAnimation = new GifDrawable[4];
            idleAnimation = new GifDrawable[4];
            sounds = new int[4][4];

            sounds[Pikachu][0] = R.raw.pikachu_happy_1;
            sounds[Pikachu][1] = R.raw.pikachu_happy_2;
            sounds[Pikachu][2] = R.raw.pikachu_happy_3;
            pokemons[Pikachu] = (GifImageView) findViewById(R.id.pikachu);
            idleAnimation[Pikachu] = new GifDrawable( getResources(), R.drawable.pikachu_idle);
            roarAnimation[Pikachu] = new GifDrawable( getResources(), R.drawable.pikachu_roar);
            sounds[Bulbasaur][0] = R.raw.bulbasaur_happy_1;
            sounds[Bulbasaur][1] = R.raw.bulbasaur_happy_2;
            sounds[Bulbasaur][2] = R.raw.bulbasaur_happy_3;

            pokemons[Bulbasaur] = (GifImageView) findViewById(R.id.bulbasaur);
            idleAnimation[Bulbasaur] = new GifDrawable( getResources(),R.drawable.bulbasaur_idle);
            roarAnimation[Bulbasaur] = new GifDrawable( getResources(),R.drawable.bulbasaur_roar);

            sounds[Charmander][0]= R.raw.charmander_happy_1;
            sounds[Charmander][1]= R.raw.charmander_happy_2;
            sounds[Charmander][2]= R.raw.charmander_happy_3;
            pokemons[Charmander] = (GifImageView) findViewById(R.id.charmander);
            idleAnimation[Charmander] = new GifDrawable( getResources(),R.drawable.charmander_idle);
            roarAnimation[Charmander] = new GifDrawable( getResources(),R.drawable.charmander_roar);

            sounds[Squirtle][0]= R.raw.squirtle_happy_1;
            sounds[Squirtle][1]= R.raw.squirtle_happy_2;
            sounds[Squirtle][2]= R.raw.squirtle_happy_3;
            pokemons[Squirtle] = (GifImageView) findViewById(R.id.squirtle);
            idleAnimation[Squirtle] = new GifDrawable( getResources(),R.drawable.squirtle_idle);
            roarAnimation[Squirtle] = new GifDrawable( getResources(),R.drawable.squirtle_roar);
            scoreView = (TextView)findViewById(R.id.score_val);
        }catch (Exception e){
            ShowText(e.getMessage());
        }
    }

    private void setListeners(){
        for( int i =0;i<4;i++){
            final int pokemon = i;
            pokemons[pokemon].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int current = seq_index++;
                    if(current == sequence.size()-1){
                        ToggleButtons(false);
                        player_finished = true;
                    }
                    if (sequence.get(current) != pokemon) {
                        gameOver();
                    } else {
                        score++;
                        updateScore();
                    }
                    act(pokemon);
                    return false;
                }
            });

            roarAnimation[pokemon].addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    pokemons[pokemon].setPressed(false);
                    pokemons[pokemon].setImageDrawable(idleAnimation[pokemon]);
                    if( player_finished && !game_over ){
                        player_finished =false;
                        NextLevel();
                    }
                }
            });
        }
    }
    private void ToggleButtons(boolean state){
        for( View v: pokemons){
            v.setEnabled(state);
        }
    }
    private void gameOver(){
        game_over =true;
        ToggleButtons(false);
        ShowText("GAME OVER!");
    }

    private void ShowText(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

    private void act(final int pokemon){
        sp.playShortResource(sounds[pokemon][new Random().nextInt(3)]);
        roarAnimation[pokemon].reset();
        roarAnimation[pokemon].setLoopCount(1);
        pokemons[pokemon].setPressed(true);
        pokemons[pokemon].setImageDrawable(roarAnimation[pokemon]);

    }

    @Override
    protected void onResume() {
        super.onResume();
        playMusic(R.raw.battle,0.1f);
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        mp.release();
        super.onBackPressed();
    }
}



