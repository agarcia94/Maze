package com.example.andrewgarcia.mazerunner;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Alex on 6/8/2016.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        final MediaPlayer intro = MediaPlayer.create(this,R.raw.intro_splash);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //Audio Manager to set Boot Music at Maximum
                //-----------------------------------------------------------------------------------------------------------------------
                final AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                //final int originalVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
                //-----------------------------------------------------------------------------------------------------------------------

                intro.setAudioStreamType(AudioManager.STREAM_MUSIC);
                //intro.setVolume(1.0f, 1.0f);
                intro.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                finish();
                intro.stop();
                Intent i = new Intent(getBaseContext(),MazeActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
