package com.example.cuong.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cuong on 3/14/2018.
 */

public class ChessBoard {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private int[][] board;
    private int player;
    private Context context;
    private int bitmapWidth;
    private int bitmapHeight;
    private int colQty;
    private int rowQty;
    private List<Line> listLine;

    private Bitmap bmTick;
    private Bitmap bmCross;
    private MinimaxBoss minimaxBoss;

    public ChessBoard(Context context, int bitmapWidth, int bitmapHeight, int colQty, int rowQty)
    {
        this.context=context;
        this.bitmapWidth=bitmapWidth;
        this.bitmapHeight=bitmapHeight;
        this.colQty=colQty;
        this.rowQty=rowQty;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getBitmapWidth() {
        return bitmapWidth;
    }

    public void setBitmapWidth(int bitmapWidth) {
        this.bitmapWidth = bitmapWidth;
    }

    public int getBitmapHeight() {
        return bitmapHeight;
    }

    public void setBitmapHeight(int bitmapHeight) {
        this.bitmapHeight = bitmapHeight;
    }

    public int getColQty() {
        return colQty;
    }

    public void setColQty(int colQty) {
        this.colQty = colQty;
    }

    public int getRowQty() {
        return rowQty;
    }

    public void setRowQty(int rowQty) {
        this.rowQty = rowQty;
    }

    public int[][] getBoard() {
        int[][] newBoard= new int[rowQty][colQty];
        for (int i=0;i<=rowQty;i++){
            for (int j=0;j<=colQty;j++){
                newBoard[i][j]=board[i][j];
            }
        }
        return newBoard;
    }

    public int getPlayer(){
        return  player;
    }



    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void init(){
         bitmap= Bitmap.createBitmap(bitmapWidth,bitmapHeight, Bitmap.Config.ARGB_8888);
         canvas=new Canvas(bitmap);
         paint=new Paint();
         int storkeWidth=2;
         paint.setStrokeWidth(storkeWidth);
         board=new int[rowQty][colQty];
         player=0;
         listLine=new ArrayList<>();

         bmCross= BitmapFactory.decodeResource(context.getResources(),R.drawable.circle);
         bmTick=BitmapFactory.decodeResource(context.getResources(),R.drawable.xicon);

         int cellWidth=bitmapWidth/colQty;
         for(int i=0;i<=colQty;i++){
             listLine.add(new Line(i*cellWidth,0,cellWidth*i, bitmapHeight));
         }
         int cellHeight=bitmapHeight/rowQty;
        for (int i=0;i<=rowQty;i++)
        {
            listLine.add(new Line(0,i*cellHeight,bitmapWidth,i*cellHeight));
        }

        for (int i=0;i<rowQty;i++){
            for (int j=0;j<colQty;j++){
                board[i][j]=-1;
            }
        }
    }

    public Bitmap drawBoard(){
        Line line;
        for(int i=0;i<listLine.size();i++){
            line=listLine.get(i);
            canvas.drawLine(line.getStartX(),line.getStartY(),line.getStopX(),line.getStopY(),paint);
        }
        return bitmap;
    }




    public void onDrawBoard(int colIndex, int rowIndex, View view) {
        int cellWidth = view.getWidth() / colQty;
        int cellHeight = view.getHeight() / rowQty;

        board[rowIndex][colIndex] = player;//gán nước đi là người chơi nào
        int padding = 50;
        if (player == 0) {
            canvas.drawBitmap(bmCross, new Rect(0, 0, bmCross.getWidth(), bmCross.getHeight()), new Rect(colIndex * cellWidth + padding,
                    rowIndex * cellHeight + padding,
                    (colIndex + 1) * cellWidth - padding, (rowIndex + 1) * cellHeight - padding), paint);
            player = 1;
        } else {
            player = 0;
            MinimaxRecord minimaxRecord;
            minimaxRecord = minimaxBoss.minimax(this, player, colQty * rowQty - count(), count());
            canvas.drawBitmap(bmTick, new Rect(0, 0, bmTick.getWidth(), bmTick.getHeight()), new Rect(colIndex * cellWidth + padding,
                    rowIndex * cellHeight + padding,
                    (colIndex + 1) * cellWidth - padding, (rowIndex + 1) * cellHeight - padding), paint);
        }
    }


    public boolean onTouch(final View v, MotionEvent event){

        int cellWidth=v.getWidth()/colQty;
        int cellHeight=v.getHeight()/rowQty;

        int cellWidthBm=bitmapWidth/colQty;;
        int cellHeightBm=bitmapHeight/rowQty;

        int columnIndex=(int) event.getX()/cellWidth;
        int rowIndex=(int) event.getY()/cellHeight;

       if(board[rowIndex][columnIndex]!=-1) {
           return true;
       }


        board[rowIndex][columnIndex]=player;
       onDrawBoard(columnIndex,rowIndex,v);
       player=(player+1)%2;
        int count = getCurrentDept();
        final int currentDetp = rowQty*colQty - count;
        ((MainActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //cho mình 1 nước đi, nghĩa là mọi đến minimax
                //duyệt mảng 2 chiều board nếu mà board khác -1 thì có bước đi
                MinimaxRecord record = minimaxBoss.minimax(ChessBoard.this, 1, currentDetp, 9);//nước đi
                //có nước đi, đặt nước đi
                //tiến trình
                makeMove(record.getMove());
                onDrawBoard(record.getMove().getColIndex(), record.getMove().getRowIndex(),v);

      /*  //Update Chessboard
        int padding=5;
        if(player==0){
            canvas.drawBitmap(bmCross,new Rect(0,0,bmCross.getWidth(),bmCross.getHeight()),new Rect(columnIndex*cellWidthBm+padding,
                    rowIndex*cellHeightBm+padding,
                    (columnIndex+1)*cellWidthBm-padding,(rowIndex+1)*cellHeightBm-padding),paint);
            player=1;}
        else {
            player = 0;
            MinimaxRecord minimaxRecord;
            minimaxRecord=minimaxBoss.minimax(this,player,colQty*rowQty-count(),count());
            canvas.drawBitmap(bmTick,new Rect(0,0,bmTick.getWidth(),bmTick.getHeight()),new Rect(columnIndex*cellWidthBm+padding,
                    rowIndex*cellHeightBm+padding,
                    (columnIndex+1)*cellWidthBm-padding,(rowIndex+1)*cellHeightBm-padding),paint);
        }       ;
        */
                //  v.invalidate();


                //Toast.makeText(context, event.getX()+" "+event.getY(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, v.getWidth()+" "+v.getHeight(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, rowIndex+" "+columnIndex, Toast.LENGTH_SHORT).show();

                return true;
            }
        }


     public boolean checkWin(int player){
            if (board[0][0]==board[0][1] && board[0][0]==board[0][2] && board[0][0]==player) return true;
            else if (board[0][0]==board[1][1]&& board[0][0]==board[2][2] && board[0][0]==player) return true;
            else if (board[2][0]==board[1][1]&&board[0][2]==board[2][0]&& board[1][1]==player) return true;
            else if (board[1][0]==board[1][1] && board[1][0]==board[1][2] && board[1][0]==player) return true;
            else if (board[2][0]==board[2][1] && board[2][0]==board[2][2] && board[2][0]==player) return true;
            else if (board[0][0]==board[1][0] && board[0][0]==board[2][0] && board[0][0]==player) return true;
            else if (board[0][1]==board[1][1] && board[0][1]==board[2][1] && board[0][1]==player) return true;
            else if (board[0][2]==board[1][2] && board[0][2]==board[2][2] && board[0][2]==player) return true;
            else return false;
     }

     public boolean isGameOver(){
         if (checkWin(0)==true ||checkWin(1)==true)
             return true;
         int count=0;
         for (int i=0;i<=rowQty;i++){
             for (int j=0;j<=colQty;j++){
                 if (board[i][j]==0||board[i][j]==1)
                     count=count+1;
             }
         }
         if(count==rowQty*colQty) return true;
         else return false;
     }

     public int count(){
         int count=0;
         for(int i=-0;i<=rowQty;i++){
             for (int j=0;j<=colQty;j++){
                 if (board[i][j]!=0 && board[i][j]!=1) count++;
             }
         }
         return count;
     }

     public void setPlayer(int player)
     {
         this.player=player;
     }

     public int evaluate(int player){
         if (checkWin(player)==true) return 1;
         if (checkWin((player+1)/2)==true) return -1;
         else return 0;
     }


     public int currentPlayer(){
         return player;
     }

     public List<Move> getMove() {
         List<Move> moves = new ArrayList<>();
         for (int i = 0; i < rowQty; i++) {
             for (int j = 0; j <= colQty; j++)
                 moves.add(new Move(i, j));
         }
         return moves;
         }



     public void makeMove(Move move){
         board[move.getRowIndex()][move.getColIndex()]=player;
         player=(player+1)%2;
     }
                                                  public int getCurrentDept(){
                                                      int count = 0;
                                                      for (int i = 0; i < rowQty; i++) {
                                                          for (int j = 0; j < colQty; j++) {
                                                              if (board[i][j] == -1) count++;
                                                          }
                                                      }
                                                      return count;
                                                  }

}




 /*     public boolean isGameOver() {
        if (checkWin(0) || checkwin(1)) ;
        int count = 0;
        for (int m = 0; m < rowQty; m++) {
            for (int n = 0; n < colQty; n++)

                return true;
        }
    }
/*
    private boolean checkWin(int i) {
        for (int i1=0; i<=rowQty;i++){
          for(int j=0;j<=colQty;i++)
          {

          }
          }
    }

    public List<Move> getMove()
        {
            List<Move> moves=new ArrayList<>();
            for (int i=0;i<rowQty;i++){
                for (int j=0;j<colQty;j++)
                    moves.add(new Move(i,j));
            }
            return moves;
        }


        public void abc(Move move){
          //  board
        }


        public int everLuate(){
            //if(checkWin(player)) return 1;
           // if (checkWin((player+1)/2)) return -1;
            //else if return 0;
            return 1;
        }
        */

