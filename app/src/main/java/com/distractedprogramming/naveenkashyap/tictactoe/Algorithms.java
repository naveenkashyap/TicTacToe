package com.distractedprogramming.naveenkashyap.tictactoe;

/**
 * Created by Naveen on 1/1/17.
 */

public abstract class Algorithms {

    protected final int TRUE = -1;
    protected final int FALSE = -2;

    public abstract int[] compMove();

    public abstract int[] nextMove(char player, int depth);

}
