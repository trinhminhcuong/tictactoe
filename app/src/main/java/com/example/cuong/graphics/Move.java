package com.example.cuong.graphics;

/**
 * Created by Cuong on 3/21/2018.
 */

public class Move {

    private int colIndex;
    private int rowIndex;

    public Move(int colIndex, int rowIndex) {
        this.colIndex = colIndex;
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
}
