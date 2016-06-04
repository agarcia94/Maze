package com.example.andrewgarcia.mazerunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;

/**
 * Created by Alex Perez on 6/4/2016.
 */
public class MazeBoard {
    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<MazeTile> tiles;

    MazeBoard(Bitmap bitmap, int parentWidth) {
        //Bitmap.createBitmap(bitmap, 0, 0, parentWidth, parentWidth);
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, parentWidth, parentWidth, false);
        int size =parentWidth/NUM_TILES;
        int count = 0;
        tiles = new ArrayList<MazeTile>();

        for(int i = 0; i < NUM_TILES;i++){

            for( int j = 0; j < NUM_TILES; j++){

                if(count == 8){
                    tiles.add((null));
                }
                else{
                    tiles.add(new MazeTile(Bitmap.createBitmap(bm, j*size, i*size, size, size),count));
                    //System.out.println("X:" + xCoord + "Y:" + yCoord);
                }
                count++;
            }
        }

        //Using cartisian plane
        //for loop to iterate through the arraylist
        //Add tiles to the array to break apart tiles.add.createBitMap(photo,x-coord, y-coord,width,height)
        //y-coord += parentHeight;
    }

    MazeBoard(MazeBoard otherBoard) {
        tiles = (ArrayList<MazeTile>) otherBoard.tiles.clone();
    }

    public void reset() {
        // Nothing for now but you may have things to reset once you implement the solver.
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((MazeBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            MazeTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            MazeTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            MazeTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        MazeTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public ArrayList<MazeBoard> neighbours() {
        ArrayList<MazeBoard> boards = new ArrayList<>();
        MazeBoard copyBoard = null;
        int x = 0;
        int y = 0;
        //MazeTile empty = null;
        //int numberOfTile = 0;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) == null) {
                x = i % NUM_TILES;
                y = i / NUM_TILES;
                break;
            }
        }

        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = x + delta[0];
            int nullY = y + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES) {
                copyBoard = new MazeBoard(this);
                copyBoard.swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(x, y));
                boards.add(copyBoard);
            }
        }
        return boards;
    }
    public int priority() {

        return 0;
    }
}
