package com.example.andrewgarcia.mazerunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex Perez on 6/4/2016.
 */
public class MazeBoard {
    private static final int NUM_TILES = 12;
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

                //if(count == 8){
                //    tiles.add((null));
                //}
                //else{                                                                             // wall,  start
                tiles.add(new MazeTile(Bitmap.createBitmap(bm, j*size, i*size, size, size),count, 1, 0));
                //System.out.println("X:" + xCoord + "Y:" + yCoord);
                //}
                count++;
            }
        }

        tiles.get(3).setStart(1);

        tiles.get(3).setWall(0);
        tiles.get(15).setWall(0);
        tiles.get(27).setWall(0);
        tiles.get(39).setWall(0);
        tiles.get(51).setWall(0);
        tiles.get(63).setWall(0);
        tiles.get(64).setWall(0);
        tiles.get(65).setWall(0);
        tiles.get(66).setWall(0);
        tiles.get(67).setWall(0);
        tiles.get(68).setWall(0);


        tiles.get(26).setWall(0);
        tiles.get(25).setWall(0);
        tiles.get(37).setWall(0);
        tiles.get(49).setWall(0);
        tiles.get(61).setWall(0);
        tiles.get(73).setWall(0);
        tiles.get(85).setWall(0);

        tiles.get(97).setWall(0);
        tiles.get(98).setWall(0);
        tiles.get(99).setWall(0);
        tiles.get(100).setWall(0);
        tiles.get(101).setWall(0);
        tiles.get(102).setWall(0);
        tiles.get(103).setWall(0);


        tiles.get(28).setWall(0);
        tiles.get(29).setWall(0);
        tiles.get(30).setWall(0);
        tiles.get(31).setWall(0);
        tiles.get(32).setWall(0);
        tiles.get(20).setWall(0);
        tiles.get(21).setWall(0);
        tiles.get(22).setWall(0);

        tiles.get(34).setWall(0);
        tiles.get(46).setWall(0);
        tiles.get(58).setWall(0);
        tiles.get(70).setWall(0);
        tiles.get(82).setWall(0);
        tiles.get(94).setWall(0);
        tiles.get(93).setWall(0);
        tiles.get(92).setWall(0);


        tiles.get(104).setWall(0);
        tiles.get(116).setWall(0);
        tiles.get(128).setWall(0);
        tiles.get(127).setWall(0);
        tiles.get(126).setWall(0);
        tiles.get(125).setWall(0);
        tiles.get(124).setWall(0);
        tiles.get(123).setWall(0);
        tiles.get(135).setWall(0);


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
                    Log.d("Tag Name", "Try Moving-  i% Num_tiles: " + i%NUM_TILES + "    i/Num_tiles: " + i/NUM_TILES);
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    public boolean buttonClick(int direction){
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            MazeTile tile = tiles.get(i);
            //if (tile == null) {
            if (tile.getStartCube() == 1) {

                //if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                Log.d("Tag Name", "Try Moving-  i% Num_tiles: " + i%NUM_TILES + "    i/Num_tiles: " + i/NUM_TILES);

                // 1-Up
                if (direction==1) {
                    if (((i / NUM_TILES)-1)>=0) {

                        if (tiles.get(XYtoIndex(  i % NUM_TILES, (i / NUM_TILES)-1)).getWall()==0) {

                            return tryMoving(i % NUM_TILES, (i / NUM_TILES) - 1);

                        }


                    }

                }


                // 2-Left
                if (direction==2) {
                    if (((i % NUM_TILES)-1)>=0) {

                        if (tiles.get(XYtoIndex(  (i % NUM_TILES)-1, (i / NUM_TILES))).getWall()==0) {

                            return tryMoving((i % NUM_TILES) - 1, (i / NUM_TILES));

                        }

                    }

                }


                // 3-down
                if (direction==3) {
                    if (((i / NUM_TILES)+1)<NUM_TILES) {

                        if (tiles.get(XYtoIndex(   i % NUM_TILES, (i / NUM_TILES)+1)).getWall()==0) {

                            return tryMoving(i % NUM_TILES, (i / NUM_TILES) + 1);

                        }

                    }

                }


                // 4-right
                if (direction==4) {
                    if (((i % NUM_TILES)+1)<NUM_TILES) {
                        if (tiles.get(XYtoIndex(   (i % NUM_TILES)+1, (i / NUM_TILES))).getWall()==0) {
                            return tryMoving((i % NUM_TILES)+1, (i / NUM_TILES));
                        }


                    }

                }



                //return tryMoving(i % NUM_TILES, (i / NUM_TILES)+1);
            }
        }

        return false;
    }




    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            MazeTile tile = tiles.get(i);

            //if (tile == null || tile.getNumber() != i)

            if (tile.getStartCube()==1||tile.getNumber()!=1)
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

            Log.d("Tag Name", "    nullX: " + nullX + "    nullY: " + nullY + "   XYtoIndex: " + XYtoIndex(nullX, nullY));

            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)).getWall()==0 &&
                    //tiles.get(XYtoIndex(nullX, nullY)) == null) {
                    tiles.get(XYtoIndex(nullX, nullY)).getStartCube() == 1) {

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

            //if (tiles.get(i) == null) {
            if (tiles.get(i).getStartCube() == 1) {
                x = i % NUM_TILES;
                y = i / NUM_TILES;
                break;
                //}
            }
        }

        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = x + delta[0];
            int nullY = y + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)).getWall()==0) {
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
