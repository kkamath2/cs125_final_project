package com.example.cs125finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private String gameId;
    // initialize all categories with a list of words
    private final String[] disneyWords = {};
    private final String[] activityWords = {};
    private final String[] dailyLifeWords = {};
    private final String[] uiucWords = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameId = getIntent().getStringExtra("game");
        // use gameId to determine which deck to use here

        TextView toAct = findViewById(R.id.word);
        // code for game here:
        // every time answer is correct, pick random new word in array representing current deck
        // also keep score (possibly make a method for this?)
        // account for correct answer vs pass?
        // toAct.setText("..."); changes displayed word

        runTimer();

    }

    public void runTimer() {
        final TextView timer = findViewById(R.id.timer);
        final CountDownTimer countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisec) {
                long totalSeconds = millisec / 1000;
                long minutes = totalSeconds / 60;
                long seconds = totalSeconds - minutes * 60;
                String timeLeft = "" + minutes + ":";
                if (seconds < 10) {
                    timeLeft += "0" + seconds;
                } else {
                    timeLeft += seconds;
                }
                timer.setText(timeLeft);
            }

            public void onFinish() {
                endGame();
            }
        };
        countDownTimer.start();
    }

    public void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Time's up! Your score is...");
        builder.setNegativeButton(R.string.new_deck, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                // is there some other way to restart the game?
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                intent.putExtra("game", gameId);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
}
