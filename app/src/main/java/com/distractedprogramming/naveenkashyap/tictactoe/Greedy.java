package com.distractedprogramming.naveenkashyap.tictactoe;

import android.util.Log;

/**
 * Created by Naveen on 1/2/17.
 *
 * Greedy Algorithm
 * Prefers the first move leading to a win
 */

public class Greedy extends Algorithms {

    Board board;
    private final String LOGTAG = "GREEDY";

    public Greedy(Board board) {
        this.board = board;
        log(Log.DEBUG, "Using greedy algorithm!");
    }

    /*
    compMove()
    @return if decision was made, nextMove
            else, any free space
     */
    @Override
    public int[] compMove() {
        int[] res = nextMove(board.COMP,0);
        if (res[2] == FALSE){
            int[] random = chooseRandom();
            res[0] = random[0];
            res[1] = random[1];
        }
        log(Log.DEBUG, "Comp found move");
        return new int[] {res[0],res[1]};
    }

    private int[] chooseRandom(){
        for (int row = 0; row < board.DIMEN; row++) {
            for (int col = 0; col < board.DIMEN; col++) {
                if (board.isEmptyAt(row,col))
                    return new int[] {row,col};
            }
        }
        return new int[] {FALSE,FALSE};
    }

    /*
    nextMove(int[])
    @param {char player}: current player
    @return {int[] res}:
                res[0]: desired row, FALSE if no decision
                res[1]: desired col, FALSE if no decision
                res[2]: TRUE if computer will win
                        FALSE if computer will lose or draw
     */
    @Override
    public int[] nextMove(char player, int depth) {
        for (int row = 0; row < board.DIMEN; row++) {
            for (int col = 0; col < board.DIMEN; col++) {

                if (!board.isEmptyAt(row,col))
                    continue;

                board.click(row,col,player);
                if (board.isCompWin()){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a computer win");
                    return new int[] {row,col,TRUE};
                }
                if (board.isUserWin()){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a user win");
                    return new int[] {row,col,FALSE};
                }

                if (board.isDraw()){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a draw");
                    return new int[] {row,col,FALSE};
                }

                int[] res = nextMove(otherPlayer(player),depth);
                int win = res[2];

                if (win == TRUE) {
                    board.undoClick(row,col);
                    return new int[] {row,col,win};
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
