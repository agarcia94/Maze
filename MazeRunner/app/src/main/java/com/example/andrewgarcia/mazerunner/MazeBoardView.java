package com.example.andrewgarcia.mazerunner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alex Perez on 6/4/2016.
 */
public class MazeBoardView extends View {
    public static final int NUM_SHUFFLE_STEPS = 40;
    private Activity activity;
    private MazeBoard mazeBoard;
    private ArrayList<MazeBoard> animation;
    private Random random = new Random();

    public MazeBoardView(Context context) {
        super(context);
        activity = (Activity) context;
        animation = null;
    }

    public void initialize(Bitmap imageBitmap) {
        int width = getWidth();
        mazeBoard = new MazeBoard(imageBitmap, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mazeBoard != null) {
            if (animation != null && animation.size() > 0) {
                mazeBoard = animation.remove(0);
                mazeBoard.draw(canvas);
                if (animation.size() == 0) {
                    animation = null;
                    mazeBoard.reset();
                    Toast toast = Toast.makeText(activity, "Solved! ", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                mazeBoard.draw(canvas);
            }
        }
    }

    public void shuffle() {
        if (animation == null && mazeBoard != null) {

            MazeBoard temp = mazeBoard;
            ArrayList<MazeBoard> puzzles = mazeBoard.neighbours(); //null

            for(int i = 0; i < NUM_SHUFFLE_STEPS; i++){

                puzzles = temp.neighbours();
                temp = puzzles.get(random.nextInt(puzzles.size()));

            }
            mazeBoard = temp;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //if (animation == null && mazeBoard != null) {
        //    switch(event.getAction()) {
        //        case MotionEvent.ACTION_DOWN:
        //            if (mazeBoard.click(event.getX(), event.getY())) {


        //               Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());

        //               invalidate();
        //               if (mazeBoard.resolved()) {
        //                   Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
        //                   toast.show();
        //               }
        //               return true;
        //           }
        //   }
        //}
        return super.onTouchEvent(event);
    }

    public void solve() {
    }




    public void moveUp(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(1)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());
            invalidate();
        }

    }




    public void moveDown(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(3)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());
            invalidate();
        }

    }


    public void moveLeft(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(2)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());
            invalidate();
        }

    }


    public void moveRight(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(4)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());
            invalidate();
        }

    }



}
