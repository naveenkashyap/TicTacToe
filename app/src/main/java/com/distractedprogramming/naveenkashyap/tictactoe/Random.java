package com.distractedprogramming.naveenkashyap.tictactoe;

/**
 * Created by Naveen on 1/5/17.
 */

import java.lang.Math;

public class Random extends Algorithms {

    private Board board;

    public Random(Board board){
        this.board = board;
    }
    @Override
    public int[] compMove() {

        int row = (int) (Math.random()*3);
        int col = (int) (Math.random()*3);

        if (board.isEmptyAt(row,col))
            return new int[] {row,col,TRUE};
        else
            return compMove();
    }

    @Override
    public int[] nextMove(char player, int depth) {
        return new int[0];
    }
}
