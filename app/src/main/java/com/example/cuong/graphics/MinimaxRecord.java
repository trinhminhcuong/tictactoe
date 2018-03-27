package com.example.cuong.graphics;

/**
 * Created by Cuong on 3/21/2018.
 */

public class MinimaxRecord {
    private Move move;
    private int score;


    public MinimaxRecord(Move move, int score) {
        this.move = move;
        this.score = score;
    }

    public MinimaxRecord() {
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
