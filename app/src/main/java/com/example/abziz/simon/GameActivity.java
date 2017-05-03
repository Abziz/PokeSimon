package com.example.abziz.simon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GameActivity extends AppCompatActivity {
    final Handler hand = new Handler();
    GifImageView[] pokemons;
    final Random rand = new Random();
    GifDrawable[] roarAnimation ;
    GifDrawable[] idleAnimation ;
    int[] roarSounds;
    boolean AnimationRunning = false;
    static final int Bulbasaur =0, Charmander =1, Squirtle=2,Pikachu=3;
    ArrayList<Integer> sequence = new ArrayList();
    int current = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AssignViews();
        setListeners();
    }

    private void NextLevel(){
        int r = rand.nextInt(4);
        ShowText((new Integer(r)).toString());
        sequence.add(r);

    }
    private void PlayAll() {
        for( GifImageView v : pokemons){
            v.setEnabled(false);
        }
        current  = 0;
        hand.postDelayed(
            new Runnable() {
            @Override
            public void run() {
                    int pokemon = sequence.get(current);

                    if( current != 0){
                        int last = sequence.get(current-1);
                        pokemons[last].setPressed(false);
                        pokemons[last].setImageDrawable(idleAnimation[last]);
                    }
                    pokemons[pokemon].setImageDrawable(roarAnimation[pokemon]);
                    pokemons[pokemon].setPressed(true);
                    if(current < sequence.size()-1) {
                        current++;
                        hand.postDelayed(this, roarAnimation[pokemon].getDuration());
                    }else{
                        int last = sequence.get(sequence.size()-1);
                        pokemons[last].setPressed(false);
                        pokemons[last].setImageDrawable(idleAnimation[last]);
                        for( GifImageView v : pokemons){
                            v.setEnabled(true);
                        }
                    }
            }
        }
        ,roarAnimation[sequence.get(0)].getDuration());


    }

    private void AssignViews(){
        try {
            pokemons = new GifImageView[4];
            roarAnimation = new GifDrawable[4];
            idleAnimation = new GifDrawable[4];
            roarSounds = new int[4];

            pokemons[Pikachu] = (GifImageView) findViewById(R.id.pikachu);
            idleAnimation[Pikachu] = new GifDrawable( getResources(), R.drawable.pikachu_idle);
            roarAnimation[Pikachu] = new GifDrawable( getResources(), R.drawable.pikachu_roar);;
            roarAnimation[Pikachu].addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    pokemons[Pikachu].setPressed(false);
                    pokemons[Pikachu].setImageDrawable(idleAnimation[Pikachu]);
                }
            });

            pokemons[Bulbasaur] = (GifImageView) findViewById(R.id.bulbasaur);
            idleAnimation[Bulbasaur] = new GifDrawable( getResources(),R.drawable.bulbasaur_idle);
            roarAnimation[Bulbasaur] = new GifDrawable( getResources(),R.drawable.bulbasaur_roar);
            roarAnimation[Bulbasaur].addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    pokemons[Bulbasaur].setPressed(false);
                    pokemons[Bulbasaur].setImageDrawable(idleAnimation[Bulbasaur]);
                }
            });


            pokemons[Charmander] = (GifImageView) findViewById(R.id.charmander);
            idleAnimation[Charmander] = new GifDrawable( getResources(),R.drawable.charmander_idle);
            roarAnimation[Charmander] = new GifDrawable( getResources(),R.drawable.charmander_roar);
            roarAnimation[Charmander].addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    pokemons[Charmander].setPressed(false);
                    pokemons[Charmander].setImageDrawable(idleAnimation[Charmander]);
                }
            });

            pokemons[Squirtle] = (GifImageView) findViewById(R.id.squirtle);
            idleAnimation[Squirtle] = new GifDrawable( getResources(),R.drawable.squirtle_idle);
            roarAnimation[Squirtle] = new GifDrawable( getResources(),R.drawable.squirtle_roar);
            roarAnimation[Squirtle].addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    pokemons[Squirtle].setPressed(false);
                    pokemons[Squirtle].setImageDrawable(idleAnimation[Squirtle]);
                }
            });



        }catch (Exception e){
            ShowText(e.getMessage());
        }
    }

    private void setListeners(){
        pokemons[Pikachu].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                act(Pikachu);
                return false;
            }
        });

//        pokemons[Pikachu].setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                act(pokemons[Pikachu],R.drawable.pikachu_idle,R.drawable.pikachu_move,null);
//            }
//        });

        pokemons[Bulbasaur].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                act(Bulbasaur);
                return false;
            }
        });

        pokemons[Charmander].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                act(Charmander);
                return false;
            }
        });

        pokemons[Squirtle].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                act(Squirtle);
                return false;
            }
        });
    }

    private void ShowText(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    private void act(final int pokemon){
        roarAnimation[pokemon].reset();
        roarAnimation[pokemon].setLoopCount(1);
        pokemons[pokemon].setPressed(true);
        pokemons[pokemon].setImageDrawable(roarAnimation[pokemon]);
    }
}



