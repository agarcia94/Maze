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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MazeActivity extends AppCompatActivity{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap = null;
    private MazeBoardView boardView;
    private ImageView photo;
    int row, col;

    double max_Time = 60000;
    double cur_Time = 0;

    int destX = 3;
    int destY = 11;


    // Button and Clock View
    Button startButton, resetButton, solveButton,
            leftButton, rightButton, upButton, downButton;
    TextView clockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //Keeps the Screen from going to sleep while in game


        //MUSIC HANDLER
        //----------------------------------------------------------------------
        final MediaPlayer menu = MediaPlayer.create(this,R.raw.main_menu_music);
        final MediaPlayer gameplay = MediaPlayer.create(this,R.raw.gameplay);
        final MediaPlayer gameplay2 = MediaPlayer.create(this,R.raw.gameplay2);
        menu.start();
        //----------------------------------------------------------------------

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        startButton = (Button) findViewById(R.id.startButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        solveButton = (Button) findViewById(R.id.solveButton);

        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);
        upButton = (Button) findViewById(R.id.upButton);
        downButton = (Button) findViewById(R.id.downButton);

        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        solveButton.setEnabled(false);
        resetButton.setEnabled(false);

        clockView = (TextView) findViewById(R.id.clockView);

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
                resetButton.setEnabled(false);

                Bitmap testMaze = BitmapFactory.decodeResource(getResources(),R.drawable.mazebg2);
                boardView.initialize(testMaze);
                boardView.invalidate();

                leftButton.setEnabled(true);
                rightButton.setEnabled(true);
                upButton.setEnabled(true);
                downButton.setEnabled(true);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //timer.cancel();
                if(gameplay.isPlaying()){
                    gameplay.stop();
                    gameplay.reset();
                }else if(gameplay2.isPlaying()){
                    gameplay2.stop();
                }
                gameplay2.start();
                timer.start();
                
                Bitmap testMaze = BitmapFactory.decodeResource(getResources(),R.drawable.mazebg2);
                boardView.initialize(testMaze);
                boardView.invalidate();

                leftButton.setEnabled(true);
                rightButton.setEnabled(true);
                upButton.setEnabled(true);
                downButton.setEnabled(true);

                solveButton.setEnabled(false);
            }
        });

        solveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bitmap testMaze = BitmapFactory.decodeResource(getResources(),R.drawable.mazebg2);
                boardView.initialize(testMaze);
                boardView.invalidate();
                solve(v);
            }
        });

       RelativeLayout container = (RelativeLayout) findViewById(R.id.maze_container);
       boardView = new MazeBoardView(this);

       // Some setup of the view.
       boardView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
       container.addView(boardView);

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boardView.moveDown();

                ArrayList<MazeTile> tiles = boardView.getBoard().getTiles();

                for(int i =0; i < tiles.size(); i++){
                    if(tiles.get(i).getStartCube() == 1){
                        int tileX = i % boardView.getBoard().getNUM_TILES();
                        int tileY = i / boardView.getBoard().getNUM_TILES();

                        if(tileX == destX && tileY == destY){
                            double seconds = ((max_Time-cur_Time)/ 1000) % 60 ;
                            DecimalFormat df = new DecimalFormat("#.##");
                            seconds = Double.valueOf(df.format(seconds));

                            Toast toast = Toast.makeText(getApplicationContext(), "Finished in " + (seconds) + " Seconds", Toast.LENGTH_LONG);
                            toast.show();

                            timer.cancel();

                            if(gameplay.isPlaying()){
                                gameplay.stop();
                                //gameplay.reset();
                            }
                            else if(gameplay2.isPlaying()){
                                gameplay2.stop();
                            }

                            leftButton.setEnabled(false);
                            rightButton.setEnabled(false);
                            upButton.setEnabled(false);
                            downButton.setEnabled(false);
                            resetButton.setEnabled(true);
                            solveButton.setEnabled(true);

                            break;
                        }
                    }
                }

            }
        });


        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boardView.moveUp();

                ArrayList<MazeTile> tiles = boardView.getBoard().getTiles();

                for(int i =0; i < tiles.size(); i++){
                    if(tiles.get(i).getStartCube() == 1){
                        int tileX = i % boardView.getBoard().getNUM_TILES();
                        int tileY = i / boardView.getBoard().getNUM_TILES();

                        if(tileX == destX && tileY == destY){
                            double seconds = ((max_Time-cur_Time)/ 1000) % 60 ;
                            DecimalFormat df = new DecimalFormat("#.##");
                            seconds = Double.valueOf(df.format(seconds));

                            Toast toast = Toast.makeText(getApplicationContext(), "Finished in " + (seconds) + " Seconds", Toast.LENGTH_LONG);
                            toast.show();

                            if(gameplay.isPlaying()){
                                gameplay.stop();
                                gameplay.reset();
                            }

                            leftButton.setEnabled(false);
                            rightButton.setEnabled(false);
                            upButton.setEnabled(false);
                            downButton.setEnabled(false);
                            resetButton.setEnabled(true);
                            solveButton.setEnabled(true);

                            break;
                        }
                    }
                }



            }
        });


        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boardView.moveLeft();

                ArrayList<MazeTile> tiles = boardView.getBoard().getTiles();

                for(int i =0; i < tiles.size(); i++){
                    if(tiles.get(i).getStartCube() == 1){
                        int tileX = i % boardView.getBoard().getNUM_TILES();
                        int tileY = i / boardView.getBoard().getNUM_TILES();

                        if(tileX == destX && tileY == destY){
                            double seconds = ((max_Time-cur_Time)/ 1000) % 60 ;
                            DecimalFormat df = new DecimalFormat("#.##");
                            seconds = Double.valueOf(df.format(seconds));

                            Toast toast = Toast.makeText(getApplicationContext(), "Finished in " + (seconds) + " Seconds", Toast.LENGTH_LONG);
                            toast.show();

                            timer.cancel();

                            if(gameplay.isPlaying()){
                                gameplay.stop();
                                gameplay.reset();
                            }

                            leftButton.setEnabled(false);
                            rightButton.setEnabled(false);
                            upButton.setEnabled(false);
                            downButton.setEnabled(false);
                            resetButton.setEnabled(true);
                            solveButton.setEnabled(true);

                            break;
                        }
                    }
                }

            }
        });


        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boardView.moveRight();

                ArrayList<MazeTile> tiles = boardView.getBoard().getTiles();

                for(int i =0; i < tiles.size(); i++){
                    if(tiles.get(i).getStartCube() == 1){
                        int tileX = i % boardView.getBoard().getNUM_TILES();
                        int tileY = i / boardView.getBoard().getNUM_TILES();

                        if(tileX == destX && tileY == destY){
                            double seconds = ((max_Time-cur_Time)/ 1000) % 60 ;
                            DecimalFormat df = new DecimalFormat("#.##");
                            seconds = Double.valueOf(df.format(seconds));

                            Toast toast = Toast.makeText(getApplicationContext(), "Finished in " + (seconds) + " Seconds", Toast.LENGTH_LONG);
                            toast.show();
                            timer.cancel();

                            if(gameplay.isPlaying()){
                                gameplay.stop();
                                gameplay.reset();
                            }

                            leftButton.setEnabled(false);
                            rightButton.setEnabled(false);
                            upButton.setEnabled(false);
                            downButton.setEnabled(false);
                            resetButton.setEnabled(true);
                            solveButton.setEnabled(true);

                            break;
                        }
                    }
                }

            }
        });
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
            cur_Time = millis;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            clockView.setText(hms);

            if(millis == 0){
                if(running_out_of_time.isPlaying()){
                    running_out_of_time.stop();
                }
            }
            else if (millis < 16555){
                running_out_of_time.start();
            }
        }

        @Override
        public void onFinish() {
            out_of_time.start();
            //game_over.start();
            clockView.setText("Times Up.");
            leftButton.setEnabled(false);
            rightButton.setEnabled(false);
            upButton.setEnabled(false);
            downButton.setEnabled(false);

            solveButton.setEnabled(true);
            resetButton.setEnabled(true);
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