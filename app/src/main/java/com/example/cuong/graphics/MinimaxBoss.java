package com.example.cuong.graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cuong on 3/21/2018.
 */

public class MinimaxBoss {


    public MinimaxRecord minimax(ChessBoard chessBoard, int player, int maxDepth, int currentDepth) {

        int bestScore;
        Move bestMove = null;


        //moves=chessBoard.getMove();

        if (chessBoard.isGameOver() == true || currentDepth == maxDepth)
            return new MinimaxRecord(null, chessBoard.evaluate(player));

        else {
            if (chessBoard.currentPlayer() == player) {
                bestScore = Integer.MIN_VALUE;
            }
            bestScore = Integer.MAX_VALUE;


            for (Move move : chessBoard.getMove()) {
                ChessBoard newBoard = new ChessBoard(chessBoard.getContext(), chessBoard.getBitmapWidth(), chessBoard.getBitmapHeight(), chessBoard.getRowQty(), chessBoard.getColQty());
                newBoard.setBoard(chessBoard.getBoard());
                newBoard.setPlayer(chessBoard.getPlayer());
                MinimaxRecord minimaxRecord = minimax(newBoard, player, maxDepth, currentDepth + 1);
                if (chessBoard.currentPlayer() == player) {
                    if (minimaxRecord.getScore() > bestScore) {
                        bestScore = minimaxRecord.getScore();
                        bestMove = minimaxRecord.getMove();
                    }
                } else {
                    if (minimaxRecord.getScore() < bestScore) {
                        bestScore = minimaxRecord.getScore();
                        bestMove = minimaxRecord.getMove();
                    }
                }

            }
        }
        return new MinimaxRecord(bestMove, bestScore);
    }
}





    //   public MinimaxRecord minimax(ChessBoard board, int player,int maxDepth, int currentDepth)
 //   }


