package com.example.abziz.simon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GameActivity extends AppCompatActivity {
    final Handler hand = new Handler();
    GifImageView[] pokemons;
    GifDrawable[] animation_info;
    final Random rand = new Random();
    int[] roarAnimation ;
    int[] idleAnimation ;
    int[] roarSounds;
    static final int Bulbasaur =0, Charmander =1, Squirtle=2,Pikachu=3;
    ArrayList<Integer> sequence = new ArrayList();
    int current = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        AssignViews();
        setListeners();
        for(int i =0;i<5;i++){
            NextLevel();
        }

        try {
            PlayAll();
        }catch(Exception e){
            ShowText(e.getMessage());
        }
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
                        pokemons[last].setImageResource(idleAnimation[last]);
                    }
                    pokemons[pokemon].setImageResource(roarAnimation[pokemon]);
                    pokemons[pokemon].setPressed(true);
                    if(current < sequence.size()-1) {
                        current++;
                        hand.postDelayed(this, animation_info[pokemon].getDuration());
                    }else{
                        int last = sequence.get(sequence.size()-1);
                        pokemons[last].setPressed(false);
                        pokemons[last].setImageResource(idleAnimation[last]);
                        for( GifImageView v : pokemons){
                            v.setEnabled(true);
                        }
                    }
            }
        }
        ,animation_info[sequence.get(0)].getDuration());


    }

    private void AssignViews(){
        try {
            pokemons = new GifImageView[4];
            roarAnimation = new int[4];
            idleAnimation = new int[4];
            roarSounds = new int[4];
            animation_info = new GifDrawable[4];

            pokemons[Pikachu] = (GifImageView) findViewById(R.id.pikachu);
            idleAnimation[Pikachu] = R.drawable.pikachu_idle;
            roarAnimation[Pikachu] = R.drawable.pikachu_roar;
            animation_info[Pikachu] = new GifDrawable( getResources(), roarAnimation[Pikachu]);

            pokemons[Bulbasaur] = (GifImageView) findViewById(R.id.bulbasaur);
            idleAnimation[Bulbasaur] = R.drawable.bulbasaur_idle;
            roarAnimation[Bulbasaur] = R.drawable.bulbasaur_roar;
            animation_info[Bulbasaur] = new GifDrawable( getResources(), roarAnimation[Bulbasaur]);

            pokemons[Charmander] = (GifImageView) findViewById(R.id.charmander);
            idleAnimation[Charmander] = R.drawable.charmander_idle;
            roarAnimation[Charmander] = R.drawable.charmander_roar;
            animation_info[Charmander] = new GifDrawable( getResources(), roarAnimation[Charmander]);

            pokemons[Squirtle] = (GifImageView) findViewById(R.id.squirtle);
            idleAnimation[Squirtle] = R.drawable.squirtle_idle;
            roarAnimation[Squirtle] = R.drawable.squirtle_roar;
            animation_info[Squirtle] = new GifDrawable( getResources(), roarAnimation[Squirtle]);


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
        pokemons[pokemon].setImageResource(roarAnimation[pokemon]);
        pokemons[pokemon].setPressed(true);
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                pokemons[pokemon].setPressed(false);
                pokemons[pokemon].setImageResource(idleAnimation[pokemon]);
            }
        },animation_info[pokemon].getDuration());
    }
}



