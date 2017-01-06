package com.distractedprogramming.naveenkashyap.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.logging.Level;

public class LevelsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String DIFFICULTY = "difficulty";
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    private final String LOGTAG = "LEVELS";

    private TextView easyTextView;
    private TextView mediumTextView;
    private TextView hardTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        initializeTextViews();
    }

    private void initializeTextViews() {
        easyTextView = (TextView) findViewById(R.id.tv_easy);
        easyTextView.setOnClickListener(this);
        mediumTextView = (TextView) findViewById(R.id.tv_medium);
        mediumTextView.setOnClickListener(this);
        hardTextView = (TextView) findViewById(R.id.tv_hard);
        hardTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(LevelsActivity.this, MainActivity.class);
        switch (id) {
            case R.id.tv_easy:
                log(Log.DEBUG, "EASY level chosen.");
                intent.putExtra(DIFFICULTY, EASY);
                break;
            case R.id.tv_medium:
                log(Log.DEBUG, "MEDIUM level chosen.");
                intent.putExtra(DIFFICULTY, MEDIUM);
                break;
            case R.id.tv_hard:
                log(Log.DEBUG, "HARD level chosen.");
                intent.putExtra(DIFFICULTY, HARD);
                break;
        }
        startActivity(intent);
    }

    public void log(int logLevel, String msg){
        Log.println(logLevel, LOGTAG, msg);
    }
}
