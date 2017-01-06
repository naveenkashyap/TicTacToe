package com.distractedprogramming.naveenkashyap.tictactoe;

import android.os.Build;
import android.util.Log;

/**
 * Created by Naveen on 1/5/17.
 */

public class Minimax extends Algorithms {
    private final String LOGTAG = "MINIMAX";

    private Board board;

    public Minimax(Board board){
        this.board = board;
        log(Log.DEBUG, "Using Minimax algorithm!");
    }

    @Override
    public int[] compMove() {
        int[] res = nextMove(board.COMP,0);
        log(Log.DEBUG, "Comp found move");
        return new int[] {res[0],res[1]};
    }

    @Override
    public int[] nextMove(char player, int depth) {
        for (int row = 0; row < board.DIMEN; row++) {
            for (int col = 0; col < board.DIMEN; col++) {

                if (!board.isEmptyAt(row,col))
                    continue;

                board.click(row,col,player);

                if (board.isWin(player)){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a win for player " + player);
                    return new int[]{row,col,TRUE};
                }
                if (board.isDraw()){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a draw");
                    return new int[] {row,col,FALSE};
                }

                int[] res = nextMove(otherPlayer(player),depth);
                int win = res[2];

                if (win == FALSE) {
                    board.undoClick(row,col);
                    return new int[] {row,col,TRUE};
                }

                board.undoClick(row,col);
            }
        }
        return new int[] {FALSE,FALSE,FALSE};
    }

    private char otherPlayer(char player){
        if (player == board.USER)
            return board.COMP;
        return board.USER;
    }

    private void log(int logLevel, String msg){
        Log.println(logLevel, LOGTAG, msg);
    }
}
