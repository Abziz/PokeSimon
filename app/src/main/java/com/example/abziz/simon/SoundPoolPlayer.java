package com.example.abziz.simon;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Abziz on 04/05/2017.
 */

public class SoundPoolPlayer {
    private SoundPool mShortPlayer= null;
    private HashMap mSounds = new HashMap();
    Integer i = 0;
    public SoundPoolPlayer(final Context pContext)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(12, AudioManager.STREAM_MUSIC, 0);
        mShortPlayer.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Toast.makeText(pContext,(++i).toString(),Toast.LENGTH_SHORT).show();
            }
        });
        mSounds.put(R.raw.pikachu_happy_1, this.mShortPlayer.load(pContext, R.raw.pikachu_happy_1, 1));
        mSounds.put(R.raw.pikachu_happy_2, this.mShortPlayer.load(pContext, R.raw.pikachu_happy_2, 1));
        mSounds.put(R.raw.pikachu_happy_3, this.mShortPlayer.load(pContext, R.raw.pikachu_happy_3, 1));

        mSounds.put(R.raw.bulbasaur_happy_1, this.mShortPlayer.load(pContext, R.raw.bulbasaur_happy_1, 1));
        mSounds.put(R.raw.bulbasaur_happy_2, this.mShortPlayer.load(pContext, R.raw.bulbasaur_happy_2, 1));
        mSounds.put(R.raw.bulbasaur_happy_3, this.mShortPlayer.load(pContext, R.raw.bulbasaur_happy_3, 1));

        mSounds.put(R.raw.charmander_happy_1, this.mShortPlayer.load(pContext, R.raw.charmander_happy_1, 1));
        mSounds.put(R.raw.charmander_happy_2, this.mShortPlayer.load(pContext, R.raw.charmander_happy_2, 1));
        mSounds.put(R.raw.charmander_happy_3, this.mShortPlayer.load(pContext, R.raw.charmander_happy_3, 1));

        mSounds.put(R.raw.squirtle_happy_1, this.mShortPlayer.load(pContext, R.raw.squirtle_happy_1, 1));
        mSounds.put(R.raw.squirtle_happy_2, this.mShortPlayer.load(pContext, R.raw.squirtle_happy_2, 1));
        mSounds.put(R.raw.squirtle_happy_3, this.mShortPlayer.load(pContext, R.raw.squirtle_happy_3, 1));
       // mSounds.put(R.raw.squirtle_sad, this.mShortPlayer.load(pContext, R.raw.squirtle_sad, 1));
    }

    public void playShortResource(int piResource) {
        int iSoundId = (Integer) mSounds.get(piResource);
        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1.1f);
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }
}
