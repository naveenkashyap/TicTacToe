package com.distractedprogramming.naveenkashyap.tictactoe;

import android.util.Log;

/**
 * Created by Naveen on 1/2/17.
 *
 * Shortest Win algorithm
 * Prefers move that leads to a computer win in the shortest number of wins
 */

public class ShortestWin extends Algorithms {
    private Board board;
    private int shortestRow, shortestCol, winFound, depthOfWin;
    private final String LOGTAG = "SHORTESTWIN";

    public ShortestWin(Board board) {
        this.board = board;
        shortestRow = FALSE;
        shortestCol = FALSE;
        winFound = FALSE;
        depthOfWin = FALSE;
        log(Log.DEBUG, "Using shortest win algorithm!");
    }

    /*
    compMove()
    @return if decision was made, nextMove
            else, any free space
     */
    @Override
    public int[] compMove() {
        resetShortestPath();
        int[] res = nextMove(board.COMP, 0);
        if (res[2] == FALSE){
            int[] random = chooseRandom();
            res[0] = random[0];
            res[1] = random[1];
        }
        log(Log.DEBUG, "Comp found move");
        log(Log.DEBUG, "Winning depth was: " + res[3]);
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
    public int[] nextMove(char player, int currentDepth) {
        int shortestWin = 9;
        for (int row = 0; row < board.DIMEN; row++) {
            for (int col = 0; col < board.DIMEN; col++) {

                if (!board.isEmptyAt(row,col))
                    continue;

                board.click(row,col,player);

                /*
                if (board.isCompWin()){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a computer win");
                    return new int[] {row,col,TRUE,currentDepth};
                }
                if (board.isUserWin()){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a user win");
                    return new int[] {row,col,FALSE};
                }
                */
                if (board.isWin(player)){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a win for player " + player);
                    return new int[] {row,col,TRUE,currentDepth};
                }
                if (board.isDraw()){
                    board.undoClick(row,col);
                    log(Log.DEBUG, "Found a draw");
                    return new int[] {row,col,FALSE, currentDepth};
                }

                int[] res = nextMove(otherPlayer(player), currentDepth+1);
                int win = res[2];

                if (win == FALSE) {
                    int depthOfWin = res[3];
                    if (depthOfWin < shortestWin){
                        log(Log.DEBUG, "Found shorter win path");
                        shortestWin = depthOfWin;
                        setShortestCoordinate(row,col,depthOfWin);
                    }
                }

                board.undoClick(row,col);
            }
        }

        return getShortestCoordinate();
    }

    private void setShortestCoordinate(int row, int col, int winningDepth){
        shortestRow = row;
        shortestCol = col;
        winFound = TRUE;
        depthOfWin = winningDepth;
    }

    private int[] getShortestCoordinate(){
        return new int[] {shortestRow, shortestCol, winFound, depthOfWin};
    }

    private void resetShortestPath(){
        shortestRow = FALSE;
        shortestCol = FALSE;
        winFound = FALSE;
        depthOfWin = FALSE;
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
