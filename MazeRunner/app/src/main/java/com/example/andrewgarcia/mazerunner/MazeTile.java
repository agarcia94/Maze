package com.example.andrewgarcia.mazerunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Alex Perez on 6/4/2016.
 */
public class MazeTile {
    private Bitmap bitmap;
    private int number;

    private int wall;
    private int start;
    private int end;

    private boolean isStartTile;
    private boolean isEndTile;
    private int x;
    private int y;


    public MazeTile(Bitmap bitmap, int number, int wall, int start){
        this.bitmap = bitmap;
        this.number = number;

        this.wall = wall;
        this.start = start;

        this.end = 0;

        isStartTile = false;
        isEndTile = false;

    }

    public MazeTile(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getEndCube(){ return end; }

    public void setEnd(int end){ this.end = end; }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean isStart(){
        return isStartTile;
    }

    public void setStartTile(boolean start){
        isStartTile = start;
    }

    public boolean isEnd(){
        return isEndTile;
    }

    public void setEndTile(boolean end){
        isEndTile = end;
    }

    public int getNumber() {
        return number;
    }

    public int getWall() {
        return wall;
    }

    public void setWall(int wall) {
        this.wall = wall;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStartCube(){
        return start;
    }


    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(bitmap, x * bitmap.getWidth(), y * bitmap.getHeight(), null);
    }

    public boolean isClicked(float clickX, float clickY, int tileX, int tileY) {
        int tileX0 = tileX * bitmap.getWidth();
        int tileX1 = (tileX + 1) * bitmap.getWidth();
        int tileY0 = tileY * bitmap.getWidth();
        int tileY1 = (tileY + 1) * bitmap.getWidth();
        return (clickX >= tileX0) && (clickX < tileX1) && (clickY >= tileY0) && (clickY < tileY1);
    }
}
