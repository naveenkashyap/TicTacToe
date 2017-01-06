package com.distractedprogramming.naveenkashyap.tictactoe;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String LOGTAG = "MAIN";
    private final String CALLBACK_TEXTVIEWS = "textviews";
    private TextView[][] mTextViews = new TextView[3][3];
    private Board board;
    private Algorithms algo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = new Board();
        int difficulty = getIntent().getIntExtra(LevelsActivity.DIFFICULTY,0);
        switch (difficulty){
            case 0:
                algo = new Random(board);
                break;
            case 1:
                algo = new Greedy(board);
                break;
            case 2:
                algo = new Minimax(board);
                break;
        }
        loadTextViews();
        setClickListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_restart:
                restart();
                break;
            case R.id.menu_levels:
                goToLevelsActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToLevelsActivity(){
        Intent intent = new Intent(MainActivity.this, LevelsActivity.class);
        startActivity(intent);
    }

    private void loadTextViews(){
        mTextViews[0][0] = (TextView) findViewById(R.id.aa);
        mTextViews[0][1] = (TextView) findViewById(R.id.ab);
        mTextViews[0][2] = (TextView) findViewById(R.id.ac);
        mTextViews[1][0] = (TextView) findViewById(R.id.ba);
        mTextViews[1][1] = (TextView) findViewById(R.id.bb);
        mTextViews[1][2] = (TextView) findViewById(R.id.bc);
        mTextViews[2][0] = (TextView) findViewById(R.id.ca);
        mTextViews[2][1] = (TextView) findViewById(R.id.cb);
        mTextViews[2][2] = (TextView) findViewById(R.id.cc);
        log(Log.DEBUG, "Loaded TextViews successfully!");

    }

    private void setClickListeners(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                mTextViews[i][j].setOnClickListener(this);
            }
        }
        log(Log.DEBUG, "Set click listeners successfully!");
    }

    private void restart(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                mTextViews[i][j].setText(null);
                mTextViews[i][j].setClickable(true);
            }
        }
        board.setBoard();
    }

    private void setTextViews(TextView[][] TextViews){
        mTextViews = TextViews;
    }

    private void userClicked(int row, int col){
        log(Log.DEBUG, "User clicked (" + row + ", " + col +")");

        // update UI
        mTextViews[row][col].setText(board.USER+"");
        mTextViews[row][col].setClickable(false);
        // update internal board
        board.userClicked(row,col);

        if (board.isUserWin()) {
            reportUserWin();
        } else if (board.isDraw()){
            reportDraw();
        } else {
            compMove();
        }
    }

    private void compClicked(int row, int col){
        log(Log.DEBUG, "Comp clicked (" + row + ", " + col +")");

        // update UI
        mTextViews[row][col].setText(board.COMP+"");
        mTextViews[row][col].setClickable(false);
        // update internal board
        board.compClicked(row,col);

        if (board.isCompWin()){
            reportCompWin();
        } else if (board.isDraw()){
            reportDraw();
        }
    }

    private void compMove(){
        int[] res = algo.compMove();
        int row = res[0], col = res[1];
        compClicked(row,col);
    }

    private void reportUserWin(){
        Toast.makeText(MainActivity.this, "USER WINS!!", Toast.LENGTH_LONG).show();
        freezeGame();
    }

    private void reportCompWin(){
        Toast.makeText(MainActivity.this, "COMPUTER WINS!!", Toast.LENGTH_LONG).show();
        freezeGame();
    }

    private void reportDraw(){
        Toast.makeText(MainActivity.this, "DRAW!!", Toast.LENGTH_LONG).show();
        freezeGame();
    }

    private void freezeGame(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                mTextViews[i][j].setClickable(false);
            }
        }
    }

    // TODO: send computations to background loader
    // TODO: handle lifecycle


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View clickedView) {
        int id = clickedView.getId();
        switch (id){
            case R.id.aa:
                userClicked(0,0);
                break;
            case R.id.ab:
                userClicked(0,1);
                break;
            case R.id.ac:
                userClicked(0,2);
                break;
            case R.id.ba:
                userClicked(1,0);
                break;
            case R.id.bb:
                userClicked(1,1);
                break;
            case R.id.bc:
                userClicked(1,2);
                break;
            case R.id.ca:
                userClicked(2,0);
                break;
            case R.id.cb:
                userClicked(2,1);
                break;
            case R.id.cc:
                userClicked(2,2);
                break;
        }
    }

    private void log(int logLevel, String msg){
        Log.println(logLevel, LOGTAG, msg);
    }
}
