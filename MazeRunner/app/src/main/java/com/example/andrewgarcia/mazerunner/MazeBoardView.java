package com.example.andrewgarcia.mazerunner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
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
    private HashMap<MazeBoard, Double> visitedBoards = Maps.newHashMap();
    private PriorityQueue<MazeBoard> boards = new PriorityQueue<>();

    int destX = 3; //destination x
    int destY = 11; //destination y

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
                    Toast toast = Toast.makeText(activity, "Computer Best Route", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    this.postInvalidateDelayed(500);
                }
            } else {
                mazeBoard.draw(canvas);
            }
        }
    }

    public MazeBoard getBoard(){ return mazeBoard; }


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



//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (animation == null && mazeBoard != null) {
//            switch(event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    if (mazeBoard.click(event.getX(), event.getY())) {
//
//
//                       Log.d("Touch event", "Event.getX: " + event.getX() + "    Event.getY: " + event.getY());
//
//                       invalidate();
//
////                        if(event.getX() == destX && event.getY() == destY){
////                            Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
////                            toast.show();
////                        }
//                       if (mazeBoard.resolved()) {
//                           Toast toast = Toast.makeText(activity, "Congratulations!", Toast.LENGTH_LONG);
//                           toast.show();
//                       }
//                       return true;
//                   }
//           }
//        }
//        return super.onTouchEvent(event);
//    }

    public void solve() {
        mazeBoard.setHValue(destX, destY); //set the heuristic value for the entry board
        boards.add(mazeBoard);

        MazeBoard current = null;

        while(!boards.isEmpty()){
            current = boards.poll();

            visitedBoards.put(current, current.getFValue());   //mark this current board as visited

            ArrayList<MazeTile> currentTiles = current.getTiles();
            //MazeTile targetTile = null;

            /* This will be crucial to seeing if we've arrived at our destination */
            for(int i =0; i < currentTiles.size(); i++){
                if(currentTiles.get(i).getStartCube() == 1){  //if the tile encountered is the null tile
                    int tileX = i % mazeBoard.getNUM_TILES();
                    int tileY = i / mazeBoard.getNUM_TILES();

                    if(tileX == destX && tileY == destY){ //if we've arrived at our destination
                        animation = Lists.newArrayList();
                        while(current != null){ //retrace the steps to create the path
                            animation.add(current);
                            current = current.getPreviousBoard();
                        }

                        Collections.reverse(animation);
                        return;  //exit the method
                    }

                }
            }

            /* What if we're not at our destination yet?
            *  Perform A* on the neighbors
            */
            ArrayList<MazeBoard> currentNeighbors = current.neighbours();
            for(MazeBoard neighbor : currentNeighbors){
                calculateNeighbor(current, neighbor);
            }

        }
    }

    /* Helper method to perform A* on the neighbors */
    private void calculateNeighbor(MazeBoard current, MazeBoard neighbor){
        MazeBoard prior = null;  //temp variable if parent in hashmap needs to be removed

        neighbor.setHValue(destX,destY); //set the heuristic value for the neighbor

        boolean isNeighborVisited = false;

        //Determine if the neighbor is in the closed list
        for(Map.Entry<MazeBoard, Double> entry : visitedBoards.entrySet()){
            if(entry.getKey().equals(neighbor)){
                isNeighborVisited = true;
                prior = entry.getKey();
                break;
            }
        }

        if(isNeighborVisited){
            if(neighbor.getFValue() < prior.getFValue()){
                visitedBoards.remove(prior);
                neighbor.setPreviousBoard(current);
                boards.add(neighbor);
            }
        }else{
            if(!boards.contains(neighbor))
                boards.add(neighbor);
        }
    }




    public void moveUp(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(1)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());

//            ArrayList<MazeTile> tiles = mazeBoard.getTiles();
//
//            for(int i =0; i < tiles.size(); i++){
//                if(tiles.get(i).getStartCube() == 1){
//                    int tileX = i % mazeBoard.getNUM_TILES();
//                    int tileY = i / mazeBoard.getNUM_TILES();
//
//
//
//                    if(tileX == destX && tileY == destY){
//                        Toast toast = Toast.makeText(activity, "Solved!", Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                }
//            }
            invalidate();
        }

    }




    public void moveDown(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(3)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());
//            ArrayList<MazeTile> tiles = mazeBoard.getTiles();
//
//            for(int i =0; i < tiles.size(); i++){
//                if(tiles.get(i).getStartCube() == 1){
//                    int tileX = i % mazeBoard.getNUM_TILES();
//                    int tileY = i / mazeBoard.getNUM_TILES();
//
//                    if(tileX == destX && tileY == destY){
//                        Toast toast = Toast.makeText(activity, "Solved!", Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                }
//            }
            invalidate();
        }

    }


    public void moveLeft(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(2)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());

//            ArrayList<MazeTile> tiles = mazeBoard.getTiles();
//
//            for(int i =0; i < tiles.size(); i++){
//                if(tiles.get(i).getStartCube() == 1){
//                    int tileX = i % mazeBoard.getNUM_TILES();
//                    int tileY = i / mazeBoard.getNUM_TILES();
//
//                    if(tileX == destX && tileY == destY){
//                        Toast toast = Toast.makeText(activity, "Solved!", Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                }
//            }
            invalidate();
        }

    }


    public void moveRight(){
        // 1-up, 2-left, 3-down, 4-right
        if (mazeBoard.buttonClick(4)) {
            //Log.d("Tag Name", "Event.getX: "+event.getX()+ "    Event.getY: "+event.getY());

//            ArrayList<MazeTile> tiles = mazeBoard.getTiles();
//
//            for(int i =0; i < tiles.size(); i++){
//                if(tiles.get(i).getStartCube() == 1){
//                    int tileX = i % mazeBoard.getNUM_TILES();
//                    int tileY = i / mazeBoard.getNUM_TILES();
//
//                    if(tileX == destX && tileY == destY){
//                        Toast toast = Toast.makeText(activity, "Solved!", Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                }
//            }
            invalidate();
        }

    }



}
