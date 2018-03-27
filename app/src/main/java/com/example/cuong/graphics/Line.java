package com.example.cuong.graphics;

/**
 * Created by Cuong on 3/14/2018.
 */

public class Line {

    private int startX;
    private int startY;
    private int stopX;
    private int stopY;

    public Line(int startX, int startY, int stopX, int stopY)
    {
        this.startX=startX;
        this.startY=startY;
        this.stopX=stopX;
        this.stopY=stopY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStopX() {
        return stopX;
    }

    public void setStopX(int stopX) {
        this.stopX = stopX;
    }

    public int getStopY() {
        return stopY;
    }

    public void setStopY(int stopY) {
        this.stopY = stopY;
    }
}
