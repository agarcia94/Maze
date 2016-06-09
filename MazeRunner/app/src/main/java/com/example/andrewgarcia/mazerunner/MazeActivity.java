package com.example.andrewgarcia.mazerunner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MazeActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap = null;
    private MazeBoardView boardView;
    private ImageView photo;
    int row, col;
    int global_Timer = 0;
    // Button and Clock View
    Button startButton, resetButton, solveButton;
    TextView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        //MUSIC HANDLER
        //----------------------------------------------------------------------
        final MediaPlayer menu = MediaPlayer.create(this,R.raw.main_menu_music);
        final MediaPlayer gameplay = MediaPlayer.create(this,R.raw.gameplay);
        menu.start();
        //----------------------------------------------------------------------

        startButton = (Button) findViewById(R.id.startButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        solveButton = (Button) findViewById(R.id.solve_button);

        clockView = (TextView) findViewById(R.id.clockView);

//        while(gameplay.isPlaying()){
//            int mCurrentPosition = gameplay.getCurrentPosition() / 1000;
//            if(mCurrentPosition == 10){
//                gameplay.stop();
//            }
//        }

        final clockClass timer = new clockClass(60000, 1000);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                if(menu.isPlaying()){
                    menu.stop();
                    gameplay.start();
                }
                startButton.setEnabled(false);

                Bitmap testMaze = BitmapFactory.decodeResource(getResources(),R.drawable.testmaze);

                boardView.initialize(testMaze);
                boardView.invalidate();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //timer.cancel();
                if(gameplay.isPlaying()){
                    gameplay.stop();
                }
                gameplay.start();
                timer.start();
                //startButton.setEnabled(true);
            }
        });

       RelativeLayout container = (RelativeLayout) findViewById(R.id.maze_container);
       boardView = new MazeBoardView(this);

       // Some setup of the view.
       boardView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
       container.addView(boardView);
    }

    class clockClass extends CountDownTimer {
        final MediaPlayer running_out_of_time = MediaPlayer.create(getApplicationContext(), R.raw.running_out_of_time);
        final MediaPlayer out_of_time = MediaPlayer.create(getApplicationContext(), R.raw.pacman_death_sound);
        

        public clockClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            System.out.println("Milis: " + millis);

            if(millis == 0){
                if(running_out_of_time.isPlaying()){
                    running_out_of_time.stop();
                }
            }
            else if (millis < 16555){
                running_out_of_time.start();
            }

            clockView.setText(hms);
        }

        @Override
        public void onFinish() {
            out_of_time.start();
            clockView.setText("Times Up.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
    //        Bundle extras = data.getExtras();
    //        Bitmap imageBitmap = (Bitmap) extras.get("data");
    //        boardView.initialize(imageBitmap);
     //       boardView.invalidate();
//
    //    }
    //}

    //public void dispatchTakePictureIntent(View view) {
    //    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
    //        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    //    }
    //}

    public void shuffleImage(View view) {
        boardView.shuffle();
        boardView.invalidate();
    }

    public void solve(View view) {
        boardView.solve();

    }
}